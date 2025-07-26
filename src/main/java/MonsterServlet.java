import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import creature.Character;
import creature.Monster;
import creature.character.Hero;
import creature.character.SuperHero;
import creature.character.Thief;
import creature.character.Wizard;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@SuppressWarnings("unchecked") //ArrayListの型が正しいと判断 +try-catchで対策済みとして使用

@WebServlet("/MonsterServlet")//クラス名と同じ
public class MonsterServlet extends HttpServlet {//HTTP通信を処理するクラスを継承
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        //javaで実行する内容

        HttpSession session = request.getSession();//sessionを使ってHelloServletで保存したnamesをサーバから取得する
        ArrayList<Character> party = null;
        ArrayList<Monster> enemy = null;
        int charIndex = -1;
        try {
            party = (ArrayList<Character>) session.getAttribute("partyB");
            enemy =  (ArrayList<Monster>) session.getAttribute("enemyB");
            charIndex = (Integer) session.getAttribute("charIndexB");
        } catch (ClassCastException e ) {
            System.out.println("データの受け取りに失敗:" + e);
        }
        if (party == null || enemy == null || charIndex < 0) {
            throw new NullPointerException("データの受け取りに失敗したため、動作を停止します");
        }

        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println("敵のターン");
        for(Monster actEm: enemy) {
            int hitChar = (int)(Math.random() * party.size());
            actEm.attack(out,party.get(hitChar));
            if (damageShock(out,party.get(hitChar))) {
                party.remove(hitChar);
            }
            out.println("</p>");
            if (party.isEmpty()) {//要警戒-->途中でWipeOut
                break;
            }
        }
        //状態の保存
        session.setAttribute("partyA", party);
        session.setAttribute("enemyA", enemy);
        session.setAttribute("charIndexA", 0);

        //エンティティの状態表示
        out.println("<hr>");
        out.println("---味方パーティ---<br><br>");
        for (Character member : party) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("---敵グループ---<br><br>");
        for (Monster member : enemy) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("<hr>");
        if (party.isEmpty()) {
            out.println("<form action=\"BattleEndServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"battle\">");
            out.println("<button type=\"submit\">そして…</button>");
            out.println("</form>");
        }else {
            out.println("<form action=\"CharacterServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"monster\">");
            out.println("<button type=\"submit\">次のラウンドへ</button>");
            out.println("</form>");
        }

        out.println("</body></html>");
    }

    private static boolean damageShock(PrintWriter out,Character character) {
        if (character.getHp() <= 0) {
            character.die(out);
            return true;
        }
        return false;
    }
}