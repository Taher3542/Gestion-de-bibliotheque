package dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import modele.Pret;
import util.SingletonConnection;

public class PretDao implements Idao<Pret> {

    @Override
    public List<Pret> getAll() {
        List<Pret> prets = new ArrayList<>();
        String sql = "SELECT * FROM pret";
        
        try (Connection conn = SingletonConnection.getInstance();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                prets.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prets;
    }
 
    public void enregistrerRetour(int idPret, Date dateRetour) {
        String sql = "UPDATE pret SET date_retour_effective = ? WHERE id_pret = ?";

        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, dateRetour);
            ps.setInt(2, idPret);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur dans enregistrerRetour : " + e.getMessage());
        }
    }
  
    public List<Pret> findActivePrets() {
        List<Pret> liste = new ArrayList<>();
        String sql = "SELECT * FROM pret WHERE date_retour_effective IS NULL";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Pret p = new Pret();
                p.setId(rs.getInt("id_pret"));
                p.setIdAdherent(rs.getInt("id_adherent"));
                p.setIdDocument(rs.getInt("id_document"));
                
                p.setDatePret(rs.getDate("date_pret"));
                p.setDateRetourPrevue(rs.getDate("date_retour_prevue"));
                
                liste.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur dans findActivePrets : " + e.getMessage());
        }
        return liste;
    }
    @Override
    public Pret findById(int id) {
        String sql = "SELECT * FROM pret WHERE id_pret = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Pret p) {
        String sql = "INSERT INTO pret (id_adherent, id_doc, date_pret, date_retour_prevue) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, p.getIdAdherent());
            ps.setInt(2, p.getIdDocument());
            ps.setDate(3, Date.valueOf(p.getDatePret()));
            ps.setDate(4, Date.valueOf(p.getDateRetourPrevue()));
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pret p) {
        String sql = "UPDATE pret SET id_adherent = ?, id_doc = ?, date_pret = ?, "
                   + "date_retour_prevue = ?, date_retour_effective = ? WHERE id_pret = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, p.getIdAdherent());
            ps.setInt(2, p.getIdDocument());
            ps.setDate(3, Date.valueOf(p.getDatePret()));
            ps.setDate(4, Date.valueOf(p.getDateRetourPrevue()));
            
            if (p.getDateRetourEffective() != null) {
                ps.setDate(5, Date.valueOf(p.getDateRetourEffective()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pret WHERE id_pret = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Pret> findByIdAdherent(int idAdherent) {
        List<Pret> list = new ArrayList<>();
        String sql = "SELECT * FROM pret WHERE id_adherent = ?";
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAdherent);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Pret> findByIdDocument(int idDoc) {
        List<Pret> list = new ArrayList<>();
        String sql = "SELECT * FROM pret WHERE id_doc = ?";
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDoc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Pret findActivePretByDoc(int idDoc) {
        Pret pret = null;
        String sql = "SELECT * FROM pret WHERE id_doc = ? AND date_retour_effective IS NULL";

        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDoc);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pret = new Pret();
                pret.setId(rs.getInt("id_pret"));
                pret.setIdAdherent(rs.getInt("id_adherent"));
                pret.setIdDocument(rs.getInt("id_doc")); 
                pret.setDatePret(rs.getDate("date_pret").toLocalDate());
                pret.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());
                pret.setDateRetourEffective();
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return pret;
    }

    private Pret map(ResultSet rs) throws SQLException {
        Pret p = new Pret();
        p.setId(rs.getInt("id_pret"));
        p.setIdAdherent(rs.getInt("id_adherent"));
        p.setIdDocument(rs.getInt("id_doc"));
        
        p.setDatePret(rs.getDate("date_pret").toLocalDate());
        p.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());
        
        Date dEffective = rs.getDate("date_retour_effective");
        if (dEffective != null) {
            p.setDateRetourEffective(dEffective.toLocalDate());
        }
        
        return p;
    }
}