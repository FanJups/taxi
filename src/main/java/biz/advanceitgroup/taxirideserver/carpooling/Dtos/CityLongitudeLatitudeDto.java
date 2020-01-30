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
public class CityLongitudeLatitudeDto {
	
	protected String cityName;
	protected double longitude;
	protected double latitude;

}
