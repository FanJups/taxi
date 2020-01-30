package biz.advanceitgroup.taxirideserver.authentification.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshToken> findById(Long id);

    Optional<String> findTokenById(Long id);

    Optional<RefreshToken> findByToken(String token);

    Optional<Users> findUserById(Long id);

    Optional<Users> findUserByToken(String token);

    RefreshToken save(RefreshToken refreshToken);

    RefreshToken createRefreshToken() ;


    /**
     * Vérifier si le jeton fourni a expiré ou non sur la base du courant
     * heure du serveur et / ou erreur de projection sinon
     */
    public void verifyExpiration(RefreshToken token) ;

    public void deleteById(Long id);


    public void increaseCount(RefreshToken refreshToken) ;
}
