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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LogUserLocation {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 50)
	@NotNull(message = "Email or Phone number can not be null")
    private String emailOrPhoneNumber;
	
	@Column(length = 3)
	@NotNull(message = "Country code can not be null")
    private String codePays;
	
	@Column(length = 15)
	@NotNull(message = "City code can not be null")
    private String codeVille;
	
	@NotNull(message = "Longitude code can not be null")
    private double longitude;
	
	@NotNull(message = "lattitude code can not be null")
    private double latitude;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date dateEnreg;
	
	@NotNull(message = "Ride id code can not be null")
	private Long RIDE_ID;

    public LogUserLocation(String emailOrPhoneNumber, String codePays, String codeVille, double longitude, double latitude, Date dateEnreg, Long RIDE_ID) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
        this.codePays = codePays;
        this.codeVille = codeVille;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateEnreg = dateEnreg;
        this.RIDE_ID = RIDE_ID;
    }

        
}
