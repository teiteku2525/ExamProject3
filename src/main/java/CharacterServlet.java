import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/CharacterServlet")//クラス名と同じ
public class CharacterServlet extends HttpServlet {//HTTP通信を処理するクラスを継承
    //doGetメソッドを追加し、メソッドの呼び出し元であるHttpServletに例外の伝播をする
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        //javaで実行する内容
        HttpSession session = request.getSession();
        //ArrayList<String> names = (ArrayList<String>) session.getAttribute("names");//sessionを使ってHelloServletで保存したnamesをサーバから取得する
        int ttest =  (Integer) session.getAttribute("test");

        //int targetIndex = Integer.parseInt(request.getParameter("targetIndex"));//intに変換する(NumberFormatExceptionが発生する可能性あり)
        session.setAttribute("names", names);//変更した内容を再度保存する
        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println(ttest);
        for (String name : names) {
            out.println(name + "<br>");
        }
        out.println("の中から");
        //out.println(names.get(targetIndex) + "を選択しました");
        out.println("</body></html>");
    }
}