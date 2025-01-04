package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.LoginRequest;
import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import emerson_care.emerson_care.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already taken. Please use a unique Username.", HttpStatus.BAD_REQUEST);
        }

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email already exists. Please use a unique email.", HttpStatus.BAD_REQUEST);
        }

        // Check if phone already exists
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return new ResponseEntity<>("Phone already exists. Please use a unique Phone Number.", HttpStatus.BAD_REQUEST);
        }

        // Check if ID already exists
        if (userRepository.findByIdNumber(user.getIdNumber()).isPresent()) {
            return new ResponseEntity<>("ID Number already exists. Please use a unique ID Number.", HttpStatus.BAD_REQUEST);
        }

        // Determine the role based on the flags
        if ("true".equalsIgnoreCase(user.getIsAdmin())) {
            user.setRole("admin");
        } else if ("true".equalsIgnoreCase(user.getIsProvider())) {
            user.setRole("provider");
        } else if ("true".equalsIgnoreCase(user.getIsPatient())) {
            user.setRole("patient");
        } else {
            user.setRole("user");
        }

        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(String.valueOf(user));
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User existingUser = existingUserOptional.get();

        // Update the user's details
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(existingUser.getUsername())) {
            if (userRepository.findByUsername(updatedUser.getUsername()).isPresent()) {
                return new ResponseEntity<>("Username already taken. Please use a unique Username.", HttpStatus.BAD_REQUEST);
            }
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
                return new ResponseEntity<>("Email already exists. Please use a unique email.", HttpStatus.BAD_REQUEST);
            }
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPhoneNumber() != null && !updatedUser.getPhoneNumber().equals(existingUser.getPhoneNumber())) {
            if (userRepository.findByPhoneNumber(updatedUser.getPhoneNumber()).isPresent()) {
                return new ResponseEntity<>("Phone number already exists. Please use a unique Phone Number.", HttpStatus.BAD_REQUEST);
            }
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        if (updatedUser.getIdNumber() != null && !updatedUser.getIdNumber().equals(existingUser.getIdNumber())) {
            if (userRepository.findByIdNumber(updatedUser.getIdNumber()).isPresent()) {
                return new ResponseEntity<>("ID Number already exists. Please use a unique ID Number.", HttpStatus.BAD_REQUEST);
            }
            existingUser.setIdNumber(updatedUser.getIdNumber());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            // Update and hash the password if provided
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Update additional fields if provided
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getMiddleName() != null) {
            existingUser.setMiddleName(updatedUser.getMiddleName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }

        // Update role based on flags
        if ("true".equalsIgnoreCase(updatedUser.getIsAdmin())) {
            existingUser.setRole("admin");
        } else if ("true".equalsIgnoreCase(updatedUser.getIsProvider())) {
            existingUser.setRole("provider");
        } else if ("true".equalsIgnoreCase(updatedUser.getIsPatient())) {
            existingUser.setRole("patient");
        }

        // Save the updated user
        userRepository.save(existingUser);

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Delete the user
        userRepository.deleteById(id);

        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
