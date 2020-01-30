package biz.advanceitgroup.taxirideserver.profiles.services.interfaces;


import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.UserPaymentMode;

import java.util.List;
import java.util.Optional;

public interface UserPaymentModeService {

    UserPaymentMode save(UserPaymentMode paymentMode);

    void delete(UserPaymentMode paymentMode);

    Optional<UserPaymentMode> findOne(String id);

    Optional<UserPaymentMode> findOne(Long id);

    UserPaymentMode update(UserPaymentMode paymentMode, Long id);

    List<UserPaymentMode> findAll();

    List<UserPaymentMode> findAllByUser(Users user);
    
    //delete payment mode
    public Boolean deletePayment(Long user_id, int paymentnumber);
    
    //Set default payment mode
    public Boolean defaultPayment(Long user_id, int paymentnumber);
    
    //Find default payment mode
    Optional<UserPaymentMode> findDefaultPayment(Long user_id);

}
