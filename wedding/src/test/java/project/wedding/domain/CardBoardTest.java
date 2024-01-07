package project.wedding.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardBoardTest {

    @DisplayName("CardBoard에서 Card를 만들 수 있다.")
    @Test
    void checkCardId() {
        CardBoard cardBoard = new CardBoard();
        cardBoard.createCard();

        int cardId = cardBoard.getAllCardId().get(0);
        assertThat(cardBoard.getCardById(cardId).getCardId()).isEqualTo(cardId);
    }

    @DisplayName("CardBoard에서 Card를 만들면 Card의 제목을 기입할 수 있다.")
    @Test
    void addCardTitle() {
        CardBoard cardBoard = new CardBoard();
        cardBoard.createCard();
        cardBoard.createCard();

        List<Integer> randomCardId = cardBoard.getAllCardId();
        for (Integer cardId : randomCardId) {
            cardBoard.getCardById(cardId).setCardTitle("카드 제목" + cardId);
            assertThat(cardBoard.getCardById(cardId).getCardTitle()).isEqualTo("카드 제목" + cardId);
        }
    }

    @DisplayName("cardBoard는 card 개수를 20개만 만들 수 있다.")
    @Test
    void createCardLimit() {
        CardBoard cardBoard = new CardBoard();

        for (int i = 0; i < 21; i++) {
            cardBoard.createCard();
        }
        assertThatThrownBy(cardBoard::createCard)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("카드는 최대 20개까지만 만들 수 있습니다.");
    }

    @DisplayName("모든 카드들을 조회할 수 있다.")
    @Test
    void getAllCards() {
        CardBoard cardBoard = new CardBoard();
        cardBoard.createCard();
        cardBoard.createCard();

        assertThat(cardBoard.getAllCards().size()).isEqualTo(2);
        assertThat(cardBoard.getAllCards().get(0).getCardStatus()).isEqualTo(CardStatus.BACKLOG);
    }

    @DisplayName("모든 백로그 상태의 카드들을 조회할 수 있다.")
    @Test
    void getAllBacklogCards() {
        CardBoard cardBoard = new CardBoard();
        cardBoard.createCard();
        cardBoard.createCard();

        assertThat(cardBoard.getAllBacklogCards().size()).isEqualTo(2);
        for (Card card : cardBoard.getAllBacklogCards()) {
            assertThat(card.getCardStatus()).isEqualTo(CardStatus.BACKLOG);
        }
    }
}
