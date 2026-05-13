package vue;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AdherentView extends JFrame {

    private final Color BLEU_PROFOND   = new Color(11, 17, 30);
    private final Color OR_ANTIQUE     = new Color(212, 175, 55);
    private final Color BLEU_CHAMP     = new Color(22, 34, 47);
    private final Color ROUGE_CARDINAL = new Color(166, 58, 80);
    private final Color VERT_DISPO     = new Color(39, 174, 96);
    private final Color ROUGE_INDISPO  = new Color(192, 57, 43);

    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnDeconnexion;
    private JTable tableDocuments;
    private JTable tableMesEmprunts;
    private JTabbedPane tabbedPane;

    public AdherentView() {
        setTitle("Espace Membre — Bibliothèque ");
        setSize(1150, 780);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BLEU_PROFOND);

       
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(20, 25, 10, 25));

        JLabel title = new JLabel(" > BIBLIOTHÈQUE — ESPACE ADHÉRENT");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(OR_ANTIQUE);

        btnDeconnexion = new JButton("SE DECONNECTER");
        styleButton(btnDeconnexion, ROUGE_CARDINAL, Color.WHITE);

        header.add(title, BorderLayout.WEST);
        header.add(btnDeconnexion, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

       
        JSeparator sep = new JSeparator();
        sep.setForeground(OR_ANTIQUE);
        sep.setBackground(OR_ANTIQUE);
        add(sep, BorderLayout.NORTH);

      
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BLEU_PROFOND);
        tabbedPane.setForeground(OR_ANTIQUE);
        tabbedPane.setFont(new Font("Georgia", Font.BOLD, 13));
        tabbedPane.setBorder(new EmptyBorder(5, 15, 15, 15));

       
        JPanel tabCatalogue = new JPanel(new BorderLayout(10, 10));
        tabCatalogue.setBackground(BLEU_PROFOND);
        tabCatalogue.setBorder(new EmptyBorder(15, 10, 10, 10));

     
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        txtSearch = new JTextField();
        txtSearch.setBackground(BLEU_CHAMP);
        txtSearch.setForeground(Color.WHITE);
        txtSearch.setCaretColor(OR_ANTIQUE);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(OR_ANTIQUE, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));

        btnSearch = new JButton(">  RECHERCHER");
        styleButton(btnSearch, OR_ANTIQUE, Color.BLACK);

        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        tabCatalogue.add(searchPanel, BorderLayout.NORTH);

        
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        legendPanel.setOpaque(false);
        legendPanel.add(makeLegende("● Disponible", VERT_DISPO));
        legendPanel.add(makeLegende("● Indisponible", ROUGE_INDISPO));
        tabCatalogue.add(legendPanel, BorderLayout.AFTER_LINE_ENDS); 

        JPanel northWrap = new JPanel(new BorderLayout(0, 5));
        northWrap.setOpaque(false);
        northWrap.add(searchPanel, BorderLayout.NORTH);
        northWrap.add(legendPanel, BorderLayout.CENTER);
        tabCatalogue.add(northWrap, BorderLayout.NORTH);

        String[] colsCat = {"ID", "Titre", "Auteur", "Type", "Exemplaires", "Disponibilité"};
        DefaultTableModel modelCat = new DefaultTableModel(colsCat, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableDocuments = new JTable(modelCat);
        styleTable(tableDocuments);

       
        tableDocuments.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                JLabel lbl = new JLabel(val != null ? val.toString() : "", SwingConstants.CENTER);
                lbl.setOpaque(true);
                int exemplaires = (int) t.getValueAt(row, 4);
                if (exemplaires > 0) {
                    lbl.setBackground(VERT_DISPO);
                    lbl.setForeground(Color.WHITE);
                    lbl.setBorder(BorderFactory.createLineBorder(VERT_DISPO.darker(), 2));
                } else {
                    lbl.setBackground(ROUGE_INDISPO);
                    lbl.setForeground(Color.WHITE);
                    lbl.setBorder(BorderFactory.createLineBorder(ROUGE_INDISPO.darker(), 2));
                }
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                return lbl;
            }
        });

        JScrollPane scrollCat = new JScrollPane(tableDocuments);
        scrollCat.setBorder(new LineBorder(OR_ANTIQUE, 1));
        scrollCat.getViewport().setBackground(BLEU_PROFOND);
        tabCatalogue.add(scrollCat, BorderLayout.CENTER);

        JPanel footerCat = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerCat.setOpaque(false);
        tabCatalogue.add(footerCat, BorderLayout.SOUTH);

        tabbedPane.addTab(">  Catalogue des documents", tabCatalogue);

        
        JPanel tabEmprunts = new JPanel(new BorderLayout(10, 10));
        tabEmprunts.setBackground(BLEU_PROFOND);
        tabEmprunts.setBorder(new EmptyBorder(15, 10, 10, 10));

        JLabel lblEmprunts = new JLabel("Historique de vos emprunts", SwingConstants.LEFT);
        lblEmprunts.setFont(new Font("Georgia", Font.ITALIC, 14));
        lblEmprunts.setForeground(new Color(180, 190, 200));
        lblEmprunts.setBorder(new EmptyBorder(0, 0, 8, 0));
        tabEmprunts.add(lblEmprunts, BorderLayout.NORTH);

        String[] colsEmp = {"ID Prêt", "ID Document", "Titre", "Date d'emprunt", "Retour prévu", "Statut"};
        DefaultTableModel modelEmp = new DefaultTableModel(colsEmp, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableMesEmprunts = new JTable(modelEmp);
        styleTable(tableMesEmprunts);

        tableMesEmprunts.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                JLabel lbl = new JLabel(val != null ? val.toString() : "", SwingConstants.CENTER);
                lbl.setOpaque(true);
                String s = val != null ? val.toString() : "";
                if (s.startsWith("En cours")) {
                    lbl.setBackground(new Color(41, 128, 185));
                    lbl.setForeground(Color.WHITE);
                } else if (s.startsWith("⚠")) {
                    lbl.setBackground(new Color(211, 84, 0));
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(VERT_DISPO);
                    lbl.setForeground(Color.WHITE);
                }
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                return lbl;
            }
        });

        JScrollPane scrollEmp = new JScrollPane(tableMesEmprunts);
        scrollEmp.setBorder(new LineBorder(OR_ANTIQUE, 1));
        scrollEmp.getViewport().setBackground(BLEU_PROFOND);
        tabEmprunts.add(scrollEmp, BorderLayout.CENTER);

        tabbedPane.addTab(">  Mes Emprunts", tabEmprunts);

        JPanel mainBody = new JPanel(new BorderLayout());
        mainBody.setBackground(BLEU_PROFOND);
        mainBody.add(header, BorderLayout.NORTH);
        mainBody.add(tabbedPane, BorderLayout.CENTER);
        add(mainBody, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BLEU_PROFOND);
        footer.setBorder(new EmptyBorder(0, 20, 15, 20));

        JLabel hint = new JLabel("Sélectionnez un document dans le catalogue puis cliquez sur Emprunter.");
        hint.setForeground(new Color(140, 150, 165));
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        JButton btnEmprunter = new JButton(">  EMPRUNTER LE DOCUMENT SÉLECTIONNÉ");
        styleButton(btnEmprunter, OR_ANTIQUE, Color.BLACK);
        btnEmprunter.setPreferredSize(new Dimension(360, 44));
        btnEmprunter.setName("btnEmprunter");

        footer.add(hint, BorderLayout.WEST);
        footer.add(btnEmprunter, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);
    }


    private JLabel makeLegende(String text, Color color) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleTable(JTable table) {
        table.setBackground(BLEU_CHAMP);
        table.setForeground(Color.WHITE);
        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(45, 55, 72));
        table.setShowVerticalLines(false);

        JTableHeader h = table.getTableHeader();
        h.setBackground(OR_ANTIQUE);
        h.setForeground(Color.BLACK);
        h.setFont(new Font("Georgia", Font.BOLD, 13));
        h.setPreferredSize(new Dimension(0, 40));

        table.setSelectionBackground(OR_ANTIQUE);
        table.setSelectionForeground(Color.BLACK);
    }


    public JButton getBtnSearch()      { return btnSearch; }
    public JButton getBtnDeconnexion() { return btnDeconnexion; }
    public JButton getBtnEmprunter()   {
        for (Component c : ((JPanel) getContentPane().getComponent(1)).getComponents()) {
            if (c instanceof JButton && "btnEmprunter".equals(c.getName())) return (JButton) c;
        }
        return findBtnEmprunter(getContentPane());
    }
    private JButton findBtnEmprunter(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton && "btnEmprunter".equals(c.getName())) return (JButton) c;
            if (c instanceof Container) {
                JButton found = findBtnEmprunter((Container) c);
                if (found != null) return found;
            }
        }
        return null;
    }

    public String getSearchQuery()     { return txtSearch.getText(); }
    public JTable getTable()           { return tableDocuments; }
    public JTable getTableMesEmprunts() { return tableMesEmprunts; }
    public JTabbedPane getTabbedPane() { return tabbedPane; }
}