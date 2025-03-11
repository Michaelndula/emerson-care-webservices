package emerson_care.emerson_care.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private boolean isRead = false;
    private String jobUuid;
    private Long jobId;

    public Notification(String title, String message, String type, String jobUuid, Long jobId) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.jobUuid = jobUuid;
        this.jobId = jobId;
        this.createdAt = LocalDateTime.now();
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
