package emerson_care.emerson_care.repository;

import emerson_care.emerson_care.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
