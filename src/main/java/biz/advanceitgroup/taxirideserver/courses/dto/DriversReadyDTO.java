/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.courses.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *is used to find all drivers availables in a circle of a given radius
 * @author daniel
 */
@Data
@AllArgsConstructor
public class DriversReadyDTO {
    private double latitude;
    private double longitude;
    private String codeOption;
}
