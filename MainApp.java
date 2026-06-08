import java.util.Scanner;
import java.sql.ResultSet;

public class MainApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Search Patient");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            try {

                switch (choice) {

                    // ADD
                    case 1:
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        sc.nextLine();
                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter Disease: ");
                        String disease = sc.nextLine();

                        System.out.print("Enter Prescription: ");
                        String prescription = sc.nextLine();

                        Patient p = new Patient(name, age, gender, disease, prescription);
                        PatientDAO.addPatient(p);

                        System.out.println("Patient Added!");
                        break;

                    // VIEW
                    case 2:
                        ResultSet rs = PatientDAO.getAllPatients();

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("id") + " | " +
                                    rs.getString("name") + " | " +
                                    rs.getInt("age") + " | " +
                                    rs.getString("gender") + " | " +
                                    rs.getString("disease") + " | " +
                                    rs.getString("prescription") + " | " +
                                    rs.getString("created_at")
                            );
                        }
                        break;

                    // UPDATE
                    case 3:
                        System.out.print("Enter Patient ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Disease: ");
                        String newDisease = sc.nextLine();

                        PatientDAO.updatePatient(uid, newDisease);
                        System.out.println("Updated!");
                        break;

                    // DELETE
                    case 4:
                        System.out.print("Enter Patient ID to delete: ");
                        int did = sc.nextInt();

                        PatientDAO.deletePatient(did);
                        System.out.println("Deleted!");
                        break;

                    // SEARCH
                    case 5:
                        System.out.print("Enter Patient ID to search: ");
                        int sid = sc.nextInt();

                        ResultSet rs2 = PatientDAO.searchPatientById(sid);

                        if (rs2.next()) {
                            System.out.println(
                                    rs2.getInt("id") + " | " +
                                    rs2.getString("name") + " | " +
                                    rs2.getInt("age") + " | " +
                                    rs2.getString("gender") + " | " +
                                    rs2.getString("disease") + " | " +
                                    rs2.getString("prescription") + " | " +
                                    rs2.getString("created_at")
                            );
                        } else {
                            System.out.println("Patient Not Found!");
                        }
                        break;

                    // EXIT
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice!");
                }

            } catch (Exception e) {
                System.out.println("Error occurred!");
                e.printStackTrace();
            }
        }
    }
}