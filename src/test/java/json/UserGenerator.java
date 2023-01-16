package json;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User getRandomUser() {
        return new User(RandomStringUtils.randomAlphabetic(7) + "@" + RandomStringUtils.randomAlphabetic(7)+".ru",
                RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphabetic(7));
    }
}
