package biz.advanceitgroup.taxirideserver.authentification.dto;

import biz.advanceitgroup.taxirideserver.authentification.annotations.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Roles Request", description = "The role request dto")
public class RoleRequest {
    @NullOrNotBlank(message = "Roles  can be null but not blank")
    @ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String")
    private String role;
}
