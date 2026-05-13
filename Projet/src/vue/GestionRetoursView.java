package vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import javax.swing.border.CompoundBorder;

public class GestionRetoursView extends JFrame {

    private JButton btnValider;
    private JTextField txtId; 

    private final Color BLEU_PROFOND = new Color(11, 17, 30);
    private final Color OR_ANTIQUE = new Color(212, 175, 55);
    private final Color BLEU_CARTE = new Color(22, 34, 47);

    public GestionRetoursView() {
        setTitle("Gestion des Retours");
        setSize(550, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(BLEU_PROFOND);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BLEU_CARTE);
        card.setBorder(new CompoundBorder(new LineBorder(OR_ANTIQUE, 1), new EmptyBorder(30, 40, 30, 40)));

        JLabel title = new JLabel("RETOUR DE DOCUMENT");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(OR_ANTIQUE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel("Veuillez saisir l'identifiant (ID) du prêt :");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtId = new JTextField();
        txtId.setMaximumSize(new Dimension(220, 40));
        txtId.setBackground(BLEU_PROFOND);
        txtId.setForeground(OR_ANTIQUE);
        txtId.setFont(new Font("Consolas", Font.BOLD, 22));
        txtId.setCaretColor(OR_ANTIQUE);
        txtId.setHorizontalAlignment(SwingConstants.CENTER);
        txtId.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(OR_ANTIQUE, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));

        btnValider = new JButton("CONFIRMER LE RETOUR");
        btnValider.setMaximumSize(new Dimension(250, 42));
        btnValider.setBackground(OR_ANTIQUE);
        btnValider.setForeground(Color.BLACK);
        btnValider.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnValider.setFocusPainted(false);
        btnValider.setBorder(BorderFactory.createEmptyBorder());
        btnValider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnValider.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnValider.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnValider.setBackground(OR_ANTIQUE.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnValider.setBackground(OR_ANTIQUE);
            }
        });

        card.add(title);
        card.add(Box.createVerticalStrut(25));
        card.add(lbl);
        card.add(Box.createVerticalStrut(15));
        card.add(txtId);
        card.add(Box.createVerticalStrut(30));
        card.add(btnValider);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(card);

        add(wrapper, BorderLayout.CENTER);
    }

    public void viderChamp() {
        txtId.setText(""); 
        txtId.requestFocus();
    } 

    public JButton getBtnValider() { return btnValider; }

    // --- MODIFICATION ICI : On renforce la clarté du nom ---
    public String getIdPret() { return txtId.getText(); }

    // On garde l'ancienne pour ne pas casser le contrôleur si tu n'as pas encore changé l'appel
    public String getIdDocument() { return txtId.getText(); } 
}