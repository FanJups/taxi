package biz.advanceitgroup.taxirideserver.courses.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import biz.advanceitgroup.taxirideserver.geolocalisation.entities.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Enregistrer la position courante d'un terminal mobile: TaxiRide.
 * @author Saha Merlin
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CurrentDriverLocation {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 50,nullable= false)
    private String emailOrPhoneNumber;
	
	@Column(length = 1)
    private Boolean driverAvailable;
	
	private long timeoutDriverConnect;
	
	
	@Column(length = 3,nullable= false)
    private String codePays;
	
	@Column(length = 100,nullable= false)
    private String ville;
	
	@Column(nullable= false)
    private double longitude;
	
	@Column(nullable= false)
    private double latitude;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date dateEnreg;
	
	@Column(name="RIDE_ID", columnDefinition="Decimal(10) default '0'",nullable=false)
	private Long RIDE_ID;
        
        private String position;
        
        private double distance;
	
	private double duration;
	
	
	
}
