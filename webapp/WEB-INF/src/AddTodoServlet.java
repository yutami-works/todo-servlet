import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// main.jspのフォームの送信先（action="/todo/add"）と合わせます
@WebServlet("/todo/add")
public class AddTodoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 日本語の文字化けを防ぐ（超重要）
        request.setCharacterEncoding("UTF-8");

        // フォームから送信された値（name属性）を取得
        String taskName = request.getParameter("taskName");
        String priorityStr = request.getParameter("priority");

        // priorityは文字列として送られてくるので、数値(int)に変換
        int priority = 2; // デフォルトは中(2)
        if (priorityStr != null && !priorityStr.isEmpty()) {
            priority = Integer.parseInt(priorityStr);
        }

        // DBに登録する処理
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://db:5432/todo_db";
            String user = "todo_user";
            String pass = "todo_password";

            // INSERT文の ? は「後で安全に値を埋め込む場所（プレースホルダー）」です
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO tasks (task_name, priority, is_completed) VALUES (?, ?, FALSE)")) {

                // ? の部分に実際の値をセットする（SQLインジェクション対策）
                stmt.setString(1, taskName);
                stmt.setInt(2, priority);

                // SQLを実行してDBを書き換える
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 登録が終わったら、ToDo一覧画面(/todo)にリダイレクトして最新のリストを再表示させる
        response.sendRedirect("/todo");
    }
}