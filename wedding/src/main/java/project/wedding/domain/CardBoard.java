package project.wedding.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CardBoard {
    private final LinkedHashMap<Integer, Card> cardList = new LinkedHashMap<>();

    public void createCard() {
        if (isSatisfied()) {
            Card card = CardFactory.getCardInstance();
            cardList.put(card.getCardId(), card);
            return;
        }
        throw new IllegalArgumentException("카드는 최대 20개까지만 만들 수 있습니다.");
    }

    private boolean isSatisfied() {
        return cardList.size() <= 20;
    }

    public List<Card> getAllCards() { return List.copyOf(cardList.values()); }

    public List<Card> getAllBacklogCards() {
        List<Card> backlogCards = List.copyOf(cardList.values());
        return backlogCards.stream()
            .filter(card -> card.getCardStatus() == CardStatus.BACKLOG)
            .collect(Collectors.toList());
    }

    public Card getCardById(int cardId) {
        if (cardList.get(cardId) == null) {
            throw new IllegalArgumentException("해당 cardId가 존재하지 않습니다.");
        }
        return cardList.get(cardId);
    }

    public List<Integer> getAllCardId() {
        List<Card> allCards = new ArrayList<>(cardList.values());
        return allCards.stream()
            .map(Card::getCardId)
            .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return """
            CardBoard{
                cardList=%s
            }
            """.formatted(cardList);
    }
}
