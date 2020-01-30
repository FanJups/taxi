package biz.advanceitgroup.taxirideserver.profiles.services.interfaces;

import biz.advanceitgroup.taxirideserver.profiles.entities.PaymentMode;

import java.util.List;
import java.util.Optional;

public interface PaymentModeService {
    PaymentMode save(PaymentMode payment);

    void delete(PaymentMode payment);

    Optional<PaymentMode> findOne(String id);

    Optional<PaymentMode> findOne(Long id);

    Optional<PaymentMode> findByPaymentType(Integer type);

    PaymentMode update(PaymentMode payment, Long id);

    List<PaymentMode> findAll();
    
    //public Boolean deletePayment(Long id);
    
   

}
