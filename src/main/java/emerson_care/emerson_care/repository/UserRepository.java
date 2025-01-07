package emerson_care.emerson_care.repository;

import emerson_care.emerson_care.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByIdNumber(String idNumber);
    Optional<User> findByProviderId(String providerId);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.role <> 'admin'")
    List<User> findAllPatientsExcludingAdmin();

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(@Param("role") String role);
}
