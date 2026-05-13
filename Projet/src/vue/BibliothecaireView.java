package vue;

import javax.swing.*;


import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BibliothecaireView extends JFrame {

    private JButton btnDocs, btnAdherents, btnPrets, btnRetours, btnDeconnexion;

    private final Color BLEU_PROFOND = new Color(11, 17, 30); 
    private final Color OR_ANTIQUE = new Color(212, 175, 55); 
    private final Color ROUGE_CARDINAL = new Color(166, 58, 80);

    public BibliothecaireView() {
        setTitle("Administration  - Tableau de Bord");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(BLEU_PROFOND);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(25, 35, 15, 35));

        JLabel title = new JLabel("TABLEAU DE BORD ADMINISTRATION");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(OR_ANTIQUE);

        btnDeconnexion = new JButton("SE DECONNECTER");
        btnDeconnexion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnDeconnexion.setForeground(Color.WHITE);
        btnDeconnexion.setBackground(ROUGE_CARDINAL);
        btnDeconnexion.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnDeconnexion.setFocusPainted(false);
        btnDeconnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        header.add(title, BorderLayout.WEST);
        header.add(btnDeconnexion, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(new EmptyBorder(20, 35, 35, 35));

        btnDocs = createMenuButton("📖", "CATALOGUE DES DOCUMENTS");
        btnAdherents = createMenuButton("👥", "RÉPERTOIRE DES ADHÉRENTS");
        btnPrets = createMenuButton("📤", "REGISTRE DES EMPRUNT");
        btnRetours = createMenuButton("📥", "RESTITUER UN DOCUMENT");

        gridPanel.add(btnDocs);
        gridPanel.add(btnAdherents);
        gridPanel.add(btnPrets);
        gridPanel.add(btnRetours);

        add(gridPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String icon, String text) {
        JButton btn = new JButton("<html><center>"
                + "<div style='font-size:32px; margin-bottom:5px;'>" + icon + "</div>"
                + "<div style='font-size:14px; font-weight:bold; letter-spacing:1px;'>" + text + "</div>"
                + "</center></html>");

        btn.setBackground(new Color(22, 34, 47));
        btn.setForeground(OR_ANTIQUE);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(OR_ANTIQUE, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(OR_ANTIQUE);
                btn.setForeground(Color.BLACK);
            }
            
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(22, 34, 47));
                btn.setForeground(OR_ANTIQUE);
            }
        });

        return btn;
    }

    public JButton getBtnDocs() { return btnDocs; }
    public JButton getBtnAdherents() { return btnAdherents; }
    public JButton getBtnPrets() { return btnPrets; }
    public JButton getBtnRetours() { return btnRetours; }
    public JButton getBtnDeconnexion() { return btnDeconnexion; }
}