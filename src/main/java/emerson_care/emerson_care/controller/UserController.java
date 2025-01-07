package emerson_care.emerson_care.controller;

import emerson_care.emerson_care.dto.DTOMapper;
import emerson_care.emerson_care.dto.PatientInfoDTO;
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
    public ResponseEntity<PatientInfoDTO> getUserInfoById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    PatientInfoDTO patientInfoDTO = DTOMapper.mapToPatientInfoDTO(user);
                    return ResponseEntity.ok(patientInfoDTO);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientInfoDTO>> getAllUsersExcludingAdmin() {
        List<User> users = userRepository.findAllPatientsExcludingAdmin();
        List<PatientInfoDTO> patientInfoDTOs = DTOMapper.mapToPatientInfoDTOList(users);
        return ResponseEntity.ok(patientInfoDTOs);
    }
}
