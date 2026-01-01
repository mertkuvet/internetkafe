import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblMessage;

    public LoginFrame() {
        setTitle("Admin Login");
        setSize(360, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);

        JButton btnLogin = new JButton("Giriş");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(100, 40));

        btnLogin.addActionListener(e -> checkLogin());

        formPanel.add(new JLabel("Kullanıcı Adı"));
        formPanel.add(txtUsername);
        formPanel.add(new JLabel("Şifre"));
        formPanel.add(txtPassword);

        mainPanel.add(lblMessage, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnLogin, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // 🔹 Login kontrol
    public void checkLogin() {
        String username = getUsername();
        String password = getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Alanlar boş bırakılamaz");
            return;
        }

        if (validateUser(username, password)) {
            new MainFrame().setVisible(true);
            this.dispose();
        } else {
            lblMessage.setText("Hatalı giriş");
        }
    }

    // 🔹 DB kontrol
    private boolean validateUser(String u, String p) {
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Getter'lar
    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
}
