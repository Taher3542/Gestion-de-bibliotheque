package vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class GestionPretsView extends JFrame {

    private final Color BLEU_PROFOND = new Color(11, 17, 30); 
    private final Color OR_ANTIQUE = new Color(212, 175, 55); 
    private final Color BLEU_CHAMP = new Color(22, 34, 47);
    private final Color VERT_RENDU = new Color(76, 175, 80);
    private final Color BLEU_JOURS = new Color(66, 165, 245);

    private JButton  btnFermer;
    private JTable tablePrets;
    private DefaultTableModel model;

    public GestionPretsView() {
        setTitle("Registre des Flux d'Emprunt");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BLEU_PROFOND);
        setLayout(new BorderLayout(15, 15));

        JLabel lblTitre = new JLabel(" HISTORIQUE DES PRÊTS", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Georgia", Font.BOLD, 22));
        lblTitre.setForeground(OR_ANTIQUE);
        lblTitre.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(lblTitre, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(20, 20));
        panelCentral.setOpaque(false);
        panelCentral.setBorder(new EmptyBorder(10, 30, 10, 30));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        formPanel.setOpaque(false);


       

        String[] columns = {"ID Prêt", "ID Adhérent", "ID Document", "Date d'Emprunt", "Retour Prévu", "État du Prêt"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePrets = new JTable(model);
        stylizeTable(tablePrets);
        tablePrets.setDefaultRenderer(Object.class, new ColoredTableRenderer());

        JScrollPane scroll = new JScrollPane(tablePrets);
        scroll.setBorder(new LineBorder(OR_ANTIQUE, 1));
        scroll.getViewport().setBackground(BLEU_PROFOND);
        panelCentral.add(scroll, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBas.setOpaque(false);
        panelBas.setBorder(new EmptyBorder(0, 0, 20, 30));

        btnFermer = new JButton("FERMER LE SERVICE");
        stylePrestigeButton(btnFermer, new Color(50, 60, 75));
        panelBas.add(btnFermer);

        add(panelBas, BorderLayout.SOUTH);
    }


    private class ColoredTableRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (column == 5) {
                if (!isSelected) {
                    String etat = (String) value; // La valeur de la colonne État
                    
                    if ("Rendu avant la date".equals(etat)) {
                        c.setBackground(VERT_RENDU);
                    } else if ("Retard détecté".equals(etat) || 
                               (etat != null && etat.startsWith("Date limite de retour dépassée"))) {
                        c.setBackground(new Color(244, 67, 54)); // Rouge
                    } else if (etat != null && etat.startsWith("Jours restants")) {
                        c.setBackground(BLEU_JOURS);
                    } else {
                        c.setBackground(BLEU_CHAMP);
                    }
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(OR_ANTIQUE);
                    c.setForeground(Color.BLACK);
                }
            } else {
                if (!isSelected) {
                    c.setBackground(BLEU_CHAMP);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(OR_ANTIQUE);
                    c.setForeground(Color.BLACK);
                }
            }
            
            return c;
        }
    }
    private void stylePrestigeButton(JButton b, Color bg) {
        b.setPreferredSize(new Dimension(180, 38));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBorder(new LineBorder(OR_ANTIQUE, 1));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void stylizeTable(JTable t) {
        t.setBackground(BLEU_CHAMP);
        t.setForeground(Color.WHITE);
        t.setRowHeight(35);
        t.getTableHeader().setBackground(OR_ANTIQUE);
        t.getTableHeader().setForeground(Color.BLACK);
        t.getTableHeader().setFont(new Font("Georgia", Font.BOLD, 13));
        t.setSelectionBackground(OR_ANTIQUE);
        t.setSelectionForeground(Color.BLACK);
        t.setGridColor(new Color(45, 55, 72));
    }

   
    public JButton getBtnFermer() { return btnFermer; }
    public DefaultTableModel getModel() { return model; }
}