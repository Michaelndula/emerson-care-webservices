package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {
    private final NotificationService notificationService;

    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PutMapping("/mark-read/{id}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id) {
        boolean updated = notificationService.markAsRead(id);
        if (updated) {
            return ResponseEntity.ok("Notification marked as read.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found.");
        }
    }
}
