package project.wedding.domain;

import java.util.logging.Logger;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class User {
    private final String userId;
    private final Logger logger = Logger.getLogger(User.class.getName());

    public User(String userId) {
        this.userId = userId;
        logger.info("User 생성");
    }
}
