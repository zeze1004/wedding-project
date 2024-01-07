package project.wedding.domain;

import lombok.Getter;

@Getter
public class Todo {
    private final String todoId;
    private String todoDescription;
    private boolean todoCheckStatus; // todoCheckStatus가 true면 todo 완료, false면 미완료

    public Todo(String todoId, String todoDescription) {
        this.todoId = todoId;
        this.todoDescription = todoDescription;
        this.todoCheckStatus = false;
    }

    public void modifyTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }

    // 체크박스 체크 여부 변경
    public void changeCheckStatus() {
        this.todoCheckStatus = !this.todoCheckStatus;
    }

    public String toString() {
        return """
            Todo{
                todoId='%s',
                todoDescription='%s',
                checkStatus='%s'
            }
            """.formatted(todoId, todoDescription, todoCheckStatus);
    }
}
