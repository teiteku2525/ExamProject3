import java.io.*;
import java.util.ArrayList;

import creature.Character;
import creature.Monster;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@SuppressWarnings("unchecked") //ArrayListの型が正しいと判断 +try-catchで対策済みとして使用

@WebServlet("/BattleEndServlet")//クラス名と同じ
public class BattleEndServlet extends HttpServlet {//HTTP通信を処理するクラスを継承
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        //javaで実行する内容

        HttpSession session = request.getSession();//sessionを使ってHelloServletで保存したnamesをサーバから取得する
        ArrayList<Character> party = null;
        ArrayList<Monster> enemy = null;

        String result = request.getParameter("result");
        String source = request.getParameter("beforeServlet");
        if (source.equals("battle")) {
            try {
                party = (ArrayList<Character>) session.getAttribute("partyB");
                enemy =  (ArrayList<Monster>) session.getAttribute("enemyB");
            } catch (ClassCastException e ) {
                System.out.println("データの受け取りに失敗:" + e);
            }
        }else if (source.equals("character")) {
            try {
                party = (ArrayList<Character>) session.getAttribute("partyA");
                enemy =  (ArrayList<Monster>) session.getAttribute("enemyA");
            } catch (ClassCastException e ) {
                System.out.println("データの受け取りに失敗:" + e);
            }
        }


        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (result.equals("win")) {
            if (party == null) {
                throw new NullPointerException("データの受け取りに失敗したため、動作を停止します");
            }
            out.println("敵を全て倒した！" + party.getFirst().getName() + "達は勝利した！");
        }else if(result.equals("lose")) {
            out.println("味方パーティは全滅してしまった");
        }
        out.println("</body></html>");
    }
}