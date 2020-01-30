package biz.advanceitgroup.taxirideserver.geolocalisation.entities;

import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Cette classe représente une ville dans laquelle les activités de TaxiRide ont lieu.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City extends MainEntity {

    @Id
    @Column(name = "CITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private Double signalScope;
    private Double notificationDistance;
    private Double notificationTime;
    private Double tripCancellationTime;
    @NotNull(message = "the longitude can not be null")
    private double longitude=11.5;
    @NotNull(message = "the latitude can not be null")
    private double latitude=3.8;

}
