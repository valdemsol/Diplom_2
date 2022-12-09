package user;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public Credentials UserCredentialsBadEmail(Credentials creds) {
        creds.email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru";
        return creds;
    }

    public Credentials UserCredentialsBadPassword(Credentials creds) {
        creds.email = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        return creds;
    }

    public static Credentials from(User user) {
        return new Credentials(user);
    }
}
