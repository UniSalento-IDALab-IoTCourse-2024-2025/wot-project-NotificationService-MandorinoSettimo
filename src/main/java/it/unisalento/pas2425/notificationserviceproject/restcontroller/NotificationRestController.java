package it.unisalento.pas2425.notificationserviceproject.restcontroller;

import it.unisalento.pas2425.notificationserviceproject.dto.NotificationRequestDTO;
import it.unisalento.pas2425.notificationserviceproject.dto.NotificationResponseDTO;
import it.unisalento.pas2425.notificationserviceproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponseDTO> sendNotification(@RequestBody NotificationRequestDTO request) {
        try {
            notificationService.sendPushNotification(request);
            return ResponseEntity.ok(new NotificationResponseDTO(NotificationResponseDTO.OK, "Notifica inviata"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new NotificationResponseDTO(NotificationResponseDTO.ERROR, "Errore: " + e.getMessage()));
        }
    }

}
