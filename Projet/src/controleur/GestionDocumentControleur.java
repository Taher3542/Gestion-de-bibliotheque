package controleur;

import dao.DocumentDao;

import modele.Document;
import vue.GestionDocView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class GestionDocumentControleur {

    private final GestionDocView vue;
    private final DocumentDao documentDao;

    private Document documentEnCours;

    public GestionDocumentControleur(GestionDocView vue) {
        this.vue = vue;
        this.documentDao = new DocumentDao();
        initListeners();
        chargerDocuments();
    }

    private void initListeners() {
        vue.getBtnAjouter().addActionListener(e -> preparerAjout());
        vue.getBtnModifier().addActionListener(e -> preparerModification());
        vue.getBtnSupprimer().addActionListener(e -> supprimerDocument());
        vue.getBtnEnregistrer().addActionListener(e -> enregistrer());
        vue.getBtnFermer().addActionListener(e -> vue.dispose());
    }


    private void chargerDocuments() {
        DefaultTableModel model = (DefaultTableModel) vue.getTable().getModel();
        model.setRowCount(0);

        List<Document> docs = documentDao.getAll();
        for (Document d : docs) {
            model.addRow(new Object[]{
                d.getId(),
                d.getTitre(),
                d.getAuteur(),
                d.getTypeDoc(),
                d.getNbExemplaires()
            });
        }
        documentEnCours = null;
    }


    private void preparerAjout() {
        JTextField txtTitre = new JTextField();
        JTextField txtAuteur = new JTextField();
        JTextField txtType = new JTextField();
        JTextField txtQte = new JTextField("1");

        Object[] fields = {
            "Titre :", txtTitre,
            "Auteur :", txtAuteur,
            "Type (Livre,Magazine...) :", txtType,
            "Nombre d'exemplaires :", txtQte
        };

        int result = JOptionPane.showConfirmDialog(vue, fields,
            "Ajouter un document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String titre = txtTitre.getText().trim();
            String auteur = txtAuteur.getText().trim();
            String type = txtType.getText().trim();
            String qteStr = txtQte.getText().trim();

            if (titre.isEmpty() || auteur.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(vue, "Titre, auteur et type sont obligatoires.",
                    "Champs manquants", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int qte;
            try {
                qte = Integer.parseInt(qteStr);
                if (qte < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vue, "La quantité doit être un nombre entier positif.",
                    "Valeur invalide", JOptionPane.WARNING_MESSAGE);
                return;
            }

            documentEnCours = new Document();
            documentEnCours.setTitre(titre);
            documentEnCours.setAuteur(auteur);
            documentEnCours.setTypeDoc(type);
            documentEnCours.setNbExemplaires(qte);

            DefaultTableModel model = (DefaultTableModel) vue.getTable().getModel();
            model.addRow(new Object[]{"(nouveau)", titre, auteur, type, qte});

            JOptionPane.showMessageDialog(vue,
                "Document préparé. Cliquez sur « Enregistrer » pour sauvegarder.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void preparerModification() {
        int row = vue.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(vue, "Sélectionnez un document à modifier.",
                "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = ((DefaultTableModel) vue.getTable().getModel()).getValueAt(row, 0);
        if (idObj.toString().equals("(nouveau)")) {
            JOptionPane.showMessageDialog(vue, "Enregistrez d'abord le document en cours d'ajout.",
                "Action impossible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) idObj;
        Document d = documentDao.findById(id);
        if (d == null) return;

        JTextField txtTitre = new JTextField(d.getTitre());
        JTextField txtAuteur = new JTextField(d.getAuteur());
        JTextField txtType = new JTextField(d.getTypeDoc());
        JTextField txtQte = new JTextField(String.valueOf(d.getNbExemplaires()));

        Object[] fields = {
            "Titre :", txtTitre,
            "Auteur :", txtAuteur,
            "Type :", txtType,
            "Nombre d'exemplaires :", txtQte
        };

        int result = JOptionPane.showConfirmDialog(vue, fields,
            "Modifier le document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int qte;
            try {
                qte = Integer.parseInt(txtQte.getText().trim());
                if (qte < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vue, "La quantité doit être un nombre entier positif.",
                    "Valeur invalide", JOptionPane.WARNING_MESSAGE);
                return;
            }

            d.setTitre(txtTitre.getText().trim());
            d.setAuteur(txtAuteur.getText().trim());
            d.setTypeDoc(txtType.getText().trim());
            d.setNbExemplaires(qte);

            documentEnCours = d;

            DefaultTableModel model = (DefaultTableModel) vue.getTable().getModel();
            model.setValueAt(d.getTitre(), row, 1);
            model.setValueAt(d.getAuteur(), row, 2);
            model.setValueAt(d.getTypeDoc(), row, 3);
            model.setValueAt(d.getNbExemplaires(), row, 4);

            JOptionPane.showMessageDialog(vue,
                "Modifications préparées. Cliquez sur « Enregistrer » pour sauvegarder.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void supprimerDocument() {
        int row = vue.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(vue, "Sélectionnez un document à supprimer.",
                "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) vue.getTable().getModel();
        Object idObj = model.getValueAt(row, 0);

        if (idObj.toString().equals("(nouveau)")) {
            model.removeRow(row);
            documentEnCours = null;
            return;
        }

        int id = (int) idObj;
        int confirm = JOptionPane.showConfirmDialog(vue,
            "Confirmer la suppression de ce document ?",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            documentDao.delete(id);
            model.removeRow(row);
            documentEnCours = null;
            JOptionPane.showMessageDialog(vue, "Document supprimé avec succès.",
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void enregistrer() {
        if (documentEnCours == null) {
            JOptionPane.showMessageDialog(vue,
                "Aucune modification à enregistrer.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (documentEnCours.getId() == 0) {
            documentDao.create(documentEnCours);
        } else {
            documentDao.update(documentEnCours);
        }

        JOptionPane.showMessageDialog(vue, "Document enregistré avec succès.",
            "Succès", JOptionPane.INFORMATION_MESSAGE);

        documentEnCours = null;
        chargerDocuments();
    }
}