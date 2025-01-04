package emerson_care.emerson_care.repository;

import emerson_care.emerson_care.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByIdNumber(String idNumber);
    Optional<User> findByProviderId(String providerId);
    Optional<User> findByUsername(String username);
}
