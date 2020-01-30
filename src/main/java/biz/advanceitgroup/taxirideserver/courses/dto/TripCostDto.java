/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCostDto {
    private long tripId;
    private long requestId;
    private long tripCost;
    private long cancelTripCostByRider;
    private long cancelTripCostByDriver;
    private int errorRadius;
}
