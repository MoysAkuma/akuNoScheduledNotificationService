package com.akumasoft.job;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.akumasoft.model.queque;
import com.akumasoft.model.historico;
import com.akumasoft.repository.quequeRepository;
import com.akumasoft.repository.historicoRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationJob {
    
    private final quequeRepository notificationScheduleRepository;
    private final JavaMailSender mailSender;
    private final historicoRepository historicoRepository;
    
    @Scheduled(fixedDelay = 60000) // Ejecutar cada minuto
    @Transactional

    public void procesar(){
        List<queque> notificacionesPendientes = notificationScheduleRepository.findByStatus("PENDING"); 
        if (notificacionesPendientes.isEmpty()) {
            log.info("No hay notificaciones pendientes para procesar.");
            return;
        }
        
        log.info("Procesando {} notificaciones pendientes.", notificacionesPendientes.size());

        for (queque notificacion : notificacionesPendientes) {
            try {
                // Aquí iría la lógica para enviar el correo utilizando mailSender
                 SimpleMailMessage message = new SimpleMailMessage();
                 message.setTo(notificacion.getCorreo_destino());
                 message.setSubject(notificacion.getAsunto());
                 message.setText(notificacion.getContentHTML());
                 mailSender.send(message);
                
                // Simulamos el envío exitoso
                notificacion.setStatus("SENT");
                notificacion.setSent_date(java.time.LocalDateTime.now());
                log.info("Notificación {} enviada exitosamente.", notificacion.getId());
            } catch (Exception e) {
                log.error("Error al enviar la notificación {}: {}", notificacion.getId(), e.getMessage());
                notificacion.setStatus("FAILED");
                notificacion.setError_message(e.getMessage());
            } finally {
                notificationScheduleRepository.save(notificacion);
            }
        }
    }
    

    @Scheduled(cron = "0 0 12 * * *") // Ejecutar todos los días a las 12:00 PM
    @Transactional
    public void createHistorico() {
        List<queque> notificacionesEnviadasList = notificationScheduleRepository.findByStatus("SENT"); 
        if (notificacionesEnviadasList.isEmpty()) {
            log.info("No hay notificaciones enviadas para crear el histórico.");
            return;
        }
        
        log.info("Creando histórico para {} notificaciones enviadas.", notificacionesEnviadasList.size());
        
        // Guardar copia en histórico
        for (queque notificacion : notificacionesEnviadasList) {
            historico hist = new historico();
            hist.setSolicitudId(notificacion.getSolicitudId());
            hist.setAsunto(notificacion.getAsunto());
            hist.setContentHTML(notificacion.getContentHTML());
            hist.setStatus(notificacion.getStatus());
            hist.setCorreo_destino(notificacion.getCorreo_destino());
            hist.setCorreo_cc(notificacion.getCorreo_cc());
            hist.setCorreo_bcc(notificacion.getCorreo_bcc());
            hist.setError_message(notificacion.getError_message());
            hist.setRetry_count(notificacion.getRetry_count());
            hist.setEnvio_date(notificacion.getEnvio_date());
            hist.setCreacion_date(notificacion.getCreacion_date());
            hist.setSent_date(notificacion.getSent_date());
            hist.setProcesada(notificacion.isProcesada());
            
            historicoRepository.save(hist);
        }
        
        log.info("Histórico creado exitosamente. Vaciando tabla queque...");
        
        // Vaciar la tabla queque
        notificationScheduleRepository.deleteAll();
        
        log.info("Tabla queque vaciada. Proceso de histórico completado.");
    }
}
