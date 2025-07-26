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

@WebServlet("/CharacterServlet")//クラス名と同じ
public class CharacterServlet extends HttpServlet {//HTTP通信を処理するクラスを継承
    //doGetメソッドを追加し、メソッドの呼び出し元であるHttpServletに例外の伝播をする
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


        session.setAttribute("party_" +(dataPos+1), afterParty);
        session.setAttribute("enemy_" +(dataPos+1), afterEnemy);
        session.setAttribute("charIndex_" +(dataPos+1) , charIndex);


        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();

        out.println("<html><body>");

        //エンティティの状態表示
        out.println("---味方パーティ---<br><br>");
        for (Character member : afterParty) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("---敵グループ---<br><br>");
        for (Monster member : afterEnemy) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("<hr>");


        Character actChar = afterParty.get(charIndex);
        out.println(actChar.getName() + "の行動");
        out.println("<form action=\"BattleServlet\">");
        out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"character\">");
        out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");

        if (actChar instanceof SuperHero) {

            out.println("<select name=\"actIndex\">");
            out.println("<option value=\"attack\">攻撃</option>");
            out.println("</select>");

        } else if(actChar instanceof Hero) {

            out.println("<select name=\"actIndex\">");
            out.println("<option value=\"attack\">攻撃</option>");
            out.println("<option value=\"superHero\">スーパーヒーローになる</option>");
            out.println("</select>");

        } else if (actChar instanceof Wizard) {

            out.println("<select name=\"actIndex\">");
            out.println("<option value=\"attack\">攻撃</option>");
            out.println("<option value=\"magic\">魔法攻撃</option>");
            out.println("</select>");

        } else if (actChar instanceof Thief) {

            out.println("<select name=\"actIndex\">");
            out.println("<option value=\"attack\">攻撃</option>");
            out.println("<option value=\"guard\">守り</option>");
            out.println("</select>");

        }
        out.println("誰に");

        out.println("<select name=\"targetIndex\">");
        int i =0;
        for (Monster select :afterEnemy) {
            out.println("<option value=\"" + i + "\">" + select.getName() + select.getSuffix() + "</option>");
            i++;
        }
        out.println("</select>");

        out.println("<button type=\"submit\">決定</button>");
        out.println("</form>");

        //out.println(names.get(targetIndex) + "を選択しました");
        out.println("</body></html>");
    }
}