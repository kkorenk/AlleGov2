package allego.repositories;

import allego.security.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ibm on 2017-10-01.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

}
