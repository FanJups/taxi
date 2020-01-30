package biz.advanceitgroup.taxirideserver.authentification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneCodeValidationRequestDTO {
    private String phoneNumber;
}
