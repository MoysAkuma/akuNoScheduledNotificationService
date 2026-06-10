package com.akumasoft.job;

import java.beans.Transient;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.akumasoft.model.queque;
import com.akumasoft.repository.NotificationScheduleRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationJob {
    
    private final NotificationScheduleRepository notificationScheduleRepository;
    private final JavaMailSender mailSender;
    
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
}
