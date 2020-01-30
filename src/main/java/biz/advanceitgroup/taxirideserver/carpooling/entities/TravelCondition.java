/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TravelCondition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long travelId;
    @Column(length = 250)
    private String description;

    public TravelCondition(long travelId, String description) {
        this.travelId = travelId;
        this.description = description;
    }
}
