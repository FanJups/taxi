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
public class TripInfo {
    private double pickUpLatitude;
    private double pickUpLongitude;
    private double arrivalLongitude;
    private double arrivalLatitude;
    private String arrivalAddress;
    private String departureAddress;
    private double tripCost;
}
