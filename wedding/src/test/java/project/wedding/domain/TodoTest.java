package project.wedding.domain;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TodoTest {

    @DisplayName("클라이언트에서 Id와 할 일을 보내주면 todo 객체가 만들어진다.")
    @Test
    void createTodoInstance() {
        Todo todo = new Todo("todoId1", "할 일");
        assertThat(todo.getTodoId()).isEqualTo("todoId1");
    }

    @DisplayName("todo 객체의 할 일을 수정할 수 있다.")
    @Test
    void modifyTodoDescription() {
        Todo todo = new Todo("todoId1", "할 일");
        todo.modifyTodoDescription("할 일 수정");
        assertThat(todo.getTodoDescription()).isEqualTo("할 일 수정");
    }

    @DisplayName("todo를 완수하면 체크상태가 true로 바뀐다.")
    @Test
    void changeTodoCheckStatus() {
        Todo todo = new Todo("todoId1", "할 일");
        todo.changeCheckStatus();
        assertThat(todo.isTodoCheckStatus()).isEqualTo(true);
    }
}
