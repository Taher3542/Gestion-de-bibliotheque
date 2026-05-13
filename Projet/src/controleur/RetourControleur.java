package controleur;

import dao.DocumentDao;
import dao.PretDao;
import modele.Document;
import modele.Pret;
import vue.GestionRetoursView;

import javax.swing.JOptionPane;
import java.time.LocalDate;

public class RetourControleur {
    private final GestionRetoursView vue;
    private final PretDao pretDao = new PretDao();
    private final DocumentDao documentDao = new DocumentDao();

    public RetourControleur(GestionRetoursView vue) {
        this.vue = vue;
        initListeners();
    }

    private void initListeners() {
        vue.getBtnValider().addActionListener(e -> enregistrerRetour());
    }

    private void enregistrerRetour() {
        // On récupère l'identifiant du PRÊT depuis la vue
        String idPretStr = vue.getIdDocument(); 

        if (idPretStr.isEmpty()) {
            JOptionPane.showMessageDialog(vue, "Veuillez saisir l'identifiant du prêt.");
            return;
        }

        try {
            int idPret = Integer.parseInt(idPretStr);

            Pret pretActif = pretDao.findById(idPret);

            if (pretActif == null) {
                JOptionPane.showMessageDialog(vue, "Aucun prêt trouvé pour cet identifiant.");
                return;
            }

            if (pretActif.getDateRetourEffective() != null) {
                JOptionPane.showMessageDialog(vue, "Ce prêt a déjà été rendu.");
                return;
            }

            LocalDate today = LocalDate.now();
            java.sql.Date sqlDateEffective = java.sql.Date.valueOf(today);
            
            LocalDate datePrevue = pretActif.getDateRetourPrevue();

            pretDao.enregistrerRetour(idPret, sqlDateEffective);

            Document doc = documentDao.findById(pretActif.getIdDocument());
            if (doc != null) {
                doc.setNbExemplaires(doc.getNbExemplaires() + 1);
                documentDao.update(doc);
            }

            StringBuilder message = new StringBuilder();
            message.append("RESTITUTION RÉUSSIE\n\n");
            message.append("Document : ").append(doc != null ? doc.getTitre() : "Inconnu").append("\n");
            
            if (datePrevue != null && today.isAfter(datePrevue)) {
                message.append("⚠️ RETARD ! La date prévue était le : ").append(datePrevue);
            } else {
                message.append("Retour effectué le : ").append(today);
            }

            JOptionPane.showMessageDialog(vue, message.toString(), "Succès", JOptionPane.INFORMATION_MESSAGE);
            
            vue.viderChamp();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vue, "L'identifiant doit être un nombre.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vue, "Erreur : " + ex.getMessage());
        }
    }
}