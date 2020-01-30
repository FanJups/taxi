package biz.advanceitgroup.taxirideserver.accounts.entities;

import biz.advanceitgroup.taxirideserver.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente le compte interne de la plateforme TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SystemAccount extends MainEntity {

    @Id
    @Column(name = "SYSTEM_ACCOUNT_ID")
    private Long id;

    private String accountName;
    private Double amount;

}
