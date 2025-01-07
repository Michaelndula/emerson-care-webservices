package emerson_care.emerson_care.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "personal_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    private String sex;
    private String maritalStatus;
    private String qualification;
    private String certification;
    private String workExperience;
    private String availability;
}
