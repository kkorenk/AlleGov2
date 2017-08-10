package allego.services;

/**
 * Created by Kamil on 23.07.2017.
 */
public interface SecurityService {

    String findLoggedInUsername();
    void autologin(String username, String password);
}
