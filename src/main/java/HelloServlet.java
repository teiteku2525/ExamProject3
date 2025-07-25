import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        //javaで実行する内容
        HttpSession session = request.getSession();//getSessionは、サーバに値を保存することで、どのクラスからでも呼び出せるようにする
        ArrayList<String> names = new ArrayList<String>();
        names.add("勇者");
        names.add("魔法使い");
        names.add("盗賊");
        session.setAttribute("names", names);//namesをサーバに保存
        session.setAttribute("test", 5);
        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<form action=\"SelectServlet\">");
        out.println("誰に攻撃しますか？");
        out.println("<input type=\"text\" name=\"targetIndex\">");
        out.println("<button type=\"submit\">スタート</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}