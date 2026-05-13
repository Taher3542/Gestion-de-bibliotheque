package vue;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtPass;
    private JButton btnConnecter;

    private final Color BLEU_PROFOND = new Color(11, 17, 30); 
    private final Color OR_ANTIQUE = new Color(212, 175, 55); 
    private final Color BLEU_CARTE = new Color(22, 34, 47);  

    public LoginView() {
        setTitle("Connexion - Bibliothèque ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 700);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        getContentPane().setBackground(BLEU_PROFOND);

       
        JPanel loginBlock = new JPanel() {
            
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BLEU_CARTE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        loginBlock.setOpaque(false);
        loginBlock.setPreferredSize(new Dimension(420, 500));
        loginBlock.setLayout(new BoxLayout(loginBlock, BoxLayout.Y_AXIS));
        loginBlock.setBorder(new EmptyBorder(40, 45, 40, 45));

       
        JLabel lblLogo = new JLabel("📖");
        lblLogo.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        lblLogo.setForeground(OR_ANTIQUE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("ACCEDER A VOTRE ESPACE");
        lblTitle.setFont(new Font("Georgia", Font.BOLD, 22));
        lblTitle.setForeground(OR_ANTIQUE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Bibliotheque Virtuelle");
        lblSubtitle.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblSubtitle.setForeground(new Color(150, 160, 175));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

       
        txtLogin = new JTextField();
        txtPass = new JPasswordField();
        styleField(txtLogin);
        styleField(txtPass);

        btnConnecter = new JButton("SE CONNECTER");
        btnConnecter.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnConnecter.setBackground(OR_ANTIQUE);
        btnConnecter.setForeground(Color.BLACK);
        btnConnecter.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnConnecter.setFocusPainted(false);
        btnConnecter.setBorder(BorderFactory.createEmptyBorder());
        btnConnecter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConnecter.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBlock.add(lblLogo);
        loginBlock.add(Box.createVerticalStrut(15));
        loginBlock.add(lblTitle);
        loginBlock.add(lblSubtitle);
        loginBlock.add(Box.createVerticalStrut(35));

        loginBlock.add(createLabel("IDENTIFIANT"));
        loginBlock.add(Box.createVerticalStrut(5));
        loginBlock.add(txtLogin);
        loginBlock.add(Box.createVerticalStrut(20));

        loginBlock.add(createLabel("MOT DE PASSE"));
        loginBlock.add(Box.createVerticalStrut(5));
        loginBlock.add(txtPass);
        loginBlock.add(Box.createVerticalStrut(35));

        loginBlock.add(btnConnecter);

        add(loginBlock);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(OR_ANTIQUE);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBackground(new Color(13, 22, 33));
        field.setForeground(Color.WHITE);
        field.setCaretColor(OR_ANTIQUE);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(OR_ANTIQUE, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }

    public String getUsername() { return txtLogin.getText(); }
    public String getPassword() { return new String(txtPass.getPassword()); }
    public JButton getBtnConnecter() { return btnConnecter; }
}