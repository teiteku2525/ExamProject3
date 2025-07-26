import java.io.*;
import java.util.ArrayList;

import creature.Character;
import creature.Monster;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@SuppressWarnings("unchecked") //ArrayListの型が正しいと判断 +try-catchで対策済みとして使用

@WebServlet("/test")//クラス名と同じ
public class test extends HttpServlet {//HTTP通信を処理するクラスを継承

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        //javaで実行する内容

        HttpSession session = request.getSession();//sessionを使ってHelloServletで保存したnamesをサーバから取得する
        int value = -1;
        value = (Integer) session.getAttribute("test");
        value += 1;
        session.setAttribute("test", value);
        ArrayList<Character> beforeParty = (ArrayList<Character>) session.getAttribute("party");
        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println(value);
        out.println("</body></html>");

    }
}