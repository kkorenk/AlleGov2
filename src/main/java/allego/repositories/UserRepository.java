package allego.repositories;

import allego.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kamil on 21.07.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
