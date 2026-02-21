<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ToDo Login</title>
    <link rel="stylesheet" href="/style.css?v=2">
</head>
<body>
    <div class="login-container">
        <h2>ToDoアプリ ログイン</h2>

        <%-- もしログイン失敗などでエラーメッセージが渡されてきたら、ここに赤字で表示する --%>
        <%
            String errorMsg = (String) request.getAttribute("errorMessage");
            if (errorMsg != null) {
        %>
            <p style="color: red; font-weight: bold;"><%= errorMsg %></p>
        <%
            }
        %>

        <form action="/login" method="POST">
            <div class="form-group">
                <label>ログインID:</label>
                <input type="text" name="userId" required>
            </div>
            <div class="form-group">
                <label>パスワード:</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit">ログイン</button>
        </form>
    </div>
</body>
</html>