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
        int charIndex = 0;

        HttpSession session = request.getSession();//sessionを使ってHelloServletで保存したnamesをサーバから取得する
        ArrayList<Character> party = null;
        ArrayList<Monster> enemy = null;
        try {
            String source = request.getParameter("beforeServlet");
            if (source.equals("battle")) {
                party = (ArrayList<Character>) session.getAttribute("partyB");
                enemy =  (ArrayList<Monster>) session.getAttribute("enemyB");
            }else if(source.equals("hello")){
                party = (ArrayList<Character>) session.getAttribute("party");
                enemy = (ArrayList<Monster>) session.getAttribute("enemy");
            }
        } catch (ClassCastException e ) {
            System.out.println("データの受け取りに失敗:" + e);
        }
        if (party == null || enemy == null) {
            throw new NullPointerException("データの受け取りに失敗したため、動作を停止します");
        }


        String source = request.getParameter("beforeServlet");
        if (source.equals("battle")) {
            charIndex = (Integer) session.getAttribute("charIndexB");
            }
        System.out.println(charIndex);




        //int ttest =  (Integer) session.getAttribute("test");
        //int targetIndex = Integer.parseInt(request.getParameter("targetIndex"));//intに変換する(NumberFormatExceptionが発生する可能性あり)
        //session.setAttribute("names", names);//変更した内容を再度保存する






        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();

        out.println("<html><body>");

        session.setAttribute("partyA", party);
        session.setAttribute("enemyA", enemy);
        session.setAttribute("charIndexA", charIndex);
        //エンティティの状態表示
        out.println("---味方パーティ---<br><br>");
        for (Character member : party) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("---敵グループ---<br><br>");
        for (Monster member : enemy) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("<hr>");


        Character actChar = party.get(charIndex);
        out.println(actChar.getName() + "の行動");
        out.println("<form action=\"BattleServlet\">");
        out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"character\">");

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
        for (Monster select :enemy) {
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