package biz.advanceitgroup.taxirideserver.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id; // L'identifiant du véhicule

    private String phoneNumber; // Le numéro de téléphone du propriétaire du véhicule
    private Long userId; // L'identifiant du propriétaire

    @Column(length = 50)
    private String vehicleType;
    private Integer numberOfSeat; // Nombre de places assises dans le véhicule
    private String brand;
    private String model;
    private String matriculationNumber; // Numéro d'imatriculation du véhicule

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date firstUseDate;

    @Column(length = 10)
    private String color; // Code de couleur en hexadecimal (#ffbf00 par exemple qui représente la couleur jaune du taxi)

    private Integer numbersWheel; // Le nombre de roues du véhicule

    private String description;
    
    private String carconstructor;
    private String travelOption;
    private String cityScope;
    private String language;

    public VehicleDTO(Long id, String phoneNumber, Long userId, String vehicleType, Integer numberOfSeat, String brand, String model, String matriculationNumber, Date firstUseDate, String color, Integer numbersWheel, String description, String carconstructor, String travelOption, String cityScope) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.numberOfSeat = numberOfSeat;
        this.brand = brand;
        this.model = model;
        this.matriculationNumber = matriculationNumber;
        this.firstUseDate = firstUseDate;
        this.color = color;
        this.numbersWheel = numbersWheel;
        this.description = description;
        this.carconstructor = carconstructor;
        this.travelOption = travelOption;
        this.cityScope = cityScope;
    }

  
    
    
}
