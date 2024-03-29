package org.wedding.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.wedding.domain.todo.TodoCheckStatus.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.in.command.todo.DeleteTodoCommand;
import org.wedding.application.port.in.command.todo.ReadTodoCommand;
import org.wedding.application.port.in.command.todo.UpdateTodoCommand;
import org.wedding.application.port.out.repository.TodoRepository;
import org.wedding.application.service.response.todo.TodoDto;
import org.wedding.domain.todo.Todo;
import org.wedding.domain.todo.exception.TodoException;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;
    private Todo todo;
    private CreateTodoCommand createCommand;
    private UpdateTodoCommand updateCommand;
    private DeleteTodoCommand deleteCommand;
    private ReadTodoCommand   readCommand;

    @BeforeEach
    void setUp() {

        createCommand = new CreateTodoCommand(1, "할일");
        todo = CreateTodoCommand.toEntity(createCommand);
    }

    /**
     * cardId가 존재 여부 테스트는 CardCheckAspectTest에서 진행함
     *
     */

    @DisplayName("한 카드에 todo가 3개 미만일 때 최대 todo 생성 개수(3개) 이내여서 todo가 생성된다.")
    @Test
    void createTodo_Success() {

        when(todoRepository.countTodoByCardId(anyInt())).thenReturn(1);
        todoService.createTodo(createCommand);
        verify(todoRepository).save(any(Todo.class));
    }

    @DisplayName("한 카드에 이미 todo가 3개 이상있으면 해당 카드에 todo 생성에 실패한다.")
    @Test
    void createTodo_Fail_WhenMaxTodoExceed() {

        when(todoRepository.countTodoByCardId(anyInt())).thenReturn(TodoService.MAX_TODOS_PER_CARD);
        assertThrows(TodoException.class, () -> todoService.createTodo(createCommand));
    }

    @DisplayName("todo 수정시 todoId를 찾을 수 없으면 예외를 발생시킨다.")
    @Test
    void updateTodo_Fail_WhenTodoNotExist() {

        when(todoRepository.existsByTodoId(anyInt(), anyInt())).thenReturn(false);
        assertThrows(TodoException.class, () -> todoService.checkTodoExistence(anyInt(), anyInt()));
    }

    @DisplayName("todo 수정시 할 일과 체크 상태를 함께 변강할 수 있다.")
    @Transactional
    @Test
    void updateTodo_Success_WithTodoItemAndCheckStatus() {

        updateCommand = new UpdateTodoCommand(1, 1, "할 일 변경", CHECKED);

        when(todoRepository.existsByTodoId(anyInt(), anyInt())).thenReturn(true);
        when(todoRepository.findByTodoId(anyInt(), anyInt())).thenReturn(todo);

        todoService.updateTodo(updateCommand);
        verify(todoRepository).update(any(Todo.class));
    }

    @DisplayName("todo 수정시 체크 상태만 변강할 수 있다.")
    @Transactional
    @Test
    void updateTodo_Success_WithTCheckStatus() {

        updateCommand = new UpdateTodoCommand(1, 1, null, CHECKED);

        when(todoRepository.existsByTodoId(anyInt(), anyInt())).thenReturn(true);
        when(todoRepository.findByTodoId(anyInt(), anyInt())).thenReturn(todo);

        todoService.updateTodo(updateCommand);
        verify(todoRepository).update(any(Todo.class));
    }

    @DisplayName("todo 삭제시 cardId와 todoId가 있으면 삭제가 성공한다.")
    @Transactional
    @Test
    void deleteTodo_Success() {

        when(todoRepository.existsByTodoId(anyInt(), anyInt())).thenReturn(true);

        deleteCommand = new DeleteTodoCommand(anyInt(), anyInt());
        todoService.deleteTodo(deleteCommand);
        verify(todoRepository).deleteTodo(anyInt(), anyInt());
    }

    @DisplayName("todo 삭제시 todoId를 찾을 수 없으면 예외를 발생시킨다.")
    @Transactional
    @Test
    void deleteTodo_Fail_WhenTodoNotExist() {

        when(todoRepository.existsByTodoId(anyInt(), anyInt())).thenReturn(false);
        assertThrows(TodoException.class, () -> todoService.checkTodoExistence(anyInt(), anyInt()));
    }

    @DisplayName("특정 카드에 속한 모든 todo를 조회한다.")
    @Test
    void getAllTodos_Success() {

        // given
        ArrayList<Todo> todos = new ArrayList<>();
        todos.add(todo);

        // when
        when(todoRepository.getAllTodos(1)).thenReturn(todos);
        ArrayList<TodoDto> todoDto = todoService.getAllTodos(1);

        // then
        verify(todoRepository).getAllTodos(1);
        assertThat(todoDto.get(0).todoItem()).isEqualTo("할일");
    }

    @DisplayName("특정 카드에 속한 모든 todo를 조회할 때 todo가 없으면 예외를 발생시킨다.")
    @Test
    void getAllTodos_Fail_WhenTodoNotExist() {

        when(todoRepository.getAllTodos(anyInt())).thenReturn(new ArrayList<>()); // todo가 없는 경우, 빈 리스트 반환
        assertThrows(TodoException.class, () -> todoService.getAllTodos(anyInt()));
    }

    @DisplayName("특정 카드에 속한 특정 todo를 조회한다.")
    @Test
    void readTodo_Success() {

        // given
        when(todoRepository.existsByTodoId(1, 1)).thenReturn(true);
        when(todoRepository.findByTodoId(1, 1)).thenReturn(todo);

        // when
        readCommand = new ReadTodoCommand(1, 1);
        TodoDto todoDto = todoService.readTodo(readCommand);

        // then
        verify(todoRepository).findByTodoId(1, 1);
        assertThat(todoDto.todoItem()).isEqualTo("할일");
    }

    @DisplayName("특정 카드에 속한 특정 todo를 조회할 때 todo가 없으면 예외를 발생시킨다.")
    @Test
    void readTodo_Fail_WhenTodoNotExist() {

        when(todoRepository.existsByTodoId(1, 1)).thenReturn(false);
        assertThrows(TodoException.class, () -> todoService.checkTodoExistence(1, 1));
    }
}
