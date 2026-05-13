package controleur;
 
import modele.Bibliothecaire;
import vue.BibliothecaireView;
import vue.GestionAdherentView;
import vue.GestionDocView;
import vue.GestionPretsView;
import vue.GestionRetoursView;
import vue.LoginView;
import javax.swing.JOptionPane;
 
public class BibliothecaireControleur {
 
    private final BibliothecaireView vue;
    private final Bibliothecaire bibliothecaire;
 
    public BibliothecaireControleur(BibliothecaireView vue, Bibliothecaire bibliothecaire) {
        this.vue = vue;
        this.bibliothecaire = bibliothecaire;
        initListeners();
    }
 
    private void initListeners() {
        vue.getBtnDocs().addActionListener(e -> ouvrirGestionDocuments());
        vue.getBtnAdherents().addActionListener(e -> ouvrirGestionAdherents());
        vue.getBtnPrets().addActionListener(e -> ouvrirGestionPrets());
        vue.getBtnRetours().addActionListener(e -> ouvrirGestionRetours());
        vue.getBtnDeconnexion().addActionListener(e -> seDeconnecter());
    }
 
    private void ouvrirGestionDocuments() {
        GestionDocView docView = new GestionDocView();
        new GestionDocumentControleur(docView);
        docView.setVisible(true);
    }
 
    private void ouvrirGestionAdherents() {
        GestionAdherentView adherentView = new GestionAdherentView();
        new GestionAdherentControleur(adherentView);
        adherentView.setVisible(true);
    }
 
    private void ouvrirGestionPrets() {
        GestionPretsView pretsView = new GestionPretsView();
        new PretControleur(pretsView); 
        pretsView.setVisible(true);
    }
 
    private void ouvrirGestionRetours() {
        GestionRetoursView retoursView = new GestionRetoursView();
        new RetourControleur(retoursView);
        retoursView.setVisible(true);
    }
 
    private void seDeconnecter() {
        int choice = JOptionPane.showConfirmDialog(vue, 
            "Désirez-vous fermer votre session de travail ?", 
            "Fermeture", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
        if (choice == JOptionPane.YES_OPTION) {
            LoginView loginView = new LoginView();
            new LoginControleur(loginView);
            loginView.setVisible(true);
            vue.dispose();
        }
    }
}