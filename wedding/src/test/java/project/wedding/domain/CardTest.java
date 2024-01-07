package project.wedding.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {

    @DisplayName("카드 인스턴스가 생성될 때마다 카드 id값이 1씩 증가한다.")
    @Test
    void createCardInstance() {
        Card card1 = new Card();
        Card card2 = new Card();

        assertThat(card1.getCardId()).isEqualTo(1);
        assertThat(card2.getCardId()).isEqualTo(2);
    }

    @DisplayName("카드 제목을 기입할 수 있다.")
    @Test
    void addCardTitle() {
        Card card = new Card();
        card.setCardTitle("카드 제목");
        assertThat(card.getCardTitle()).isEqualTo("카드 제목");
    }

    @DisplayName("카드에 투두리스트를 추가할 수 있다.")
    @Test
    void addTodoList() {
        Card card = new Card();
        card.addTodoList("todoId1", "할 일");
        assertThat(card.getTodoById("todoId1").getTodoId()).isEqualTo("todoId1");
    }

    @DisplayName("투두리스트에 담겨진 Todo 객체의 todoDescription를 수정할 수 있다.")
    @Test
    void modifyTodoDescription() {
        Card card = new Card();
        card.addTodoList("todoId1", "할 일");
        card.modifyTodoDescription("todoId1", "할 일 수정");
        assertThat(card.getTodoById("todoId1").getTodoDescription()).isEqualTo("할 일 수정");
    }

    @DisplayName("투두를 완료했을 시 체크박스에 완료 표시로 변경 할 수 있다.")
    @Test
    void changeCheckBoxStatus() {
        Card card = new Card();
        card.addTodoList("todoId1", "할 일");
        card.changeCheckStatus("todoId1");
        assertThat(card.getTodoById("todoId1").isTodoCheckStatus()).isEqualTo(true);
    }

    @DisplayName("생성된 투두를 삭제할 수 있다.")
    @Test
    void deleteTodo() {
        Card card = new Card();
        card.addTodoList("todoId1", "할 일");
        card.deleteTodo("todoId1");
        assertThatThrownBy(() -> card.getTodoById("todoId1"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("해당 todoId가 존재하지 않습니다.");
    }
}
