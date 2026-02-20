import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Task> taskList = new ArrayList<>();

        try {
            // 1. PostgreSQLのJDBCドライバを読み込む（古いJavaの作法）
            Class.forName("org.postgresql.Driver");

            // 2. DBへの接続情報（docker-compose.ymlで設定した値）
            String url = "jdbc:postgresql://db:5432/todo_db";
            String user = "todo_user";
            String pass = "todo_password";

            // 3. DBに接続し、SQLを実行する
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks ORDER BY priority ASC, id ASC");
                 ResultSet rs = stmt.executeQuery()) {

                // 4. 結果を1行ずつ取り出して Task オブジェクトに詰め、リストに追加する
                while (rs.next()) {
                    Task task = new Task();
                    task.id = rs.getInt("id");
                    task.taskName = rs.getString("task_name");
                    task.priority = rs.getInt("priority");
                    task.isCompleted = rs.getBoolean("is_completed");

                    taskList.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // エラー時はTomcatのログに出力
        }

        // 5. 取得したToDoリストをリクエストにセットして、main.jspに転送する
        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }
}