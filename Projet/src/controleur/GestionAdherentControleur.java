package controleur;

import dao.AdherentDao;
import modele.Adherent;
import vue.GestionAdherentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class GestionAdherentControleur {

    private final GestionAdherentView vue;
    private final AdherentDao adherentDao;

    private Adherent adherentEnCours;

    public GestionAdherentControleur(GestionAdherentView vue) {
        this.vue = vue;
        this.adherentDao = new AdherentDao();
        initListeners();
        chargerAdherents();
    }

    private void initListeners() {
        vue.getBtnAjouter().addActionListener(e -> preparerAjout());
        vue.getBtnModifier().addActionListener(e -> preparerModification());
        vue.getBtnSupprimer().addActionListener(e -> supprimerAdherent());
        vue.getBtnEnregistrer().addActionListener(e -> enregistrer());
        vue.getBtnFermer().addActionListener(e -> vue.dispose());
    }


    private void chargerAdherents() {
        DefaultTableModel model = vue.getModel();
        model.setRowCount(0);

        List<Adherent> adherents = adherentDao.getAll();
        for (Adherent a : adherents) {
            model.addRow(new Object[]{
                a.getId(),
                a.getNom(),
                a.getLogin(),
                "••••••••",
                a.getDateInscription()
            });
        }
        adherentEnCours = null;
    }


    private void preparerAjout() {
        JTextField txtNom = new JTextField();
        JTextField txtLogin = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        Object[] fields = {
            "Nom :", txtNom,
            "Login :", txtLogin,
            "Mot de passe :", txtPassword
        };

        int result = JOptionPane.showConfirmDialog(vue, fields,
            "Ajouter un adhérent", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nom = txtNom.getText().trim();
            String login = txtLogin.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (nom.isEmpty() || login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(vue, "Tous les champs sont obligatoires.",
                    "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Vérification unicité du login
            if (adherentDao.loginExiste(login)) {
                JOptionPane.showMessageDialog(vue,
                    "Ce login est déjà utilisé par un autre adhérent.",
                    "Login indisponible", JOptionPane.WARNING_MESSAGE);
                return;
            }

            adherentEnCours = new Adherent();
            adherentEnCours.setNom(nom);
            adherentEnCours.setLogin(login);
            adherentEnCours.setPassword(password);
            adherentEnCours.setDateInscription(Date.valueOf(LocalDate.now()));

            vue.getModel().addRow(new Object[]{
                "(nouveau)", nom, login, "••••••••", adherentEnCours.getDateInscription()
            });

            JOptionPane.showMessageDialog(vue,
                "Adhérent préparé. Cliquez sur « Enregistrer » pour sauvegarder.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void preparerModification() {
        int row = vue.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(vue, "Sélectionnez un adhérent à modifier.",
                "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = vue.getModel().getValueAt(row, 0);
        if (idObj.toString().equals("(nouveau)")) {
            JOptionPane.showMessageDialog(vue, "Enregistrez d'abord l'adhérent en cours d'ajout.",
                "Action impossible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) idObj;
        Adherent a = adherentDao.findById(id);
        if (a == null) return;

        JTextField txtNom = new JTextField(a.getNom());
        JTextField txtLogin = new JTextField(a.getLogin());
        JPasswordField txtPassword = new JPasswordField();

        Object[] fields = {
            "Nom :", txtNom,
            "Login :", txtLogin,
            "Nouveau mot de passe (laisser vide = inchangé) :", txtPassword
        };

        int result = JOptionPane.showConfirmDialog(vue, fields,
            "Modifier l'adhérent", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nouveauLogin = txtLogin.getText().trim();

            if (!nouveauLogin.equals(a.getLogin()) && adherentDao.loginExiste(nouveauLogin)) {
                JOptionPane.showMessageDialog(vue,
                    "Ce login est déjà utilisé par un autre adhérent.",
                    "Login indisponible", JOptionPane.WARNING_MESSAGE);
                return;
            }

            a.setNom(txtNom.getText().trim());
            a.setLogin(nouveauLogin);
            String newPass = new String(txtPassword.getPassword()).trim();
            if (!newPass.isEmpty()) a.setPassword(newPass);

            adherentEnCours = a;

            vue.getModel().setValueAt(a.getNom(), row, 1);
            vue.getModel().setValueAt(a.getLogin(), row, 2);

            JOptionPane.showMessageDialog(vue,
                "Modifications préparées. Cliquez sur « Enregistrer » pour sauvegarder.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void supprimerAdherent() {
        int row = vue.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(vue, "Sélectionnez un adhérent à supprimer.",
                "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = vue.getModel().getValueAt(row, 0);
        if (idObj.toString().equals("(nouveau)")) {
            vue.getModel().removeRow(row);
            adherentEnCours = null;
            return;
        }

        int id = (int) idObj;
        int confirm = JOptionPane.showConfirmDialog(vue,
            "Confirmer la suppression de cet adhérent ?",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            adherentDao.delete(id);
            vue.getModel().removeRow(row);
            adherentEnCours = null;
            JOptionPane.showMessageDialog(vue, "Adhérent supprimé avec succès.",
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void enregistrer() {
        if (adherentEnCours == null) {
            JOptionPane.showMessageDialog(vue,
                "Aucune modification à enregistrer.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (adherentEnCours.getId() == 0) {
            adherentDao.create(adherentEnCours);
        } else {
            adherentDao.update(adherentEnCours);
        }

        JOptionPane.showMessageDialog(vue, "Adhérent enregistré avec succès.",
            "Succès", JOptionPane.INFORMATION_MESSAGE);

        adherentEnCours = null;
        chargerAdherents();
    }
}
