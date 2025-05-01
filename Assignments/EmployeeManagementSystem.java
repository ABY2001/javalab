package pattern;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmployeeManagementSystem {

    static final String DB_URL = "jdbc:mysql://localhost:3306/javaassignment";
    static final String USER = "root";
    static final String PASS = "viratkohli7593";

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            int choice;
            do {
                System.out.println("\n===== Employee Management Menu =====");
                System.out.println("1. Add New Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee by ID");
                System.out.println("4. Delete Employee by ID");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewEmployees();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 5);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addEmployee() {
        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();

            if (!isValidEmail(email)) {
                System.out.println("Invalid email format!");
                return;
            }

            System.out.print("Enter salary: ");
            double salary = sc.nextDouble();
            sc.nextLine(); // consume newline

            String query = "INSERT INTO employees(name, email, salary) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();

            System.out.println("Employee added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void viewEmployees() {
        try {
            String query = "SELECT * FROM employees";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.printf("\n%-5s %-20s %-30s %-10s\n", "ID", "Name", "Email", "Salary");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-30s %-10.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateEmployee() {
        try {
            System.out.print("Enter employee ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new name: ");
            String name = sc.nextLine();
            System.out.print("Enter new email: ");
            String email = sc.nextLine();

            if (!isValidEmail(email)) {
                System.out.println("Invalid email format!");
                return;
            }

            System.out.print("Enter new salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            String query = "UPDATE employees SET name=?, email=?, salary=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setDouble(3, salary);
            stmt.setInt(4, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteEmployee() {
        try {
            System.out.print("Enter employee ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String query = "DELETE FROM employees WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }
}
