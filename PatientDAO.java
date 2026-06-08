import java.sql.*;

public class PatientDAO {

    // ADD (UPDATED with prescription)
    public static void addPatient(Patient p) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO patients(name, age, gender, disease, prescription) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, p.name);
            ps.setInt(2, p.age);
            ps.setString(3, p.gender);
            ps.setString(4, p.disease);
            ps.setString(5, p.prescription);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW ALL
    public static ResultSet getAllPatients() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery("SELECT * FROM patients");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public static void updatePatient(int id, String disease) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE patients SET disease=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, disease);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deletePatient(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM patients WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SEARCH BY ID
    public static ResultSet searchPatientById(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM patients WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔍 SEARCH BY NAME (NEW)
    public static ResultSet searchPatientByName(String name) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM patients WHERE name LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, "%" + name + "%");

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔥 TOTAL COUNT
    public static int getPatientCount() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM patients");

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}