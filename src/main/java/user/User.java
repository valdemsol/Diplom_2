package user;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getEmptyUser() {
        return new User("", "", "");
    }

    public static User getRandomUser() {
        String email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru";
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public static User getRandomUserWithoutEmail(String email) {
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public static User getRandomUserWithoutPassword(String password) {
        String email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public static User getRandomUserWithoutName(String name) {
        String email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru";
        String password = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }
}
