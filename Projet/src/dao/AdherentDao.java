package dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import modele.Adherent;
import util.SingletonConnection;

public class AdherentDao implements Idao<Adherent> {

    @Override
    public List<Adherent> getAll() {
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM adherent";
        
        try (Connection conn = SingletonConnection.getInstance();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                adherents.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }

    @Override
    public Adherent findById(int id) {
        String sql = "SELECT * FROM adherent WHERE id_adherent = ?";
        
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
    public void create(Adherent obj) {
        String sql = "INSERT INTO adherent (login, password, nom, date_inscription) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getPassword());
            ps.setString(3, obj.getNom());
            ps.setDate(4, obj.getDateInscription());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Adherent obj) {
        String sql = "UPDATE adherent SET login = ?, password = ?, nom = ?, date_inscription = ? WHERE id_adherent = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getPassword());
            ps.setString(3, obj.getNom());
            ps.setDate(4, obj.getDateInscription());
            ps.setInt(5, obj.getId());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM adherent WHERE id_adherent = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Adherent findByLoginAndPass(String login, String password) {
        String sql = "SELECT * FROM adherent WHERE login = ? AND password = ?";
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private Adherent map(ResultSet rs) throws SQLException {
        Adherent a = new Adherent();
        a.setId(rs.getInt("id_adherent"));
        a.setLogin(rs.getString("login"));
        a.setNom(rs.getString("nom"));
        a.setDateInscription(rs.getDate("date_inscription"));
        return a;
    }
}