package vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class GestionAdherentView extends JFrame {

    private final Color BLEU_PROFOND = new Color(11, 17, 30); 
    private final Color OR_ANTIQUE = new Color(212, 175, 55); 
    private final Color BLEU_CHAMP = new Color(22, 34, 47);

    private JTable table;
    private DefaultTableModel model;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnEnregistrer, btnFermer;

    public GestionAdherentView() {
        setTitle("Registre  des Membres");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BLEU_PROFOND);
        setLayout(new BorderLayout(15, 15));

        JLabel lblTitre = new JLabel("RÉPERTOIRE DES ADHÉRENTS", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Georgia", Font.BOLD, 22));
        lblTitre.setForeground(OR_ANTIQUE);
        lblTitre.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(lblTitre, BorderLayout.NORTH);

        String[] columns = {"ID", "Nom", "Login", "Mot de passe", "Date Inscription"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(OR_ANTIQUE, 1));
        scroll.getViewport().setBackground(BLEU_PROFOND);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        pnlActions.setOpaque(false);
        pnlActions.setBorder(new EmptyBorder(10, 0, 20, 0));

        btnAjouter = createBtn("AJOUTER");
        btnModifier = createBtn("MODIFIER");
        btnSupprimer = createBtn("SUPPRIMER");
        btnEnregistrer = createBtn("ENREGISTRER");
        btnFermer = createBtn("FERMER");

        btnEnregistrer.setBackground(new Color(45, 122, 94));
        btnSupprimer.setBackground(new Color(166, 58, 80));
        btnFermer.setBackground(new Color(50, 60, 75));

        pnlActions.add(btnAjouter);
        pnlActions.add(btnModifier);
        pnlActions.add(btnSupprimer);
        pnlActions.add(btnEnregistrer);
        pnlActions.add(btnFermer);

        add(pnlActions, BorderLayout.SOUTH);
    }

    private void styleTable(JTable table) {
        table.setBackground(BLEU_CHAMP);
        table.setForeground(Color.WHITE);
        table.setRowHeight(36);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(45, 55, 72));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(OR_ANTIQUE);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Georgia", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 40));

        table.setSelectionBackground(OR_ANTIQUE);
        table.setSelectionForeground(Color.BLACK);
    }

    private JButton createBtn(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(140, 42));
        b.setBackground(BLEU_CHAMP);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 11));
        b.setBorder(new LineBorder(OR_ANTIQUE, 1));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnModifier() { return btnModifier; }
    public JButton getBtnSupprimer() { return btnSupprimer; }
    public JButton getBtnEnregistrer() { return btnEnregistrer; }
    public JButton getBtnFermer() { return btnFermer; }
    public DefaultTableModel getModel() { return model; }
    public JTable getTable() { return table; }
}