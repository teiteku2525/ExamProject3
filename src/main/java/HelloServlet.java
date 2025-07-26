import java.io.*;
import java.util.ArrayList;

import creature.Character;
import creature.Monster;
import creature.character.Hero;
import creature.character.Thief;
import creature.character.Wizard;
import creature.monster.Goblin;
import creature.monster.Matango;
import creature.monster.Slime;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import weapon.Dagger;
import weapon.Sword;
import weapon.Wand;
import weapon.Weapon;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        //javaで実行する内容
        HttpSession session = request.getSession();//getSessionは、サーバに値を保存することで、どのクラスからでも呼び出せるようにする



        //武器の宣言
        Weapon sword = new Sword();
        Weapon wand = new Wand();
        Weapon dagger = new Dagger();

        Hero hero = new Hero("勇者", 100, sword);
        Wizard wizard = new Wizard("魔法使い", 60, wand,40);
        Thief thief = new Thief("盗賊", 70, dagger);
        ArrayList<creature.Character> party = new ArrayList<Character>();
        party.add(hero);
        party.add(wizard);
        party.add(thief);

        Matango matango = new Matango('A', 45);
        Goblin goblin = new Goblin('A', 50);
        Slime slime = new Slime('A', 40);
        ArrayList<Monster> enemy = new ArrayList<Monster>();

        int matangoC = 0;
        int goblinC = 0;
        int slimecC = 0;

        for (int i = 0; i < 5; i++) {
            int choice = (int)(Math.random() * 3);
            switch (choice) {
                case 0:
                    enemy.add(new Matango((char)('A' + matangoC),45));
                    matangoC++;
                    break;
                case 1:
                    enemy.add(new Goblin((char)('A' + goblinC),50));
                    goblinC++;
                    break;
                case 2:
                    enemy.add(new Slime((char)('A' + slimecC),40));
                    slimecC++;
                    break;
            }
        }//敵集団生成完了





        //session.setAttribute("names", names);//namesをサーバに保存
        session.setAttribute("party", party);
        session.setAttribute("enemy", enemy);
        //ブラウザに表示する内容
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        //エンティティの状態表示
        out.println("---味方パーティ---<br><br>");
        for (Character member : party) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("---敵グループ---<br><br>");
        for (Monster member: enemy) {
            out.println(member.showStatus() + "<br><br>");
        }
        out.println("<hr>");

        out.println("<form action=\"CharacterServlet\">"); //遷移先の情報
        out.println("<input type=\"hidden\" name=\"beforeServlet\" value=\"hello\">");

        //out.println("<input type=\"text\" name=\"targetIndex\">");
        out.println("<button type=\"submit\">戦闘開始</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}