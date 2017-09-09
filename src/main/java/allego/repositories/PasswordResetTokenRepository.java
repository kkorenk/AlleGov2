package allego.repositories;

import allego.models.User;
import allego.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by ibm on 2017-08-15.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);


    /* TODO usuwanie przeterminowanych tokenów
    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expirydate <= ?1")
    void deleteAllExpiredSince(Date now);
    */

}
