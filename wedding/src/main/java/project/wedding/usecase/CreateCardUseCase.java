package project.wedding.usecase;

import java.util.logging.Logger;

import project.wedding.domain.CardBoard;
import project.wedding.domain.CardContainerRepository;

import lombok.extern.log4j.Log4j;

@Log4j
public class CreateCardUseCase {
    private final CardContainerRepository cardContainerRepository;
    private final Logger logger = Logger.getLogger(CreateCardUseCase.class.getName());

    public CreateCardUseCase(CardContainerRepository cardContainerRepository) {
        this.cardContainerRepository = cardContainerRepository;
    }

    public void createCard(String userId) {
        CardBoard container = cardContainerRepository.findByUserId(userId);
        container.createCard();
        logger.info("ID: " + userId + "의 카드가 생성됐습니다.");
    }
}
