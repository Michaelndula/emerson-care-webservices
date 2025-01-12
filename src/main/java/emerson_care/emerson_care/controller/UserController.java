package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.DTOMapper;
import emerson_care.emerson_care.dto.UserInfoDTO;
import emerson_care.emerson_care.entity.User;
import emerson_care.emerson_care.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
