package emerson_care.emerson_care.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobApplicationDTO {
    private Long id;
    private String applicantName;
    private String cv;
    private String applicationLetter;
    private LocalDate availableStartDate;
    private String currentEmploymentStatus;
}
