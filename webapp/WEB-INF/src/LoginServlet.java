import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// URLの "/login" にアクセスが来たらこのクラスを動かす、という目印です
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // POSTリクエスト（formの送信など）を受け取るメソッド
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JSPのformから送られてきた "userId" と "password" を受け取る
        request.setCharacterEncoding("UTF-8");
        String inputId = request.getParameter("userId");
        String inputPass = request.getParameter("password");

        // .env（環境変数）に設定した正解のIDとパスワードを取得する
        String correctId = System.getenv("APP_LOGIN_ID");
        String correctPass = System.getenv("APP_LOGIN_PASS");

        // 認証判定
        if (correctId != null && correctPass != null &&
            correctId.equals(inputId) && correctPass.equals(inputPass)) {

            // ログイン成功：メイン画面（main.jsp）へ移動
            response.sendRedirect("/todo");

        } else {

            // ログイン失敗：エラーメッセージをセットして、ログイン画面に戻す
            request.setAttribute("errorMessage", "IDまたはパスワードが間違っています。");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        }
    }
}