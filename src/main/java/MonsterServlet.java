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

        out.println("敵のターン");
        for(Monster actEm: afterEnemy) {
            int hitChar = (int)(Math.random() * afterParty.size());
            actEm.attack(out,afterParty.get(hitChar));
            if (damageShock(out,afterParty.get(hitChar))) {
                afterParty.remove(hitChar);
            }
            out.println("</p>");
            if (afterParty.isEmpty()) {//要警戒-->途中でWipeOut
                break;
            }
        }
        //状態の保存
        session.setAttribute("party_" +(dataPos+1), afterParty);
        session.setAttribute("enemy_" +(dataPos+1), afterEnemy);
        session.setAttribute("charIndex_" +(dataPos+1), 0);

        //エンティティの状態表示
        out.println("<hr>");
        out.println("---味方パーティ---<br><br>");
        for (Character member : afterParty) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("---敵グループ---<br><br>");
        for (Monster member : afterEnemy) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("<hr>");
        if (afterParty.isEmpty()) {
            out.println("<form action=\"BattleEndServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"battle\">");
            out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");
            out.println("<button type=\"submit\">そして…</button>");
            out.println("</form>");
        }else {
            out.println("<form action=\"CharacterServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"monster\">");
            out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");
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