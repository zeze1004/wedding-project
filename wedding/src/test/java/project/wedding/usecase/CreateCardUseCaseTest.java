package project.wedding.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.wedding.domain.CardContainerRepository;
import project.wedding.domain.User;

import project.wedding.domain.CardBoard;

@ExtendWith(MockitoExtension.class)
@DisplayName("user가 카드를 생성합니다.")
class CreateCardUseCaseTest {
    @Mock
    CardContainerRepository cardContainerRepository;

    @Test
    void createCardUseCase() throws Exception {

        User user1 = new User("ODEE1");

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardContainerRepository);

        when(cardContainerRepository.findByUserId(user1.getUserId())).thenReturn(new CardBoard());
        createCardUseCase.createCard("ODEE1");

        verify(cardContainerRepository, times(1)).findByUserId(user1.getUserId());

        assertThat(cardContainerRepository.findByUserId(user1.getUserId()).getCardById(1).getCardId()).isEqualTo(1);
    }

}
