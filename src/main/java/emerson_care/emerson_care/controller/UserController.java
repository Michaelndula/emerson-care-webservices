package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.DTOMapper;
import emerson_care.emerson_care.dto.UserInfoDTO;
import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import emerson_care.emerson_care.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

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
}
