package emerson_care.emerson_care.service;

import emerson_care.emerson_care.entity.Job;
import emerson_care.emerson_care.entity.Notification;
import emerson_care.emerson_care.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createJobNotification(Job job) {
        String message = "A new job has been posted: " + job.getTitle() + ".";

        Notification notification = new Notification(
                "New Job Available",
                message,
                "job",
                job.getUuid(),
                job.getId()
        );

        notificationRepository.save(notification);
    }

    public List<Map<String, Object>> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(notification -> {
                    Map<String, Object> formattedNotification = new HashMap<>();
                    formattedNotification.put("id", notification.getId());
                    formattedNotification.put("title", notification.getTitle());
                    formattedNotification.put("message", notification.getMessage());
                    formattedNotification.put("timestamp", getTimeElapsed(notification.getCreatedAt()));
                    formattedNotification.put("isRead", notification.getIsRead());
                    formattedNotification.put("type", notification.getType());

                    return formattedNotification;
                }).collect(Collectors.toList());
    }

    public boolean markAsRead(Long id) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setIsRead(true);
            notificationRepository.save(notification);
            return true;
        }).orElse(false);
    }



    private String getTimeElapsed(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds();

        if (days > 0) {
            return days == 1 ? "1 day ago" : days + " days ago";
        } else if (hours > 0) {
            return hours == 1 ? "1 hour ago" : hours + " hours ago";
        } else if (minutes > 0) {
            return minutes == 1 ? "1 min ago" : minutes + " mins ago";
        } else {
            return seconds <= 1 ? "just now" : seconds + " seconds ago";
        }
    }
}
