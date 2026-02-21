import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// main.jspの完了ボタンの送信先（action="/todo/complete"）
@WebServlet("/todo/complete")
public class CompleteTodoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // フォームから隠し項目(hidden)で送られてきたタスクのIDを取得
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr); // 文字列を数値に変換

            try {
                Class.forName("org.postgresql.Driver");
                // 環境変数が設定されていればそれを使い、なければローカルの設定を使う
                String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:postgresql://db:5432/todo_db";
                String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "todo_user";
                String pass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "todo_password";

                // UPDATE文で、指定されたIDのタスクだけ is_completed を TRUE に書き換える
                try (Connection conn = DriverManager.getConnection(url, user, pass);
                     PreparedStatement stmt = conn.prepareStatement(
                             "UPDATE tasks SET is_completed = TRUE WHERE id = ?")) {

                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 更新が終わったら、ToDo一覧画面(/todo)にリダイレクトして最新の状態を表示させる
        response.sendRedirect("/todo");
    }
}