package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.Document;
import util.SingletonConnection;
public class DocumentDao implements Idao<Document> {
@Override
	public List<Document> getAll() {
		List<Document> docs = new ArrayList<>();
		String sql = "SELECT * FROM document";
		try (Connection conn = SingletonConnection.getInstance();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
				while (rs.next()) docs.add(map(rs));
		} catch (SQLException e) { e.printStackTrace(); }
		return docs;
}
public List<Document> findByTitre(String titre) {
	List<Document> docs = new ArrayList<>();
	String sql = "SELECT * FROM document WHERE titre LIKE ?";
	try (Connection conn = SingletonConnection.getInstance();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "%" + titre + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) docs.add(map(rs));
	} catch (SQLException e) { e.printStackTrace(); }
return docs;
}
public List<Document> findByAuteur(String auteur) {
List<Document> docs = new ArrayList<>();
String sql = "SELECT * FROM document WHERE auteur LIKE ?";
try (Connection conn = SingletonConnection.getInstance();
PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setString(1, "%" + auteur + "%");
ResultSet rs = ps.executeQuery();
while (rs.next()) docs.add(map(rs));
} catch (SQLException e) { e.printStackTrace(); }
return docs;
}
@Override
public Document findById(int id) {
String sql = "SELECT * FROM document WHERE id_doc = ?";
try (Connection conn = SingletonConnection.getInstance();
PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setInt(1, id);
ResultSet rs = ps.executeQuery();
if (rs.next()) return map(rs);
} catch (SQLException e) { e.printStackTrace(); }
return null;
}
@Override public void create(Document d) {
    String sql = "INSERT INTO document (titre, auteur, type_doc, nb_exemplaires) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = SingletonConnection.getInstance();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, d.getTitre());
        ps.setString(2, d.getAuteur());
        ps.setString(3, d.getTypeDoc());
        ps.setInt(4, d.getNbExemplaires());
        
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
	}
@Override public void delete(int id) {
	String sql = "DELETE FROM document WHERE id_doc = ?";
    
    try (Connection conn = SingletonConnection.getInstance();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } 
	}
@Override public void update(Document d) { 
	 String sql = "UPDATE document SET titre = ?, auteur = ?, type_doc = ?, nb_exemplaires = ? WHERE id_doc = ?";
     
     try (Connection conn = SingletonConnection.getInstance();
          PreparedStatement ps = conn.prepareStatement(sql)) {
         
         ps.setString(1, d.getTitre());
         ps.setString(2, d.getAuteur());
         ps.setString(3, d.getTypeDoc());
         ps.setInt(4, d.getNbExemplaires());
         ps.setInt(5, d.getId());
         
         ps.executeUpdate();
     } catch (SQLException e) {
         e.printStackTrace();
     }	}

private Document map(ResultSet rs) throws SQLException {
Document d = new Document();
d.setId(rs.getInt("id_doc"));
d.setTitre(rs.getString("titre"));
d.setAuteur(rs.getString("auteur"));
d.setTypeDoc(rs.getString("type_doc"));
d.setNbExemplaires(rs.getInt("nb_exemplaires"));
return d;
}
}