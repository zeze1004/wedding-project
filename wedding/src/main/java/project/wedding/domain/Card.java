package project.wedding.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public class Card {
    private final int cardId;
    private String cardTitle;
    private final List<Todo> todoList;
    private final CardStatus cardStatus;

    public Card() {
        this.cardId = CardIdMamager.getLastId();
        this.todoList = new ArrayList<>();
        this.cardStatus = CardStatus.BACKLOG;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public void addTodoList(String todoId, String todoDescription) {
        todoList.add(new Todo(todoId, todoDescription));
    }

    // todoList에 담겨진 Todo 객체의 todoDescription 수정
    public void modifyTodoDescription(String todoId, String todoDescription) {
        getTodoById(todoId).modifyTodoDescription(todoDescription);
    }

    // todoList에 담겨진 Todo 객체의 checkStatus 수정
    public void changeCheckStatus(String todoId) {
        getTodoById(todoId).changeCheckStatus();
    }

    // todoList에 담겨진 Todo 객체 삭제
    public void deleteTodo(String todoId) {
        todoList.remove(getTodoById(todoId));
    }

    public Todo getTodoById(String todoId) {
        Optional<Todo> oneTodo = todoList.stream()
            .filter(todo -> todo.getTodoId().equals(todoId))
            .findFirst();
        if (oneTodo.isPresent()) {
            return oneTodo.get();
        } else {
            throw new IllegalArgumentException("해당 todoId가 존재하지 않습니다.");
        }
    }

    public String toString() {
        return """
            Card{
                CardId=%d,
                title='%s',
                cardStatus=%s
            }
            """.formatted(cardId, cardTitle, cardStatus);
    }
}
