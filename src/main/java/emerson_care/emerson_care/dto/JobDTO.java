package emerson_care.emerson_care.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobDTO {
    private Long id;
    private String uuid;
    private String title;
    private String description;
    private String posterImage;
    private LocalDate postedDate;
    private LocalDate deadline;
    private int applicationCount;
    private List<JobApplicationDTO> applications;
}
