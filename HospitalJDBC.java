import java.sql.*;
import java.util.*;

public class HospitalJDBC {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/6";
        String user = "root";  
        String pass = "kavi@123";   

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            while (true) {
                System.out.println("\n===== HOSPITAL PATIENT MANAGEMENT =====");
                System.out.println("1. Add Patient");
                System.out.println("2. View All Patients");
                System.out.println("3. Search Patient");
                System.out.println("4. Update Patient Status");
                System.out.println("5. Delete Patient");
                System.out.println("6. Exit");
                System.out.print("Choose: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // ---------------------------------
                    case 1:
                        System.out.print("Enter Patient ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Disease: ");
                        String disease = sc.nextLine();

                        System.out.print("Enter Status (Admitted/Discharged): ");
                        String status = sc.nextLine();

                        PreparedStatement ps1 = con.prepareStatement(
                                "INSERT INTO patient VALUES (?,?,?,?,?)");

                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setInt(3, age);
                        ps1.setString(4, disease);
                        ps1.setString(5, status);
                        ps1.executeUpdate();

                        System.out.println("✔ Patient Added Successfully!");
                        break;

                    // ---------------------------------
                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM patient");

                        System.out.println("\n--- Patient Records ---");
                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt(1) + " | " +
                                    rs.getString(2) + " | " +
                                    rs.getInt(3) + " | " +
                                    rs.getString(4) + " | " +
                                    rs.getString(5)
                            );
                        }
                        break;

                    // ---------------------------------
                    case 3:
                        System.out.print("Enter ID to Search: ");
                        int sid = sc.nextInt();

                        PreparedStatement ps2 = con.prepareStatement(
                                "SELECT * FROM patient WHERE id=?");
                        ps2.setInt(1, sid);

                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            System.out.println("\nPatient Found:");
                            System.out.println("ID: " + rs2.getInt(1));
                            System.out.println("Name: " + rs2.getString(2));
                            System.out.println("Age: " + rs2.getInt(3));
                            System.out.println("Disease: " + rs2.getString(4));
                            System.out.println("Status: " + rs2.getString(5));
                        } else {
                            System.out.println("❌ No patient found!");
                        }
                        break;

                    // ---------------------------------
                    case 4:
                        System.out.print("Enter Patient ID to Update Status: ");
                        int uid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Status (Admitted/Discharged): ");
                        String newStatus = sc.nextLine();

                        PreparedStatement ps3 = con.prepareStatement(
                                "UPDATE patient SET status=? WHERE id=?");

                        ps3.setString(1, newStatus);
                        ps3.setInt(2, uid);

                        int updated = ps3.executeUpdate();
                        System.out.println(updated > 0 ? "✔ Status Updated!" : "❌ ID Not Found");
                        break;

                    // ---------------------------------
                    case 5:
                        System.out.print("Enter ID to Delete Patient: ");
                        int did = sc.nextInt();

                        PreparedStatement ps4 = con.prepareStatement(
                                "DELETE FROM patient WHERE id=?");
                        ps4.setInt(1, did);

                        int deleted = ps4.executeUpdate();
                        System.out.println(deleted > 0 ? "✔ Patient Deleted!" : "❌ ID Not Found");
                        break;

                    // ---------------------------------
                    case 6:
                        con.close();
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
