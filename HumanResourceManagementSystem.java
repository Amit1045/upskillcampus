
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class HRMS {

    public static final String url = "jdbc:mysql://localhost:3306/hrms";
    public static final String username = "root";
    public static final String password = "amitkumar@12";
    public HRMS() {
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var4) {
            System.out.println(var4.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "amitkumar@12");

            while(true) {
                System.out.println();
                System.out.println("HUMAN RESOURCE MANAGEMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Add new Employee");
                System.out.println("2. View Employee detail");
                System.out.println("3. Search Employee");
                System.out.println("4. Update Employee Detail");
                System.out.println("5. Delete Employee Detail");
                System.out.println("6. Search the Attendance Status ");
                System.out.println("7. Mark attendance of Employee");
                System.out.println("8. Leave Management of Employee");
                System.out.println("0. Exit");
                System.out.println("Choose an option : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        exit();
                        sc.close();
                        return;
                    case 1:
                        RegisterEmployee(con, sc);
                        break;
                    case 2:
                        ViewRegistration(con);
                        break;

                    case 3:
                        SearchEmployee(con, sc);
                        break;
                    case 4:
                        UpdateEmployee(con, sc);
                        break;
                    case 5:
                        DeleteEmployeeDetail(con, sc);
                        break;
                    case 6:
                        SearchEmployeeAttendance(con,sc);
                        break;
                    case 7:
                        markAttendance(con , sc);
                        break;
                    case 8:
                        LeaveManagement(con,sc);
                        break;
                      default:
                        System.out.println("Invalid choice. try again.");
                }
            }
        }catch (SQLException | InterruptedException e){
            e.printStackTrace();
        }
        
}

        // Emplyoee Registration code

    private static void RegisterEmployee(Connection con, Scanner sc) {
        try {
            System.out.println("\n............................\n");
            System.out.println("Here we add Employees Data ");
            System.out.println("Enter the Id Number: ");
            int EmpId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter Employee name: ");
            String EmpName=sc.nextLine();
            System.out.println("Enter Employee contact number: ");
            String EmpContactNumber = sc.next();
            sc.nextLine();
            System.out.println("Enter Employee Designation: ");
            String EmpDesignation = sc.nextLine();
            System.out.println("Enter Employee Department: ");
            String EmpDepartment = sc.nextLine();

            String sql = "INSERT INTO Human_Management(Emp_id , Emp_name, Emp_contact, Designation, Department) VALUES (" + EmpId + ", '" + EmpName + "', '" + EmpContactNumber + "', '" + EmpDesignation + "', '" + EmpDepartment + "')";
            Statement stmt = con.createStatement();

            try {
                int affectedRow = stmt.executeUpdate(sql);
                if (affectedRow > 0) {
                    System.out.println("Employee Registration successful! and rows affected is: " + affectedRow);
                } else {
                    System.out.println("Registration failed.");
                }
            } catch (Throwable var10) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }

                throw var10;
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException var11) {
            var11.printStackTrace();
        }

    }

        // View Employee detail code

    private static void ViewRegistration(Connection con) throws SQLException {
        String sql = "select * from Human_Management";
        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery(sql);

            try {
                System.out.println("Current Registration: ");
                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                System.out.println("| Employee ID | Employee_name      | Contact_number   | Designation       | Department       | Registration_Time     |");
                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");

                while(rs.next()) {
                    int EmpId = rs.getInt("Emp_id");
                    String EmpName = rs.getString("Emp_name");
                    String ContactNumber = rs.getString("Emp_contact");
                    String EmpDesignation= rs.getString("Designation");
                    String EmpDepartment = rs.getString("Department");
                    String RegistrationDate = rs.getTimestamp("Date").toString();
                    System.out.printf("| %-11d | %-18s | %-16s | %-17s | %-16s | %17s |\n", EmpId, EmpName, ContactNumber, EmpDesignation,EmpDepartment, RegistrationDate);
                }

                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
            } catch (Throwable var11) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                    }
                }

                throw var11;
            }

            if (rs != null) {
                rs.close();
            }
        } catch (Throwable var12) {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Throwable var9) {
                    var12.addSuppressed(var9);
                }
            }

            throw var12;
        }

        if (stmt != null) {
            stmt.close();
        }

    }

    // Searching for Employee in the table

    private static void SearchEmployee(Connection con, Scanner sc) {
        try {
            System.out.println("1. For Search using Id ");
            System.out.println("2. For Search using name");
            System.out.println("3. For Search using Department");
            int option=sc.nextInt();
            switch(option){
                case 1:
                    System.out.println("Enter the Employee ID: ");
                    int EmpId = sc.nextInt();
                    SearchById(con ,EmpId);
                    break;
                case 2:
                    System.out.println("Enter the Employee Name: ");
                    String EmpName=sc.next();
                    SearchByName(con,EmpName);
                    break;
                case 3:
                    System.out.println("Enter the Employee Department");
                    String EmpDepartment=sc.next();
                    SearchByDepartment(con , EmpDepartment);
                    break;
                default:
                    System.out.println("It's Incorrect, Choose the correct Option Please .");
            }

        } catch (SQLException var13) {
            var13.printStackTrace();
        }
    }

    // SearchById

    private  static  void SearchById(Connection con , int EmpId) throws SQLException {

        String sql = "SELECT * FROM Human_Management WHERE Emp_id=" +EmpId + ";";
        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery(sql);

            try {
                if (rs.next()) {
                    System.out.println("Employee data as per Given Id : ");
                    System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                    System.out.println("| Employee ID | Employee_name      | Contact_number   | Designation       | Department       | Registration_Time     |");
                    System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                    int EmpId1 = rs.getInt("Emp_id");
                    String EmpName1 = rs.getString("Emp_name");
                    String ContactNumber = rs.getString("Emp_contact");
                    String EmpDesignation = rs.getString("Designation");
                    String EmpDepartment = rs.getString("Department");
                    String RegistrationDate = rs.getTimestamp("Date").toString();
                    System.out.printf("| %-11d | %-18s | %-16s | %-17s | %-16s | %17s |\n", EmpId1, EmpName1, ContactNumber, EmpDesignation, EmpDepartment, RegistrationDate);

                    System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");

                } else {
                    System.out.println("Employee id Not found ");
                }
            } catch (Throwable var11) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                    }
                }
            }
        } finally {

        }
    }


// Seach by Name function

    private static void SearchByName(Connection con, String  EmpName) throws SQLException {

        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM Human_Management WHERE Emp_name ='" + EmpName + "';";

        try {
            ResultSet rs = stmt.executeQuery(sql);

            try { System.out.println("Each Registration As your Demanding Name...");
                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                System.out.println("| Employee ID | Employee_name      | Contact_number   | Designation       | Department       | Registration_Time     |");
                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                while(rs.next()) {
                   int EmpId1 = rs.getInt("Emp_id");
                    String EmpName1 = rs.getString("Emp_name");
                    String ContactNumber = rs.getString("Emp_contact");
                    String EmpDesignation = rs.getString("Designation");
                    String EmpDepartment = rs.getString("Department");
                    String RegistrationDate = rs.getTimestamp("Date").toString();
                    System.out.printf("| %-11d | %-18s | %-16s | %-17s | %-16s | %17s |\n", EmpId1, EmpName1, ContactNumber, EmpDesignation, EmpDepartment, RegistrationDate);

                    System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                }
            } catch (Throwable var11) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                    }
                }
            }
        } finally {

        }
    }

//  Show employee data by using department

    private static void SearchByDepartment(Connection con, String EmpDepartment) throws SQLException {

        String sql = "SELECT * FROM Human_Management WHERE Department ='" + EmpDepartment + "';";
        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(sql);

            try {
                System.out.println("Each Registration As your Department...");
                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                System.out.println("| Employee ID | Employee_name      | Contact_number   | Designation       | Department       | Registration_Time     |");
                System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");

                while (rs.next()) {
                   int EmpId1 = rs.getInt("Emp_id");
                    String EmpName1 = rs.getString("Emp_name");
                    String ContactNumber = rs.getString("Emp_contact");
                    String EmpDesignation = rs.getString("Designation");
                    String EmDpt = rs.getString("Department");
                    String RegistrationDate = rs.getTimestamp("Date").toString();
                    System.out.printf("| %-11d | %-18s | %-16s | %-17s | %-16s | %17s |\n", EmpId1, EmpName1, ContactNumber, EmpDesignation, EmDpt, RegistrationDate);
                    System.out.println("+-------------+--------------------+------------------+-------------------+------------------+-----------------------+");
                }
            } catch (Throwable var11) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                    }
                }
            }
        } finally {

        }
    }


        //Update Employee data

    private static void UpdateEmployee(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to update: ");
        int EmpId = sc.nextInt();
        sc.nextLine();
        if (!EmployeeDataExists(con, EmpId)) {
            System.out.println("Registration not found for the given ID.");
        } else {
            System.out.println("Enter Employee name: ");
            String EmpName = sc.next();
            sc.nextLine();
            System.out.println("Enter Employee contact number: ");
            String EmpContactNumber = sc.next();
            System.out.println("Enter Employee Designation: ");
            String EmpDesignation = sc.next();
            System.out.println("Enter Employee Department: ");
            String EmpDepartment = sc.next();

            String sql = "UPDATE Human_Management SET Emp_name = '" + EmpName + "', Emp_contact =  '" + EmpContactNumber + "' + , Designation = '" + EmpDesignation + "',Department='" + EmpDepartment + "' WHERE Emp_id = " +EmpId;
            try {
                Statement stmt = con.createStatement();

                try {int affected = stmt.executeUpdate(sql);
                    if (affected > 0) {
                        System.out.println("Employee Data has been UPDATED !!");
                    } else {
                        System.out.println("Employee Data Updation is Failed ");
                    }
                } catch (Throwable var11) {
                    if (stmt != null) {
                        try {
                            stmt.close();

                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }

                    throw var11;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // Detele the data of Employee


    private static void DeleteEmployeeDetail(Connection con, Scanner sc) {
        try {
            System.out.println("Enter the Employee ID to delete: ");
            int EmpId = sc.nextInt();
            if (!EmployeeDataExists(con, EmpId)) {
                System.out.println("Registration not found for the given ID.");
                return;
            }
            String sql1 = "DELETE FROM attendance  WHERE Attendance_id = " + EmpId;
            String  sql2="Delete FROM leave_management WHERE Emp_id = " + EmpId;
            String sql3 = "DELETE FROM Human_Management  WHERE Emp_id = " + EmpId;
                Statement stmt = con.createStatement();

            try {
                int affectedRows1 = stmt.executeUpdate(sql1);
               int  affectedRows2=stmt.executeUpdate(sql2);
               int affectedRows3=stmt.executeUpdate(sql3);
                if ((affectedRows1 > 0) && (affectedRows2>0) && (affectedRows3>0)) {
                    System.out.println("Registered employee data is deleted !");
                } else {
                    System.out.println("Data Deletion Failed !");
                }

            } catch (Throwable var8) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException var9) {
            var9.printStackTrace();
        }

    }

    private static boolean EmployeeDataExists(Connection con, int EmpId) throws SQLException {
        try {
            String sql = "SELECT  Emp_id FROM Human_Management WHERE Emp_id=" + EmpId;
            Statement stmt = con.createStatement();

            try {
                ResultSet rs = stmt.executeQuery(sql);

            } catch (SQLException e) {
                e.printStackTrace();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    // Search Employee Attendance

    private static void SearchEmployeeAttendance(Connection con, Scanner sc) {
        try {
            System.out.println("1. Search Based Specific date ");
            System.out.println("2. Search Based on id ");
            System.out.println("choice : ");
            int choice = sc.nextInt();
            if (choice == 1) {
                    System.out.println("Enter Date (YYYY-MM-DD): ");
                    String checkDate = sc.nextLine();
                    sc.nextLine();
                    if(isValidDate(checkDate)) {
                        String sql = "SELECT  h.Emp_name, a.date, a.status FROM attendance a JOIN Human_management h ON h.Emp_id = a.Attendance_id WHERE a.date='" + checkDate + "';";
                        fetchAttendance(con, sql);
                    }
            } else if(choice==2) {
                System.out.println("Enter Id: ");
                int Emp_id = sc.nextInt();
                String sql = "SELECT * FROM Human_management h JOIN attendance a ON h.Emp_id = a.Attendance_id WHERE h.Emp_id=" + Emp_id + ";";
                fetchAttendance(con, sql);
            }else {
                System.out.println("Enter the Invalid choice.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private static void fetchAttendance(Connection con, String sql) throws SQLException {

            Statement stmt = con.createStatement();
            try {
                ResultSet rs = stmt.executeQuery(sql);

                try {
                        System.out.println("Fetched data as per Query ");
                        System.out.println("--------------------+------------------------+-------------------------+");
                        System.out.println("| Employee_name     | Registration_Time      | Attendance Status       |");
                        System.out.println("+-------------------+------------------------+-------------------------+");
                    while (rs.next()) {
                        String EmpName1 = rs.getString("Emp_name");
                        String RegistrationDate = rs.getTimestamp("Date").toString();
                        String AttendanceStatus=rs.getString("status");
                        System.out.printf("| %-17s | %-22s | %-23s |\n", EmpName1,RegistrationDate,AttendanceStatus);

                        System.out.println("+-------------------+------------------------+-------------------------+");

                    }
                } catch (Throwable var11) {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }
                    throw var11;
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (Throwable var12) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var9) {
                        var12.addSuppressed(var9);
                    }
                }

                throw var12;
            }

            stmt.close();
        }

    private static void markAttendance(Connection con,Scanner sc) throws SQLException {
        try {
            System.out.println("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Date (YYYY-MM-DD): ");
            String inputDate = sc.nextLine();
            System.out.println("Enter Status (Present/Absent/On Leave): ");
            String status = sc.nextLine();

            if (isValidDate(inputDate)) {
                // Proceed with the rest of the code, e.g., marking attendance
                String query = "INSERT INTO hrms.attendance (Attendance_id, date, status) VALUES (" + empId + ",'" + inputDate + "','" + status + "')";
                Statement stmt = con.createStatement();

                try {
                    int affectedRow = stmt.executeUpdate(query);
                    if (affectedRow > 0) {
                        System.out.println("Attendance marked successfully. rows affected: " + affectedRow);
                    } else {
                        System.out.println("failed to mark attendance .");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }finally {

        }
    }


    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr); // This will throw an exception if the format is incorrect
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    private static void LeaveManagement(Connection con, Scanner sc){
        try {
            System.out.println("1. Add Leave Request");
            System.out.println("2. View Leave Requests");
            System.out.println("3. Approve/Reject Leave Request");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter Start Date (YYYY-MM-DD): ");
                    String startDate = sc.nextLine();
                    System.out.println("Enter End Date (YYYY-MM-DD): ");
                    String endDate = sc.nextLine();
                    System.out.println("Enter Leave Type: ");
                    String leaveType = sc.nextLine();
                    System.out.println("Enter Reason for Leave: ");
                    String reason = sc.nextLine();
                    LeaveManagement.addLeaveRequest(con, empId, startDate, endDate, leaveType, reason);
                    break;

                case 2:

                    LeaveManagement.viewLeaveRequests(con ,sc );
                    break;

                case 3:
                    System.out.println("Enter Emp ID: ");
                    int EmpId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter Status (Approved/Rejected): ");
                    String status = sc.nextLine();
                    LeaveManagement.updateLeaveStatus(con,EmpId, status);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

        } finally {

        }
    }


    public class LeaveManagement {

        private static Scanner scanner = new Scanner(System.in);

        // Method to add a leave request
        public static void addLeaveRequest(Connection con, int empId, String startDate, String endDate, String leaveType, String reason) {
            try {
                String query = "INSERT INTO hrms.Leave_management (Emp_id, Start_date, End_date, Leave_type, Reason) VALUES (" + empId + ",'" + startDate + "','" + endDate + "','" + leaveType + "','" + reason + "' )";
                Statement stmt = con.createStatement();
                int affectedRow = stmt.executeUpdate(query);
                if (affectedRow > 0) {
                    System.out.println("Leave request added successfully.");
                } else {
                    System.out.println("Failed to Insert");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to view leave requests
        public static void viewLeaveRequests(Connection con,Scanner sc) {
            try {
                System.out.println("\n-------------------------");
                System.out.println("1. All Leave Request");
                System.out.println("2. Particular id ");
                int x= scanner.nextInt();
                if(x==1){
                    String query="SELECT  h.Emp_name, l.* FROM leave_management l JOIN Human_management h ON h.Emp_id = l.Emp_id ;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    // Display the output in tabular format
                    System.out.println("+----------------+----------------+-------------+-------------+-----------------+---------+-------------------------+");
                    System.out.printf("|%-14s  | %-14s | %-11s | %-11s | %-15s | %-7s | %-23s |\n","Emp_id" ,"Employee Name", "Start Date", "End Date", "Leave Type", "Status", "Reason");
                    System.out.println("+----------------+----------------+-------------+-------------+-----------------+---------+-------------------------+");

                    while (rs.next()) {
                        int EmpID= rs.getInt("Emp_id");
                        String empName = rs.getString("Emp_name");
                        String startDate = rs.getDate("Start_date").toString();
                        String endDate = rs.getDate("End_date").toString();
                        String leaveType = rs.getString("Leave_type");
                        String status = rs.getString("Status");
                        String reason = rs.getString("Reason");
                        System.out.printf("|%-14d | %-14s | %-11s | %-11s | %-15s | %-7s | %-23s |\n", EmpID,empName, startDate, endDate, leaveType, status, reason);
                        System.out.println("+----------------+----------------+-------------+-------------+-----------------+---------+-------------------------+");
                    }
                }
                else {
                    System.out.println("Enter Employee ID: ");
                    int empId = sc.nextInt();
                String query="SELECT  h.Emp_name, l.* FROM leave_management l JOIN Human_management h ON h.Emp_id = l.Emp_id WHERE h.Emp_id="+empId+";";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                // Display the output in tabular format

                System.out.println("+----------------+----------------+-------------+-------------+-----------------+---------+-------------------------+");
                System.out.printf("|%-14s | %-14s | %-11s | %-11s | %-15s | %-7s | %-23s |\n","Emp_id", "Employee Name", "Start Date", "End Date", "Leave Type", "Status", "Reason");
                System.out.println("+----------------+----------------+-------------+-------------+-----------------+---------+-------------------------+");

                while (rs.next()) {
                    int EmpID= rs.getInt("Emp_id");
                    String empName = rs.getString("Emp_name");
                    String startDate = rs.getDate("Start_date").toString();
                    String endDate = rs.getDate("End_date").toString();
                    String leaveType = rs.getString("Leave_type");
                    String status = rs.getString("Status");
                    String reason = rs.getString("Reason");
                    System.out.printf("|%-14d | %-14s | %-11s | %-11s | %-15s | %-7s | %-23s |\n",EmpID, empName, startDate, endDate, leaveType, status, reason);
                    System.out.println("+----------------+----------------+-------------+-------------+-----------------+---------+-------------------------+");
                }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to approve/reject leave requests
        public static void updateLeaveStatus(Connection con, int EmpId, String status) {
            try {
                String query = "UPDATE hrms.leave_management SET Status ='" + status + "' WHERE Emp_id =" + EmpId + ";";
                Statement stmt = con.createStatement();
                int rowsAffected = stmt.executeUpdate(query);

                if (rowsAffected > 0) {
                    System.out.println("Leave request " + (status.equals("Approved") ? "approved" : "rejected") + " successfully.");
                } else {
                    System.out.println("No leave request found with the provided ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        //Exit Code

    public static void exit() throws InterruptedException {
        System.out.print("exiting System");

        for(int i = 5; i != 0; --i) {
            System.out.print(".");
            Thread.sleep(400L);
        }
        System.out.println();
        System.out.println("Thank you for Visiting my Project.");
    }
}