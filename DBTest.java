import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mysql";
        String user = "root";
        String pass = "31447713796asdfg"; // buraya kendi root şifreni yaz

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("BAGLANTI BASARILI");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

