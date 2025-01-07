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
@RequestMapping("/api/patients")
public class PatientController {

    private final UserRepository userRepository;

    public PatientController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PatientInfoDTO> getPatientInfoById(@PathVariable Long id) {
        return userRepository.findById(id)
                .filter(user -> "patient".equalsIgnoreCase(user.getRole()))
                .map(user -> {
                    PatientInfoDTO patientInfoDTO = DTOMapper.mapToPatientInfoDTO(user);
                    return ResponseEntity.ok(patientInfoDTO);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientInfoDTO>> getAllPatientsWithRolePatient() {
        List<User> patients = userRepository.findAllByRole("patient");
        List<PatientInfoDTO> patientInfoDTOs = DTOMapper.mapToPatientInfoDTOList(patients);
        return ResponseEntity.ok(patientInfoDTOs);
    }
}
