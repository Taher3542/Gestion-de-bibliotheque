package controleur;

import dao.DocumentDao;

import dao.PretDao;
import modele.Adherent;
import modele.Document;
import modele.Pret;
import vue.AdherentView;
import vue.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class AdherentControleur {

    private final AdherentView vue;
    private final Adherent adherent;
    private final DocumentDao documentDao;
    private final PretDao pretDao;

    public AdherentControleur(AdherentView vue, Adherent adherent) {
        this.vue = vue;
        this.adherent = adherent;
        this.documentDao = new DocumentDao();
        this.pretDao = new PretDao();
        initListeners();
        chargerTousLesDocuments();
        chargerMesEmprunts();
    }

    private void initListeners() {
        vue.getBtnSearch().addActionListener(e -> rechercherDocuments());

        JButton btnEmprunter = vue.getBtnEmprunter();
        if (btnEmprunter != null) {
            btnEmprunter.addActionListener(e -> emprunterDocument());
        }

        vue.getBtnDeconnexion().addActionListener(e -> seDeconnecter());

        vue.getTabbedPane().addChangeListener(e -> {
            if (vue.getTabbedPane().getSelectedIndex() == 1) {
                chargerMesEmprunts();
            }
        });
    }

    private void chargerTousLesDocuments() {
        afficherDocuments(documentDao.getAll());
    }

    private void rechercherDocuments() {
        String query = vue.getSearchQuery().trim();
        List<Document> resultats;
        if (query.isEmpty()) {
            resultats = documentDao.getAll();
        } else {
            resultats = documentDao.findByTitre(query);
            if (resultats.isEmpty()) resultats = documentDao.findByAuteur(query);
        }
        afficherDocuments(resultats);
        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(vue,
                "Aucun document trouvé pour : \"" + query + "\"",
                "Recherche", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void emprunterDocument() {
        int ligne = vue.getTable().getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(vue,
                "Veuillez sélectionner un document dans le catalogue.",
                "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idDoc = (int) vue.getTable().getValueAt(ligne, 0);
        Document doc = documentDao.findById(idDoc);
        if (doc == null) {
            JOptionPane.showMessageDialog(vue, "Document introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (doc.getNbExemplaires() <= 0) {
            JOptionPane.showMessageDialog(vue,
                "Aucun exemplaire disponible pour ce document.\nVeuillez le réserver ou consulter plus tard.",
                "Document indisponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pret pret = new Pret();
        pret.setIdAdherent(adherent.getId());
        pret.setIdDocument(idDoc);
        pret.setDatePret(LocalDate.now());
        pret.setDateRetourPrevue(LocalDate.now().plusDays(14));

        pretDao.create(pret);
        doc.setNbExemplaires(doc.getNbExemplaires() - 1);
        documentDao.update(doc);

        JOptionPane.showMessageDialog(vue,
            "✅ Emprunt enregistré avec succès !\n\n"
            + "Document : " + doc.getTitre() + "\n"
            + "Date de retour prévue : " + pret.getDateRetourPrevue(),
            "Emprunt confirmé", JOptionPane.INFORMATION_MESSAGE);

        chargerTousLesDocuments();
        chargerMesEmprunts();
    }

    private void chargerMesEmprunts() {
        DefaultTableModel model = (DefaultTableModel) vue.getTableMesEmprunts().getModel();
        model.setRowCount(0);

        List<Pret> prets = pretDao.findByIdAdherent(adherent.getId());
        LocalDate today = LocalDate.now();

        for (Pret p : prets) {
            Document doc = documentDao.findById(p.getIdDocument());
            String titre = (doc != null) ? doc.getTitre() : "(inconnu)";

            String statut;
            if (p.getDateRetourEffective() != null) {
                statut = " Rendu";
            } else if (p.getDateRetourPrevue() != null && today.isAfter(p.getDateRetourPrevue())) {
                statut = " En retard !";
            } else {
                statut = "En cours";
            }

            model.addRow(new Object[]{
                p.getId(),
                p.getIdDocument(),
                titre,
                p.getDatePret(),
                p.getDateRetourPrevue(),
                statut
            });
        }
    }

    private void afficherDocuments(List<Document> docs) {
        DefaultTableModel model = (DefaultTableModel) vue.getTable().getModel();
        model.setRowCount(0);
        for (Document d : docs) {
            String dispo = d.getNbExemplaires() > 0 ? "DISPONIBLE" : "INDISPONIBLE";
            model.addRow(new Object[]{
                d.getId(),
                d.getTitre(),
                d.getAuteur(),
                d.getTypeDoc(),
                d.getNbExemplaires(),
                dispo
            });
        }
    }

    private void seDeconnecter() {
        LoginView loginView = new LoginView();
        new LoginControleur(loginView);
        loginView.setVisible(true);
        vue.dispose();
    }
}