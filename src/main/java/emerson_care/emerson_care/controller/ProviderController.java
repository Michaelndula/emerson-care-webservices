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
@RequestMapping("/api/providers")
public class ProviderController {

    private final UserRepository userRepository;

    public ProviderController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserInfoDTO> getProviderInfoById(@PathVariable Long id) {
        return userRepository.findById(id)
                .filter(user -> "provider".equalsIgnoreCase(user.getRole()))
                .map(user -> {
                    UserInfoDTO userInfoDTO = DTOMapper.mapToUserInfoDTO(user);
                    return ResponseEntity.ok(userInfoDTO);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserInfoDTO>> getAllPatients() {
        List<User> provider = userRepository.findAllByRole("provider");
        List<UserInfoDTO> userInfoDTOs = DTOMapper.mapToUserInfoDTOList(provider);
        return ResponseEntity.ok(userInfoDTOs);
    }
}
