import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import manager.MasaManager;
import manager.SessionManager;
import model.Masa;

public class MainFrame extends JFrame {

    private JLabel lblGelir;
    private JLabel lblBilgi;

    private MasaManager masaManager;
    private SessionManager sessionManager;

    private double toplamGelir = 0;

    public MainFrame() {
        setTitle("İnternet Kafe Yönetim Sistemi");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        masaManager = new MasaManager();
        sessionManager = new SessionManager();

        initMasalar();
        initUI();
    }

    private void initMasalar() {
        for (int i = 1; i <= 10; i++) {
            masaManager.masaEkle(new Masa(i, "Masa " + i));
        }
    }

    private void initUI() {

        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        lblGelir = new JLabel("Toplam Gelir: 0 TL", SwingConstants.CENTER);
        lblGelir.setFont(new Font("Arial", Font.BOLD, 18));
        lblGelir.setForeground(new Color(0, 128, 0));

        
        JPanel masaPanel = new JPanel(new GridLayout(2, 5, 15, 15));

        for (Masa masa : masaManager.getMasalar()) {
            JButton btnMasa = new JButton(masa.getMasaAdi());
            btnMasa.setFont(new Font("Arial", Font.BOLD, 14));
            btnMasa.setBackground(Color.LIGHT_GRAY);
            btnMasa.setFocusPainted(false);

            btnMasa.addActionListener(e -> masaTiklandi(masa, btnMasa));

            masaPanel.add(btnMasa);
        }

        
        lblBilgi = new JLabel("Hazır", SwingConstants.CENTER);
        lblBilgi.setFont(new Font("Arial", Font.PLAIN, 14));

        mainPanel.add(lblGelir, BorderLayout.NORTH);
        mainPanel.add(masaPanel, BorderLayout.CENTER);
        mainPanel.add(lblBilgi, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void masaTiklandi(Masa masa, JButton btn) {

        if (masa.getDurum().name().equals("BOS")) {
            sessionManager.masaAc(masa);
            btn.setBackground(Color.GREEN);
            lblBilgi.setText(masa.getMasaAdi() + " açıldı");

        } else {
            double ucret = sessionManager.masaKapat(masa);
            toplamGelir += ucret;

            btn.setBackground(Color.LIGHT_GRAY);
            lblBilgi.setText(masa.getMasaAdi() + " kapatıldı - " + String.format("%.2f", ucret) + " TL");

            lblGelir.setText("Toplam Gelir: " + String.format("%.2f", toplamGelir) + " TL");
        }
    }
}
