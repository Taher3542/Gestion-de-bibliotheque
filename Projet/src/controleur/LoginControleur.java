package controleur;
 
import dao.AdherentDao;

import dao.BibliothecaireDao;
import modele.Adherent;
import modele.Bibliothecaire;
import vue.AdherentView;
import vue.BibliothecaireView;
import vue.LoginView;
 
import javax.swing.*;
 

public class LoginControleur {
 
    private final LoginView vue;
    private final AdherentDao adherentDao;
    private final BibliothecaireDao bibliothecaireDao;
 
    public LoginControleur(LoginView vue) {
        this.vue = vue;
        this.adherentDao = new AdherentDao();
        this.bibliothecaireDao = new BibliothecaireDao();
        initListeners();
    }
 
    private void initListeners() {
        vue.getBtnConnecter().addActionListener(e -> connecter());
    }
 

    private void connecter() {
        String login = vue.getUsername().trim();
        String password = vue.getPassword().trim();
 
        if (login.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vue,
                "Veuillez remplir tous les champs.",
                "Champs manquants", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        Bibliothecaire biblio = bibliothecaireDao.findByLoginAndPass(login, password);
        if (biblio != null) {
            ouvrirEspaceBibliothecaire(biblio);
            return;
        }
 
        Adherent adherent = adherentDao.findByLoginAndPass(login, password);
        if (adherent != null) {
            ouvrirEspaceAdherent(adherent);
            return;
        }
 
        JOptionPane.showMessageDialog(vue,
            "Login ou mot de passe incorrect.",
            "Connexion échouée", JOptionPane.ERROR_MESSAGE);
    }
 
    private void ouvrirEspaceBibliothecaire(Bibliothecaire biblio) {
        BibliothecaireView bibliothecaireView = new BibliothecaireView();
        new BibliothecaireControleur(bibliothecaireView, biblio);
        bibliothecaireView.setVisible(true);
        vue.dispose();
    }
 
    private void ouvrirEspaceAdherent(Adherent adherent) {
        AdherentView adherentView = new AdherentView();
        new AdherentControleur(adherentView, adherent);
        adherentView.setVisible(true);
        vue.dispose();
    }
}