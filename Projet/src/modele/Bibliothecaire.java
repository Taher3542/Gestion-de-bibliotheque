package modele;
public class Bibliothecaire {
	private int id;
	private String login;
	private String password;

	private String nom;
	private String matricule;
	public Bibliothecaire() {}
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }
	public String getMatricule() { return matricule; }
	public void setMatricule(String matricule) { this.matricule = matricule; }
@Override
	public String toString() {
		return "Bibliothecaire [id=" + id + ", nom=" + nom + ", matricule=" + matricule + "]";
	}
}