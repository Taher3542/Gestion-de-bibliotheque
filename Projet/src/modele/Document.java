package modele;
public class Document {
	private int id;
	private String titre;
	private String auteur;
	private String typeDoc;
	private int nbExemplaires;
	public Document() {}
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getTitre() { return titre; }
	public void setTitre(String titre) { this.titre = titre; }
	public String getAuteur() { return auteur; }
	public void setAuteur(String auteur) { this.auteur = auteur; }
	public String getTypeDoc() { return typeDoc; }
	public void setTypeDoc(String typeDoc) { this.typeDoc = typeDoc; }
	public int getNbExemplaires() { return nbExemplaires; }
	public void setNbExemplaires(int nbExemplaires) { this.nbExemplaires = nbExemplaires; }
@Override
	public String toString() {
		return "Document [titre=" + titre + ", auteur=" + auteur + ", type=" + typeDoc + "]";
	}
}