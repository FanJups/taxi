package biz.advanceitgroup.taxirideserver.profiles.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.profiles.entities.PromotionalCode;
import biz.advanceitgroup.taxirideserver.profiles.entities.UserPromotionalCode;

import java.util.List;
import java.util.Optional;

public interface UserPromotionalCodeService {


    UserPromotionalCode save(UserPromotionalCode code);

    void delete(UserPromotionalCode code);

    Optional<UserPromotionalCode> findOne(String id);

    Optional<UserPromotionalCode> findOne(Long id);

    UserPromotionalCode update(UserPromotionalCode code, Long id);

    Boolean existsByUserAndPromotionalCode(Users user, PromotionalCode promotionalCode);

    Boolean existByKey(String key);

    Boolean existByPromotionalCode(PromotionalCode code);

    List<UserPromotionalCode> findAll();

    List<UserPromotionalCode> findAllByUser(Users user);

}
