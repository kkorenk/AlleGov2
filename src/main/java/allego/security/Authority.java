package allego.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by ibm on 2017-08-15.
 */
public class Authority implements GrantedAuthority{

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
