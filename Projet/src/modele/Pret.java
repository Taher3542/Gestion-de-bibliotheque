package modele;

import java.sql.Date;

import java.time.LocalDate;


public class Pret {
    private int id;
    private int idAdherent;
    private int idDocument;
    private LocalDate datePret;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    public Pret() {}

    public Pret(int id, int idAdherent, int idDocument, LocalDate datePret, LocalDate dateRetourPrevue) {
        this.id = id;
        this.idAdherent = idAdherent;
        this.idDocument = idDocument;
        this.datePret = datePret;
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdAdherent() { return idAdherent; }
    public void setIdAdherent(int idAdherent) { this.idAdherent = idAdherent; }
    public int getIdDocument() { return idDocument; }
    public void setIdDocument(int idDocument) { this.idDocument = idDocument; }


    public LocalDate getDatePret() { return datePret; }
    
    public void setDatePret(LocalDate datePret) { this.datePret = datePret; }
    public void setDatePret(Date sqlDate) { 
        if (sqlDate != null) this.datePret = sqlDate.toLocalDate(); 
    }

    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
    public void setDateRetourPrevue(Date sqlDate) { 
        if (sqlDate != null) this.dateRetourPrevue = sqlDate.toLocalDate(); 
    }

    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public void setDateRetourEffective() { this.dateRetourEffective = null; }

    public void setDateRetourEffective(LocalDate dateRetourEffective) { this.dateRetourEffective = dateRetourEffective; }
    public void setDateRetourEffective(Date sqlDate) { 
        if (sqlDate != null) this.dateRetourEffective = sqlDate.toLocalDate(); 
    }

    @Override
    public String toString() {
        return "Pret{" +
                "id=" + id +
                ", idAdherent=" + idAdherent +
                ", idDocument=" + idDocument +
                ", datePret=" + datePret +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffective=" + dateRetourEffective +
                '}';
    }
}