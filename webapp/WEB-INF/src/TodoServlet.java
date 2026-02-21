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

// 作成したTaskDtoクラスをインポートして使えるようにします
import com.todo.dto.TaskDto;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<TaskDto> taskList = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            // 環境変数が設定されていればそれを使い、なければローカルの設定を使う
            String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:postgresql://db:5432/todo_db";
            String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "todo_user";
            String pass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "todo_password";

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks ORDER BY priority ASC, id ASC");
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    TaskDto task = new TaskDto();
                    // セッターを使ってデータを格納します
                    task.setId(rs.getInt("id"));
                    task.setTaskName(rs.getString("task_name"));
                    task.setPriority(rs.getInt("priority"));
                    task.setIsCompleted(rs.getBoolean("is_completed"));

                    taskList.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }
}