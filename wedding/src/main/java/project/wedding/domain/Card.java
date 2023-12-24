package project.wedding.domain;

import lombok.Getter;

@Getter
public class Card {
    private final int MAX_TODO = 3;  // 한 카드당 투두는 최대 3개까지만 생성 가능
    private final int id;
    private String title;
    private String[][] todos;       // [["unchecked", "todo"], ...]
    private Status status;

    public Card() {
        this.id = CardIdMamager.getLastId();
        this.todos = new String[MAX_TODO][2];
        this.status = Status.BACKLOG;
    }

    public int getCardId() {
        return this.id;
    }

    public String[][] getTodos(int cardId) {
        return this.todos;
    }
}