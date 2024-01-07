package project.wedding.domain;

public interface CardContainerRepository {
    CardBoard findByUserId(String userId);
}
