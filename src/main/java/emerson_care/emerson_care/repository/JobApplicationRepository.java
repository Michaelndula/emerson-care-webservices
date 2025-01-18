package emerson_care.emerson_care.repository;

import emerson_care.emerson_care.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByApplicantName(String applicantName);
}
