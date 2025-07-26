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



        String result = request.getParameter("result");
        String source = request.getParameter("beforeServlet");
        int dataPos = Integer.parseInt(request.getParameter("dataPos"));
        int charIndex = -1;
        ArrayList<Character> beforeParty = null;
        ArrayList<Character> afterParty = new ArrayList<Character>();
        ArrayList<Monster> beforeEnemy = null;
        ArrayList<Monster> afterEnemy = new ArrayList<Monster>();

        try {
            beforeParty = (ArrayList<Character>) session.getAttribute("party_" +dataPos);
            beforeEnemy =  (ArrayList<Monster>) session.getAttribute("enemy_"+dataPos);
            charIndex = (Integer) session.getAttribute("charIndex_"+dataPos);

        } catch (ClassCastException e ) {
            System.out.println("データの受け取りに失敗:" + e);
        }
        if (beforeParty == null || beforeEnemy == null) {
            throw new NullPointerException("データの受け取りに失敗したため、動作を停止します");
        }
        for (Character before :  beforeParty) {
            afterParty.add((Character) before.copy());
        }
        for (Monster before :  beforeEnemy) {
            afterEnemy.add((Monster) before.copy());
        }


        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (result.equals("win")) {
            out.println("敵を全て倒した！" + afterParty.getFirst().getName() + "達は勝利した！");
        }else if(result.equals("lose")) {
            out.println("味方パーティは全滅してしまった");
        }
        out.println("</body></html>");
    }
}