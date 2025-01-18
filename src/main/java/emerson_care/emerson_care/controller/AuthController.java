package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.LoginRequest;
import emerson_care.emerson_care.entity.Address;
import emerson_care.emerson_care.entity.PersonalInformation;
import emerson_care.emerson_care.entity.Profile;
import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import emerson_care.emerson_care.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

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
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already taken. Please use a unique Username.", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email already exists. Please use a unique email.", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return new ResponseEntity<>("Phone already exists. Please use a unique Phone Number.", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByIdNumber(user.getIdNumber()).isPresent()) {
            return new ResponseEntity<>("ID Number already exists. Please use a unique ID Number.", HttpStatus.BAD_REQUEST);
        }

        if ("true".equalsIgnoreCase(user.getIsAdmin())) {
            user.setRole("admin");
        } else if ("true".equalsIgnoreCase(user.getIsProvider())) {
            user.setRole("provider");
        } else if ("true".equalsIgnoreCase(user.getIsPatient())) {
            user.setRole("patient");
        } else {
            user.setRole("user");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the user reference in the addresses
        if (user.getAddresses() != null) {
            for (Address address : user.getAddresses()) {
                address.setUser(user);
            }
        }

        if (user.getPersonalInformation() != null) {
            for (PersonalInformation personalInfo : user.getPersonalInformation()) {
                personalInfo.setUser(user);
            }
        }

        try {
            // Load the default profile photo as an InputStream
            InputStream defaultPhotoStream = getClass().getResourceAsStream("/profiles/profile.jpg");
            if (defaultPhotoStream == null) {
                return new ResponseEntity<>("Default profile photo not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String userPhotoPath = "profiles/" + user.getUsername() + "_profile.jpg";

            // Ensure the profiles directory exists
            File profilesDir = new File("profiles/");
            if (!profilesDir.exists()) {
                profilesDir.mkdirs();
            }

            // Copy the photo from the InputStream to the user's photo directory
            Files.copy(defaultPhotoStream, Paths.get(userPhotoPath), StandardCopyOption.REPLACE_EXISTING);

            // Create a profile for the user
            Profile profile = new Profile();
            profile.setUser(user);
            profile.setPhotoPath(userPhotoPath);
            user.setProfile(profile);

        } catch (IOException e) {
            return new ResponseEntity<>("Failed to set default profile photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUsername());

            // Return token in response
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token
            ));
        } else {
            return new ResponseEntity<>(Map.of("error", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
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
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

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

        if (updatedUser.getAddresses() != null && !updatedUser.getAddresses().isEmpty()) {
            List<Address> updatedAddresses = updatedUser.getAddresses();
            existingUser.getAddresses().clear();
            for (Address updatedAddress : updatedAddresses) {
                updatedAddress.setUser(existingUser);
                existingUser.getAddresses().add(updatedAddress);
            }
        }

        if (updatedUser.getPersonalInformation() != null && !updatedUser.getPersonalInformation().isEmpty()) {
            List<PersonalInformation> updatedPersonalInfo = updatedUser.getPersonalInformation();
            existingUser.getPersonalInformation().clear();
            for (PersonalInformation info : updatedPersonalInfo) {
                info.setUser(existingUser);
                existingUser.getPersonalInformation().add(info);
            }
        }

        userRepository.save(existingUser);

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);

        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/profile/{id}/upload")
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile photo) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        // Validate file size (2 MB = 2 * 1024 * 1024 bytes)
        if (photo.getSize() > 2 * 1024 * 1024) {
            return new ResponseEntity<>("File size exceeds the maximum limit of 2MB", HttpStatus.BAD_REQUEST);
        }

        try {
            File profilesDir = new File("profiles/");
            if (!profilesDir.exists()) {
                profilesDir.mkdirs();
            }

            // Save the new photo
            String newPhotoPath = "profiles/" + user.getUsername() + "_profile_" + System.currentTimeMillis() + ".jpg";
            File newFile = new File(newPhotoPath);
            photo.transferTo(newFile);

            // Update the profile photo path
            Profile profile = getProfile(user, newPhotoPath);
            user.setProfile(profile);

            // Save the updated user and profile
            userRepository.save(user);

            return new ResponseEntity<>("Profile photo updated successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Profile getProfile(User user, String newPhotoPath) {
        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        // Delete the old photo if it exists and is not the default photo
        if (profile.getPhotoPath() != null && !profile.getPhotoPath().equals("src/main/resources/profiles/profile.jpg")) {
            File oldPhoto = new File(profile.getPhotoPath());
            if (oldPhoto.exists()) {
                oldPhoto.delete();
            }
        }

        profile.setPhotoPath(newPhotoPath);
        return profile;
    }
}
