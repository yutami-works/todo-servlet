<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.todo.dto.TaskDto" %>
<%
    // Servletから渡されたデータを受け取る
    List<TaskDto> taskList = (List<TaskDto>) request.getAttribute("taskList");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ToDoリスト</title>
    <link rel="stylesheet" href="/style.css?v=2">
</head>
<body>
    <div class="todo-container">
        <h2>現在のToDo</h2>

        <ul class="task-list">
            <%
                if (taskList != null && !taskList.isEmpty()) {
                    for (TaskDto task : taskList) {
                        // ゲッターメソッドを使ってデータを取得します
                        int id = task.getId();
                        String name = task.getTaskName();
                        int priority = task.getPriority();
                        boolean isCompleted = task.getIsCompleted();

                        // 優先度の表示文字
                        String priorityStr = priority == 1 ? "高" : (priority == 2 ? "中" : "低");

                        // 完了状態によるCSSクラスの切り替え
                        String liClass = isCompleted ? "task-completed" : "";
            %>
                        <li class="<%= liClass %>">
                            <span class="priority p<%= priority %>">[<%= priorityStr %>]</span>
                            <span class="task-name"><%= name %></span>

                            <% if (!isCompleted) { %>
                                <form action="/todo/complete" method="POST" style="display:inline; margin-left:auto;">
                                    <input type="hidden" name="id" value="<%= id %>">
                                    <button type="submit" class="complete-btn">完了</button>
                                </form>
                            <% } %>
                        </li>
            <%
                    }
                } else {
            %>
                <li>タスクはありません。</li>
            <%
                }
            %>
        </ul>

        <hr>

        <h3>新規ToDo登録</h3>
        <form action="/todo/add" method="POST" class="add-form">
            <input type="text" name="taskName" placeholder="タスクを入力" required>
            <select name="priority">
                <option value="1">高</option>
                <option value="2" selected>中</option>
                <option value="3">低</option>
            </select>
            <button type="submit">登録</button>
        </form>
    </div>
</body>
</html>