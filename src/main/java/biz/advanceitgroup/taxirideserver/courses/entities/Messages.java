package biz.advanceitgroup.taxirideserver.courses.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Messages {
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	@NotNull(message = "Message type can not be null")
	private int MSG_TYPE; // Type de message: 1 = notification
	@NotNull(message = "Code can not be null")
	private int CODE; 
	@NotNull(message = "English value can not be null")
	private String VALUE_EN; 
	@NotNull(message = "French value can not be null")
	private String VALUE_FR;
	
}
