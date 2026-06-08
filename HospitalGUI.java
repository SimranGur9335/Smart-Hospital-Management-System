import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalGUI extends JFrame implements ActionListener {

    JTextField idField, nameField, ageField, genderField, diseaseField, prescriptionField;
    JTable table;
    DefaultTableModel model;

    JButton addBtn, viewBtn, updateBtn, deleteBtn, searchBtn, clearBtn, searchNameBtn;

    JLabel countLabel;

    public HospitalGUI() {

        setTitle("Smart Hospital Management System");
        setSize(1000, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel title = new JLabel("SMART HOSPITAL MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setOpaque(true);
        title.setBackground(new Color(0, 153, 204));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(100, 40));
        add(title, BorderLayout.NORTH);

        JPanel left = new JPanel(new GridLayout(14,1,5,5));

        left.add(new JLabel("ID"));
        idField = new JTextField(); left.add(idField);

        left.add(new JLabel("Name"));
        nameField = new JTextField(); left.add(nameField);

        left.add(new JLabel("Age"));
        ageField = new JTextField(); left.add(ageField);

        left.add(new JLabel("Gender"));
        genderField = new JTextField(); left.add(genderField);

        left.add(new JLabel("Disease"));
        diseaseField = new JTextField(); left.add(diseaseField);

        left.add(new JLabel("Prescription"));
        prescriptionField = new JTextField(); left.add(prescriptionField);

        addBtn = new JButton("Add");
        viewBtn = new JButton("View");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        searchBtn = new JButton("Search ID");
        searchNameBtn = new JButton("Search Name");
        clearBtn = new JButton("Clear");

        left.add(addBtn); left.add(viewBtn);
        left.add(updateBtn); left.add(deleteBtn);
        left.add(searchBtn); left.add(searchNameBtn);
        left.add(clearBtn);

        add(left, BorderLayout.WEST);

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Disease");
        model.addColumn("Prescription");
        model.addColumn("Date");

        add(new JScrollPane(table), BorderLayout.CENTER);

        countLabel = new JLabel("Total Patients: 0");
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        add(countLabel, BorderLayout.SOUTH);

        addBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        searchNameBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        setVisible(true);
    }

    public void loadTable() {
        try {
            model.setRowCount(0);
            ResultSet rs = PatientDAO.getAllPatients();

            while(rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("disease"),
                        rs.getString("prescription"),
                        rs.getString("created_at")
                });
            }

            countLabel.setText("Total Patients: " + PatientDAO.getPatientCount());

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {

        try {

            if (e.getSource() == addBtn) {

                if(nameField.getText().isEmpty() || ageField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this,"Fill all fields!");
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,"Age must be number!");
                    return;
                }

                Patient p = new Patient(
                        nameField.getText(),
                        age,
                        genderField.getText(),
                        diseaseField.getText(),
                        prescriptionField.getText()
                );

                PatientDAO.addPatient(p);
                loadTable();

                nameField.setText("");
                ageField.setText("");
                genderField.setText("");
                diseaseField.setText("");
                prescriptionField.setText("");

                JOptionPane.showMessageDialog(this,"Added!");
            }

            if (e.getSource() == viewBtn) loadTable();

            if (e.getSource() == updateBtn) {
                PatientDAO.updatePatient(
                        Integer.parseInt(idField.getText()),
                        diseaseField.getText()
                );
                loadTable();
            }

            if (e.getSource() == deleteBtn) {
                PatientDAO.deletePatient(Integer.parseInt(idField.getText()));
                loadTable();
            }

            if (e.getSource() == searchBtn) {
                model.setRowCount(0);
                ResultSet rs = PatientDAO.searchPatientById(Integer.parseInt(idField.getText()));

                while(rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("disease"),
                            rs.getString("prescription"),
                            rs.getString("created_at")
                    });
                }
            }

            if (e.getSource() == searchNameBtn) {
                model.setRowCount(0);
                ResultSet rs = PatientDAO.searchPatientByName(nameField.getText());

                while(rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("disease"),
                            rs.getString("prescription"),
                            rs.getString("created_at")
                    });
                }
            }

            if (e.getSource() == clearBtn) {
                idField.setText("");
                nameField.setText("");
                ageField.setText("");
                genderField.setText("");
                diseaseField.setText("");
                prescriptionField.setText("");
            }

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Error!");
        }
    }

    public static void main(String[] args) {
        new HospitalGUI();
    }
}