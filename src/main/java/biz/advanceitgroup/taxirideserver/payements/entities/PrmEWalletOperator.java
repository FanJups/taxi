/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author daniel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
public class PrmEWalletOperator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long countryId;
    @Column(length = 50)
    private String code;
    @Column(length = 50)
    private String nameEn;
    @Column(length = 50)
    private String nameFr;
    @Column(length = 150)
    private String descriptionFr;
     @Column(length =150)
    private String descriptionEn;
}
