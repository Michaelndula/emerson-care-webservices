package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.DTOMapper;
import emerson_care.emerson_care.dto.UserInfoDTO;
import emerson_care.emerson_care.entity.Address;
import emerson_care.emerson_care.entity.PersonalInformation;
import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import emerson_care.emerson_care.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserInfoDTO> getUserInfoById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    UserInfoDTO patientInfoDTO = DTOMapper.mapToUserInfoDTO(user);
                    return ResponseEntity.ok(patientInfoDTO);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        List<User> users = userRepository.findAllPatients();
        List<UserInfoDTO> patientInfoDTOs = DTOMapper.mapToUserInfoDTOList(users);
        return ResponseEntity.ok(patientInfoDTOs);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getLoggedInUserDetails(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);

            return userRepository.findByUsername(username)
                    .map(user -> {
                        UserInfoDTO userInfoDTO = DTOMapper.mapToUserInfoDTO(user);
                        return ResponseEntity.ok(userInfoDTO);
                    })
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/update/me")
    public ResponseEntity<String> updateLoggedInUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User updatedUser) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);

        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }

        Optional<User> existingUserOptional = userRepository.findByUsername(username);
        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User existingUser = existingUserOptional.get();

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

        return new ResponseEntity<>("Your Information updated successfully", HttpStatus.OK);
    }
}
