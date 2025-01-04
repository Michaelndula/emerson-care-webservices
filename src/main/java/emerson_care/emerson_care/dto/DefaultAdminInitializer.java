package emerson_care.emerson_care.dto;

import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.firstname}")
    private String adminFirstName;

    @Value("${admin.lastname}")
    private String adminLastName;

    @PostConstruct
    public void initializeAdminAccount() {
        if (!userRepository.findByEmail(adminEmail).isPresent()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEmail(adminEmail);
            admin.setPhoneNumber("");
            admin.setRole("admin");
            admin.setFirstName(adminFirstName);
            admin.setLastName(adminLastName);

            userRepository.save(admin);
            System.out.println("Default admin account created: " + adminEmail);
        } else {
            System.out.println("Admin account already exists.");
        }
    }
}
