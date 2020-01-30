/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.courses.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripLogsDto {
    private String departure;
    private String arrival;
    private Date postDate;
    private double tripCost;
    private long duration;
    private double tripLenght;
    private String optionName;
    private String paymentName;
    private String riderName;
    private String DriverName;
    Long tripId;
}
