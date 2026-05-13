package modele;
import java.sql.Date;
public class Adherent {
	private int id;
	private String login;
	private String password;
	private String nom;
	private Date dateInscription;
	public Adherent() {}
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }
	public Date getDateInscription() { return dateInscription; }
	public void setDateInscription(Date dateInscription) { this.dateInscription = dateInscription; }
@Override
	public String toString() {
		return "Adherent [id=" + id + ", nom=" + nom + ", login=" + login + "]";
	}
}