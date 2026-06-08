import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginGUI extends JFrame implements ActionListener {

    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;

    public LoginGUI() {

        setTitle("Login");
        setSize(300, 200);
        setLayout(new GridLayout(3,2,10,10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        loginBtn = new JButton("Login");
        add(new JLabel()); // empty space
        add(loginBtn);

        loginBtn.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String user = userField.getText();
        String pass = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new HospitalGUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}