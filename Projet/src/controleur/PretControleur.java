package controleur;

import dao.AdherentDao;
import dao.DocumentDao;
import dao.PretDao;
import modele.Adherent;
import modele.Document;
import modele.Pret;
import vue.GestionPretsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PretControleur {

    private final GestionPretsView vue;
    private final PretDao pretDao;
    private final AdherentDao adherentDao;
    private final DocumentDao documentDao;

    public PretControleur(GestionPretsView vue) {
        this.vue = vue;
        this.pretDao = new PretDao();
        this.adherentDao = new AdherentDao();
        this.documentDao = new DocumentDao();
        initListeners();
        chargerPrets(); 
    }

    private void initListeners() {
        vue.getBtnFermer().addActionListener(e -> vue.dispose());
    }


    private String calculerEtatPret(Pret pret) {
        LocalDate aujourd_hui = LocalDate.now();
        LocalDate dateRetourEffective = pret.getDateRetourEffective();
        LocalDate dateRetourPrevue = pret.getDateRetourPrevue();

        if (dateRetourEffective != null) {
            if (dateRetourEffective.isBefore(dateRetourPrevue) || dateRetourEffective.isEqual(dateRetourPrevue)) {
                return "Rendu avant la date";
            } else {
                long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourEffective);
                return "Retard détecté (" + joursRetard + " jour(s))";
            }
        }


        if (aujourd_hui.isAfter(dateRetourPrevue)) {
            long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevue, aujourd_hui);
            return "Date limite de retour dépassée (retard: " + joursRetard + " jour(s))";
        }

        long joursRestants = ChronoUnit.DAYS.between(aujourd_hui, dateRetourPrevue);
        return "Jours restants: " + joursRestants;
    }

    private void chargerPrets() {
        DefaultTableModel model = vue.getModel();
        model.setRowCount(0); 

        try {
            List<Pret> liste = pretDao.getAll(); 
            if (liste != null) {
                for (Pret p : liste) {
                    String etatPret = calculerEtatPret(p);
                    model.addRow(new Object[]{
                        p.getId(),
                        p.getIdAdherent(),
                        p.getIdDocument(),
                        p.getDatePret(),
                        p.getDateRetourPrevue(),
                        etatPret
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}