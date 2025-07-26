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

@WebServlet("/BattleServlet")//クラス名と同じ
public class BattleServlet extends HttpServlet {//HTTP通信を処理するクラスを継承
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

        String actIndex = request.getParameter("actIndex");
        int targetIndex = Integer.parseInt(request.getParameter("targetIndex"));





        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");


        if (afterParty.get(charIndex) instanceof SuperHero) {
            afterParty.get(charIndex).attack(out,afterEnemy.get(targetIndex));
            if (damageShock(out,afterEnemy.get(targetIndex))) {
                afterEnemy.remove(targetIndex);
            }
        }else if(afterParty.get(charIndex) instanceof Hero){
            switch (actIndex) {
                case "attack" :
                    afterParty.get(charIndex).attack(out,afterEnemy.get(targetIndex));
                    if (damageShock(out,afterEnemy.get(targetIndex))) {
                        afterEnemy.remove(targetIndex);
                    }
                    break;
                case "superHero" :
                    out.println(afterParty.get(charIndex).getName() + "はスーパーヒーローに進化した！");
                    out.println(afterParty.get(charIndex).getName() + "は力を開放する代償として30のダメージを受けた！");
                    if (afterParty.get(charIndex).getHp() <= 30) {
                        afterParty.get(charIndex).die(out);
                        afterEnemy.remove(charIndex);
                    }else {
                        SuperHero sHero = new SuperHero((Hero) afterParty.get(charIndex));
                        afterParty.set(0, sHero); //ここでジョブチェン
                    }
                    break;
            }
        }else if(afterParty.get(charIndex) instanceof Wizard){
            switch (actIndex) {
                case "attack" :
                    afterParty.get(charIndex).attack(out,afterEnemy.get(targetIndex));
                    if (damageShock(out,afterEnemy.get(targetIndex))) {
                        afterEnemy.remove(targetIndex);
                    }
                    break;
                case "magic" :
                    ((Wizard) afterParty.get(charIndex)).magic(out,afterEnemy.get(targetIndex));
                    if (damageShock(out,afterEnemy.get(targetIndex))) {
                        afterEnemy.remove(targetIndex);
                    }
                    break;
            }
        }else if(afterParty.get(charIndex) instanceof Thief){
            switch (actIndex) {
                case "attack" :
                    afterParty.get(charIndex).attack(out,afterEnemy.get(targetIndex));
                    if (damageShock(out,afterEnemy.get(targetIndex))) {
                        afterEnemy.remove(targetIndex);
                    }
                    break;
                case "guard" :
                    ((Thief) afterParty.get(charIndex)).guard(out);
                    break;
            }
        }

        //状態の保存
        session.setAttribute("party_" +(dataPos+1), afterParty);
        session.setAttribute("enemy_" +(dataPos+1), afterEnemy);
        session.setAttribute("charIndex_" +(dataPos+1) , ++charIndex);

        session.setAttribute("test", 0);

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
        if (afterEnemy.isEmpty()) {
            out.println("<form action=\"BattleEndServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"battle\">");
            out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");
            out.println("<input type=\"hidden\" name=\"result\" value=\"win\">");
            out.println("<button type=\"submit\">そして…！</button>");
            out.println("</form>");
        }else if (afterParty.isEmpty()) {
            out.println("<form action=\"BattleEndServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"battle\">");
            out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");
            out.println("<input type=\"hidden\" name=\"result\" value=\"lose\">");
            out.println("<button type=\"submit\">そして…</button>");
            out.println("</form>");
        }else if (charIndex < afterParty.size()) {
            out.println("<form action=\"CharacterServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"battle\">");
            out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");
            out.println("<button type=\"submit\">次のキャラクターへ</button>");
            out.println("</form>");
        }else {
            out.println("<form action=\"MonsterServlet\">");
            out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"battle\">");
            out.println("<input type=\"hidden\" name=\"dataPos\" value=\"" + (dataPos+1) + "\">");
            out.println("<button type=\"submit\">敵のターンへ進む</button>");
            out.println("</form>");
        }
        out.println("<form action=\"test\">");
        out.println("<button type=\"submit\">test</button>");
        out.println("</form>");

        out.println("</body></html>");
    }

    private static boolean damageShock(PrintWriter out,Monster monster) {//敵の削除は行わないよ

        if (monster.getHp() <= 0) {
            out.println(monster.die());
            return true;
        }else if(monster.getHp() <= 5) {
            out.println(monster.run());
            return true;
        }
        return false;
    }
}
