package dao;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;
import modele.Bibliothecaire;
import util.SingletonConnection;

public class BibliothecaireDao implements Idao<Bibliothecaire> {

    @Override
    public List<Bibliothecaire> getAll() {
        List<Bibliothecaire> biblios = new ArrayList<>();
        String sql = "SELECT * FROM bibliothecaire";
        
        try (Connection conn = SingletonConnection.getInstance();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                biblios.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return biblios;
    }

    @Override
    public Bibliothecaire findById(int id) {
        String sql = "SELECT * FROM bibliothecaire WHERE id_biblio = ?";
        
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
    public void create(Bibliothecaire obj) {
        String sql = "INSERT INTO bibliothecaire (login, password, nom, matricule) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getPassword());
            ps.setString(3, obj.getNom());
            ps.setString(4, obj.getMatricule());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bibliothecaire obj) {
        String sql = "UPDATE bibliothecaire SET login = ?, password = ?, nom = ?, matricule = ? WHERE id_biblio = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getPassword());
            ps.setString(3, obj.getNom());
            ps.setString(4, obj.getMatricule());
            ps.setInt(5, obj.getId());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM bibliothecaire WHERE id_biblio = ?";
        
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Bibliothecaire findByLoginAndPass(String login, String password) {
        String sql = "SELECT * FROM bibliothecaire WHERE login = ? AND password = ?";
        try (Connection conn = SingletonConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bibliothecaire map(ResultSet rs) throws SQLException {
        Bibliothecaire b = new Bibliothecaire();
        b.setId(rs.getInt("id_biblio"));
        b.setLogin(rs.getString("login"));
        b.setNom(rs.getString("nom"));
        b.setMatricule(rs.getString("matricule"));
        return b;
    }
}