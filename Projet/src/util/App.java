package util;
import controleur.LoginControleur;

import vue.LoginView;




public class App {
    public static void main(String[] args) {
        
            LoginView loginView = new LoginView();
            new LoginControleur(loginView);
            loginView.setVisible(true);
        ;
    }
}