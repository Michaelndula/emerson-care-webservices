package emerson_care.emerson_care.dto;

import lombok.Data;

import java.util.List;

@Data
public class PatientInfoDTO {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idNumber;
    private String role;
    private List<AddressDTO> addresses;
    private List<PersonalInformationDTO> personalInformation;
}

@Data
class AddressDTO {
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}

@Data
class PersonalInformationDTO {
    private String sex;
    private String birthDate;
    private String maritalStatus;
    private String qualification;
    private String certification;
    private String workExperience;
    private String availability;
}
