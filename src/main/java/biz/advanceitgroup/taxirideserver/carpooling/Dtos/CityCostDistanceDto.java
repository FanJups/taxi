package biz.advanceitgroup.taxirideserver.carpooling.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* @author Fanon Jupkwo
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityCostDistanceDto extends CityLongitudeLatitudeDto {
	
	private double defaulttravelCost;
    private long defaultTravelDistance;
    
    public CityCostDistanceDto(String cityName,double longitude,double latitude,double defaulttravelCost,long defaultTravelDistance)
    {
    	
    	this.cityName = cityName;
    	this.longitude = longitude;
    	this.latitude = latitude;
    	this.defaulttravelCost=defaulttravelCost;
    	this.defaultTravelDistance=defaultTravelDistance;
    	
    	
    }

}
