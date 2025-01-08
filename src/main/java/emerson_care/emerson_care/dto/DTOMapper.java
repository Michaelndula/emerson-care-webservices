package emerson_care.emerson_care.dto;

import emerson_care.emerson_care.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static UserInfoDTO mapToUserInfoDTO(User user) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setMiddleName(user.getMiddleName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setIdNumber(user.getIdNumber());
        dto.setRole(user.getRole());

        if (user.getAddresses() != null) {
            dto.setAddresses(user.getAddresses().stream().map(address -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setStreet(address.getStreet());
                addressDTO.setCity(address.getCity());
                addressDTO.setState(address.getState());
                addressDTO.setCountry(address.getCountry());
                addressDTO.setPostalCode(address.getPostalCode());
                return addressDTO;
            }).collect(Collectors.toList()));
        }

        if (user.getPersonalInformation() != null) {
            dto.setPersonalInformation(user.getPersonalInformation().stream().map(personalInfo -> {
                PersonalInformationDTO personalDTO = new PersonalInformationDTO();
                personalDTO.setSex(personalInfo.getSex());
                personalDTO.setBirthDate(String.valueOf(personalInfo.getBirthDate()));
                personalDTO.setMaritalStatus(personalInfo.getMaritalStatus());
                personalDTO.setQualification(personalInfo.getQualification());
                personalDTO.setCertification(personalInfo.getCertification());
                personalDTO.setWorkExperience(personalInfo.getWorkExperience());
                personalDTO.setAvailability(personalInfo.getAvailability());
                return personalDTO;
            }).collect(Collectors.toList()));
        }

        return dto;
    }

    public static List<UserInfoDTO> mapToUserInfoDTOList(List<User> users) {
        return users.stream()
                .map(DTOMapper::mapToUserInfoDTO)
                .collect(Collectors.toList());
    }
}
