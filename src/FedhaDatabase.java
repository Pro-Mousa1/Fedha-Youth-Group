import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class FedhaDatabase {
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
    private static final String username = "root";
    private static final String password = "Mousa@muigai123!";

    public static Connection connect() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish connection
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    // Start of Admin Data
    public static void insertAdmin(String adminName, String authenticationNumber, String password) {
        String sql = "INSERT INTO admin (adminName, authenticationNumber, password) VALUES (?, ?, ?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, adminName);
            pstmt.setString(2, authenticationNumber);
            pstmt.setString(3, password);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Admin inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting admin!");
            e.printStackTrace();
        }
    }

    public static boolean checkAdminCredentials(String authenticationNumber, String password) {
        String sql = "SELECT * FROM admin WHERE authenticationNumber = ?";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, authenticationNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // End of Admin Data

    // Start of User Data
    public static boolean userExists(String username, String email) {
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // If a result is returned, the user exists
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("User inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting user!");
            e.printStackTrace();
        }
    }

    public static boolean checkCredentials(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password"); // Adjust this based on your column name
                // Check if entered password matches the stored password
                return storedPassword.equals(password); // Or use hash comparison if needed
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // End of User Data

    // Start of Member Data
    public static void insertMember(String surname, String otherNames, String day, String month, String year, String phone, String email, String memberId, String registrationFees, String activationDate, String status) {
        String sql = "INSERT INTO members (surname,otherNames,day,month,year,phone,email,memberId,registrationFees,activationDate,status) VALUES (?, ?, ?,?,?,?,?,?,?,?,?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, surname);
            pstmt.setString(2, otherNames);
            pstmt.setString(3, day);
            pstmt.setString(4, month);
            pstmt.setString(5, year);
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setString(8, memberId);
            pstmt.setString(9, registrationFees);
            pstmt.setString(10, activationDate);
            pstmt.setString(11, status);
            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Member added successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Member failed");
            e.printStackTrace();
        }
    }
    // End of Member Data

    // Start of Loan Data
    public static void insertLoan(String memberId, double loanAmount, String loanType, String interestRate, String repaymentPeriod, String loanDue, String guarantor, String loanRepaymentAmount, String loanDate) {
        String sql = "INSERT INTO loans (memberId,loanAmount,loanType,interestRate,repaymentPeriod,loanDue,guarantor,loanRepaymentAmount,loanDate) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, memberId);
            pstmt.setDouble(2, loanAmount);
            pstmt.setString(3, loanType);
            pstmt.setString(4, interestRate);
            pstmt.setString(5, repaymentPeriod);
            pstmt.setString(6, loanDue);
            pstmt.setString(7, guarantor);
            pstmt.setString(8, loanRepaymentAmount);
            pstmt.setString(9, loanDate);
            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Loan applied successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Loan failed");
            e.printStackTrace();
        }
    }

    public static boolean isMemberIdExists(String memberId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM members WHERE memberId = ?";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Returns true if memberId exists
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static List<String> getAllMembers() {
        List<String> members = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
            String username = "root";
            String password = "Mousa@muigai123!";

            // Establish the connection
            conn = DriverManager.getConnection(url, username, password);

            // SQL query to get all members (adjust table and column names)
            String sql = "SELECT surname, otherNames FROM members";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Add each member's full name to the list
            while (rs.next()) {
                String surname = rs.getString("surname");
                String otherNames = rs.getString("otherNames");
                String fullName = surname + " " + otherNames; // Concatenate surname and other names
                members.add(fullName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return members;
    }

    public static String getMemberFullName(String memberId) {
        String fullName = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connect();
            String sql = "SELECT surname, otherNames FROM members WHERE memberId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            // Concatenate surname and other names
            if (rs.next()) {
                String surname = rs.getString("surname");
                String otherNames = rs.getString("otherNames");
                fullName = surname + " " + otherNames;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return fullName;
    }

    public static double getTotalShares(String memberId) {
        double totalDeposits = FedhaDatabase.getTotalDepositsByMemberId(memberId);
        if (totalDeposits == -1) {
            System.out.println("Member not found or error occurred while fetching deposits.");
            return 0.0;
        }
        return totalDeposits;
    }

    public static double getTotalDepositsByMemberId(String memberId) {
        double totalDeposits = -1;

        try {
            String sql = "SELECT SUM(sharesContribution) FROM deposits WHERE memberId = ?";
            Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalDeposits = rs.getDouble(1); // Get the sum of deposits
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalDeposits;
    }

    public static double getFixedDeposits(String memberId) {
        double sharesContribution = 0.0;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fedha_youth_group_schema", "root", "Mousa@muigai123!");
             PreparedStatement stmt = conn.prepareStatement("SELECT sharesContribution FROM deposits WHERE memberId = ?")) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sharesContribution = rs.getDouble("sharesContribution");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sharesContribution;
    }

    public static boolean isEligibleForLoan(String memberId) {
        try {
            String sql = "SELECT COUNT(DISTINCT MONTH(depositsDate)) AS monthsContributed " +
                    "FROM deposits WHERE memberId = ? AND depositsDate >= DATE_SUB(NOW(), INTERVAL 6 MONTH)";
            Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int monthsContributed = rs.getInt("monthsContributed");
                return monthsContributed >= 6;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // End of Loan Data

    //Start of Deposit Data
    public static void insertSharesContribution(String memberId, String sharesContribution, String depositsDate) {
        String sql = "INSERT INTO deposits (memberId, sharesContribution, depositsDate) VALUES (?, ?, ?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, memberId);
            pstmt.setString(2, sharesContribution);
            pstmt.setString(3, depositsDate);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Shares contribution inserted successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting shares contribution!");
            e.printStackTrace();
        }
    }
    // Method to check if the member has already deposited in the given month and year
    public static boolean hasAlreadyDeposited(String memberId, int year, int month) {
        String sql = "SELECT COUNT(*) FROM deposits WHERE memberId = ? AND YEAR(depositsDate) = ? AND MONTH(depositsDate) = ?";

        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, memberId);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    //End of Deposit Data

    // Start of Monthly Repayment
    public static void insertMonthlyRepayment(String memberId, double loanBorrowed, double loanRepaymentAmount, double loanBalance, String loanRepaymentDate) {
        String sql = "INSERT INTO monthlyRepayment (memberId, loanBorrowed, loanRepaymentAmount, loanBalance, loanRepaymentDate) VALUES (?,?,?,?,?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, memberId);
            pstmt.setDouble(2, loanBorrowed);
            pstmt.setDouble(3, loanRepaymentAmount);
            pstmt.setDouble(4, loanBalance);
            pstmt.setString(5, loanRepaymentDate);

            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Loan repayment successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while inserting repaying the loan!");
            e.printStackTrace();
        }
    }

    public static double getTotalLoansByMemberId(String memberId) {
        double totalLoanAmount = 0.0;
        String sql = "SELECT SUM(loanAmount) FROM loans WHERE memberId = ?";

        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalLoanAmount = rs.getDouble(1); // Get the sum of all loans
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalLoanAmount;
    }

    public static Optional<Double> getLoanAmount(String memberId) {
        String sql = "SELECT loanAmount FROM loans WHERE memberId = ? AND loanAmount > 0 LIMIT 1";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getDouble("loanAmount")); // Return the loan amount if found
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty(); // Return empty if no outstanding loan
    }

    public static double getTotalRepaymentsByMemberId(String memberId) {
        double totalRepaymentAmount = 0.0;
        String sql = "SELECT SUM(loanRepaymentAmount) FROM monthlyRepayment WHERE memberId = ?";

        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalRepaymentAmount = rs.getDouble(1); // Get the sum of all repayments
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalRepaymentAmount;
    }

    // Method to update the loan amount
    public static void updateLoanAmount(String memberId, double repaymentAmount) throws SQLException {
        // SQL query to update the loan amount by subtracting the repayment amount
        String sql = "UPDATE loans SET loanAmount = loanAmount - ? WHERE memberId = ? AND loanAmount >= ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, repaymentAmount);
            pstmt.setString(2, memberId);
            pstmt.setDouble(3, repaymentAmount); // Ensure we don't go negative

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Update failed: no rows affected.");
            }
        }
    }

    public static double getOutstandingLoan(String memberId) throws SQLException {
        double outstandingLoan = 0.0;
        String query = "SELECT loanAmount - IFNULL(SUM(monthlyRepayment.loanRepaymentAmount), 0) AS outstandingLoan " +
                "FROM loans LEFT JOIN monthlyRepayment ON loans.memberId = monthlyRepayment.memberId " +
                "WHERE loans.memberId = ?" +
                "GROUP BY loans.loanAmount";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fedha_youth_group_schema", "root", "Mousa@muigai123!");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                outstandingLoan = rs.getDouble("outstandingLoan");
            }
        }
        return outstandingLoan;
    }

    //End of Monthly Repayment

    // Start of Report Data
    public static void fetchMember(String query, JTable table) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Define table model with column names
            DefaultTableModel model = new DefaultTableModel(new String[]{"Member ID", "Surname", "Other Names", "Registration Fees"}, 0);

            // Populate the table model with data from the ResultSet
            while (resultSet.next()) {
                String memberId = resultSet.getString("memberId");
                String surname = resultSet.getString("surname");
                String otherNames = resultSet.getString("otherNames");
                String registrationFees = resultSet.getString("registrationFees");

                model.addRow(new Object[]{memberId, surname, otherNames, registrationFees});
            }

            // Set model to the table
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchTypeOfLoansData(DefaultTableModel model) {
        model.setRowCount(0);  // Clear existing rows
        try (Statement statement = connect().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT typeOfLoans, interestRate, repaymentPeriod FROM typeOfLoans")) {
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("typeOfLoans"),
                        resultSet.getString("interestRate"),
                        resultSet.getString("repaymentPeriod")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateTypeOfLoan(DefaultTableModel model, int row, int column) {
        String typeOfLoan = model.getValueAt(row, 0).toString();  // Get the loan type
        String columnName = model.getColumnName(column);  // Get column name (either interestRate or repaymentPeriod)
        Object newValue = model.getValueAt(row, column);  // Get the new value from the edited cell

        String updateQuery = "UPDATE typeOfLoans SET " + columnName + " = ? WHERE typeOfLoans = ?";
        try (PreparedStatement statement = connect().prepareStatement(updateQuery)) {
            statement.setObject(1, newValue);  // Set the new value
            statement.setString(2, typeOfLoan);  // Set the loan type
            statement.executeUpdate();  // Execute the update query
            System.out.println(columnName + " updated successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void fetchMemberShares(String query, JTable table) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            DefaultTableModel model = new DefaultTableModel(new String[]{"Member ID", "Surname", "Other Names", "Deposit Amount", "Deposit Date"}, 0);
            while (resultSet.next()) {
                String memberId = resultSet.getString("memberId");
                String surname = resultSet.getString("surname");
                String otherNames = resultSet.getString("otherNames");
                String sharesContribution = resultSet.getString("sharesContribution");
                String depositsDate = resultSet.getString("depositsDate");

                model.addRow(new Object[]{memberId, surname, otherNames, sharesContribution,depositsDate});
            }
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void fetchLoansDebt(String query, JTable table) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Define table model with updated column names
            DefaultTableModel model = new DefaultTableModel(new String[]{"Member ID", "Surname", "Other Names", "Total Loan"}, 0);

            // Populate the table model with data from the ResultSet
            while (resultSet.next()) {
                String memberId = resultSet.getString("memberId");
                String surname = resultSet.getString("surname");
                String otherNames = resultSet.getString("otherNames");
                double totalLoan = resultSet.getDouble("totalLoan");

                model.addRow(new Object[]{memberId, surname, otherNames, totalLoan});
            }

            // Set model to the table
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLoanDetailsForMember(String memberId, int row, JTable loansTable) {
        DefaultTableModel model = (DefaultTableModel) loansTable.getModel();
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT loanAmount, loanDate FROM loans WHERE memberId = ?")) {

            statement.setString(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            // Format the details as a row and insert below the clicked row
            while (resultSet.next()) {
                double loanAmount = resultSet.getDouble("loanAmount");
                Date loanDate = resultSet.getDate("loanDate");

                // Insert a new row with details under the selected member row
                model.insertRow(row + 1, new Object[]{"", "", "Loan: " + loanAmount, "Date: " + loanDate});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchGuarantor(String query, JTable table) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Define table model with column names
            DefaultTableModel model = new DefaultTableModel(new String[]{"Member ID", "Guarantors'"}, 0);

            // Populate the table model with data from the ResultSet
            while (resultSet.next()) {
                String memberId = resultSet.getString("memberId");
                String guarantor = resultSet.getString("guarantor");

                model.addRow(new Object[]{memberId, guarantor});
            }

            // Set model to the table
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void updateMember(String memberId, String surname, String otherNames, String registrationFees) {
//        String updateQuery = "UPDATE members SET surname = ?, otherNames = ?, registrationFees = ? WHERE memberId = ?";
//
//        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
//            pstmt.setString(1, surname);
//            pstmt.setString(2, otherNames);
//            pstmt.setString(3, registrationFees);
//            pstmt.setString(4, memberId);
//
//            int rowsAffected = pstmt.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Member details updated successfully.");
//            } else {
//                System.out.println("No member found with the provided Member ID.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error occurred while updating member details.");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    public static void deleteMember(String memberId) {
//
//        String deleteQuery = "DELETE FROM members WHERE memberId = ?";
//
//        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
//            pstmt.setString(1, memberId);
//
//            int rowsAffected = pstmt.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Member deleted successfully.");
//            } else {
//                System.out.println("No member found with the provided Member ID.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error occurred while deleting member.");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }


//    private boolean verifyAdminPassword() {
//        String inputPassword = JOptionPane.showInputDialog(this, "Enter Admin Password:");
//        return adminPassword.equals(inputPassword);
//    }
//
//    private void editMember(String memberId) {
//        // Logic to edit member details
//        JOptionPane.showMessageDialog(this, "Edit member with ID: " + memberId);
//    }
//
//    private void deleteMember(String memberId) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM members WHERE memberId = ?");
//            statement.setString(1, memberId);
//            statement.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Member deleted successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void editLoan(String memberId) {
//        // Logic to edit loan details
//        JOptionPane.showMessageDialog(this, "Edit loan for member with ID: " + memberId);
//    }
//
//    private void deleteLoan(String memberId) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM loans WHERE memberId = ?");
//            statement.setString(1, memberId);
//            statement.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Loan deleted successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void editGuarantor(String memberId) {
//        // Logic to edit guarantor details
//        JOptionPane.showMessageDialog(this, "Edit guarantor for member with ID: " + memberId);
//    }
//
//    private void deleteGuarantor(String memberId) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM guarantors WHERE memberId = ?");
//            statement.setString(1, memberId);
//            statement.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Guarantor deleted successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void connectToDatabase() {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "yourUsername", "yourPassword");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Database connection failed.");
//        }
//    }
//
//    private void fetchMembersData(JTable table) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM members")) {
//            while (resultSet.next()) {
//                model.addRow(new Object[]{
//                        resultSet.getInt("memberId"),
//                        resultSet.getString("surname"),
//                        resultSet.getString("otherNames"),
//                        resultSet.getDouble("registrationFees")
//                });
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void fetchLoansDebtData(JTable table) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT members.memberId, members.surname, members.otherNames, SUM(loans.loanAmount) AS totalLoan " +
//                     "FROM members JOIN loans ON members.memberId = loans.memberId " +
//                     "GROUP BY members.memberId")) {
//            while (resultSet.next()) {
//                model.addRow(new Object[]{
//                        resultSet.getInt("memberId"),
//                        resultSet.getString("surname"),
//                        resultSet.getString("otherNames"),
//                        resultSet.getDouble("totalLoan")
//                });
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void fetchGuarantorsData(JTable table) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT memberId, guarantor FROM loans WHERE guarantor IS NOT NULL")) {
//            while (resultSet.next()) {
//                model.addRow(new Object[]{
//                        resultSet.getInt("memberId"),
//                        resultSet.getString("guarantor")
//                });
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }// Database Interaction Methods

    // End of Report Data

    // Start of Exit Data
    public static void insertExit(String memberId, String fullName, String guaranteedLoans, String outstandingLoan, String reasonForExit, String noticeDate, String exitingDate) {
        String sql = "INSERT INTO exitRequests(memberId, fullName, guaranteedLoans ,outstandingLoan, reasonForExit, noticeDate, exitinGDate) VALUES (?, ?, ?,?, ?, ?, ?)";
        try (Connection connection = connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            pstmt.setString(1, memberId);
            pstmt.setString(2, fullName);
            pstmt.setString(3, outstandingLoan);
            pstmt.setString(4,guaranteedLoans);
            pstmt.setString(5, reasonForExit);
            pstmt.setString(6, noticeDate);
            pstmt.setString(7, exitingDate);
            // Execute the insert command
            pstmt.executeUpdate();
            System.out.println("Notice successfully sent for approval");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Failed to send notice for approval");
            e.printStackTrace();
        }
    }
    public static double getTotalLoanAmountForMember(String memberId) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";
        double totalLoanAmount = 0.0;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT loanAmount FROM loans WHERE memberId = ?")) {

            statement.setString(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            // Calculate the total loan amount for the specified memberId
            while (resultSet.next()) {
                double loanAmount = resultSet.getDouble("loanAmount");
                totalLoanAmount += loanAmount;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalLoanAmount;
    }
    // Method to fetch guaranteed loans data from the database
    public static String getGuaranteedLoans(String memberId) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String username = "root";
        String password = "Mousa@muigai123!";
        StringBuilder guaranteedLoans = new StringBuilder();

        String query = "SELECT members.memberId, loans.loanAmount " +
                "FROM loans " +
                "JOIN members ON loans.memberId = members.memberId " +
                "WHERE loans.guarantor = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            // Fetch and format the details
            while (resultSet.next()) {
                String memberIdResult = resultSet.getString("memberId");
                double loanAmount = resultSet.getDouble("loanAmount");

                // Append formatted result to guaranteedLoans
                guaranteedLoans.append("Member ID: ").append(memberIdResult)
                        .append(", Loan: ").append(loanAmount)
                        .append(";\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return guaranteedLoans.toString();
    }

    public static boolean hasMemberExited(String memberId) {
        String query = "SELECT COUNT(*) FROM exitRequests WHERE memberId = ?";

        try (Connection connection = connect();PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    // End of Exit Data

    // Start of MemberInformation Data

     // Fetch member details
    public static Map<String, String> getMemberDetails(String memberId) throws SQLException, ClassNotFoundException {
        String query = "SELECT surname, otherNames,day,month,year,phone, email, registrationFees, activationDate, status FROM members WHERE memberId = ?";
        try (Connection conn = connect();PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Map<String, String> memberData = new HashMap<>();
                // Concatenate surname and otherNames to form fullName
                String fullName = rs.getString("surname") + " " + rs.getString("otherNames");
                memberData.put("fullName", fullName);
                memberData.put("day",rs.getString("day"));
                memberData.put("month",rs.getString("month"));
                memberData.put("year",rs.getString("year"));
                memberData.put("phone", rs.getString("phone"));
                memberData.put("email", rs.getString("email"));
                memberData.put("registrationFees", rs.getString("registrationFees"));
                memberData.put("activationDate", rs.getString("activationDate"));
                memberData.put("status", rs.getString("status"));
                return memberData;
            }
        }
        return null;
    }

    // Method to update specific member details
    public static void updateMember(String memberId, String day, String month, String year, String phone, String email, String status, String updateDate) throws SQLException, ClassNotFoundException {
        // Convert day, month, and year into a proper date format for SQL
        LocalDate dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        // SQL query to update member information in the members table
        String sql = "UPDATE members SET day = ?, month =?,year =?, phone = ?, email = ?, status = ?, updateDate = ? WHERE memberId = ?";

        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the SQL query
            pstmt.setString(1, day);
            pstmt.setString(2, month);
            pstmt.setString(3, year);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, status);
            pstmt.setString(7,updateDate);
            pstmt.setString(8, memberId);

            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();

            // Optional: Check if the update affected any rows
            if (rowsUpdated == 0) {
                throw new SQLException("Member update failed, no rows affected.");
            }
        }
    }

    public static void getLoanDetails(DefaultTableModel model) {
        // Database credentials and query
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String user = "root";
        String password = "Mousa@muigai123!";
        String query = "SELECT loanAmount, loanType, interestRate, repaymentPeriod, " +
                "loanDue, guarantor, loanRepaymentAmount, loanDate FROM loans";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            model.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getDouble("loanAmount"),
                        rs.getString("loanType"),
                        rs.getString("interestRate"),
                        rs.getString("repaymentPeriod"),
                        rs.getDate("loanDue"),
                        rs.getString("guarantor"),
                        rs.getDouble("loanRepaymentAmount"),
                        rs.getDate("loanDate")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getDepositDetails(DefaultTableModel model) {
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String user = "root";
        String password = "Mousa@muigai123!";
        String query = "SELECT memberId, sharesContribution, depositsDate FROM deposits";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            model.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getString("memberId"),
                        rs.getString("sharesContribution"),
                        rs.getDate("depositsDate"),
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getLoanRepaymentDetails(DefaultTableModel model) {
        // Database credentials and query
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String user = "root";
        String password = "Mousa@muigai123!";
        String query = "SELECT memberId, loanBorrowed, loanRepaymentAmount, loanRepaymentDate FROM monthlyRepayment";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            model.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getString("memberId"),
                        rs.getString("loanBorrowed"),
                        rs.getString("loanRepaymentAmount"),
                        rs.getDate("loanRepaymentDate"),
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getExitDetails(DefaultTableModel model) {
        // Database credentials and query
        String url = "jdbc:mysql://localhost:3306/fedha_youth_group_schema";
        String user = "root";
        String password = "Mousa@muigai123!";
        String query = "SELECT fullName,outstandingLoan,guaranteedLoans,reasonForExit" +
                ",noticeDate,exitingDate FROM exitRequests";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            model.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getString("fullName"),
                        rs.getString("outstandingLoan"),
                        rs.getString("guaranteedLoans"),
                        rs.getString("reasonForExit"),
                        rs.getDate("noticeDate"),
                        rs.getDate("exitingDate")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // End of MemberInformation Data
}
//DELETE FROM `fedha_youth_group_schema`.`deposits` WHERE `iddeposits` = '1';
//DELETE FROM `fedha_youth_group_schema`.`deposits` WHERE `iddeposits` = '2';
//DELETE FROM `fedha_youth_group_schema`.`deposits` WHERE `iddeposits` = '3';
//DELETE FROM `fedha_youth_group_schema`.`deposits` WHERE `iddeposits` = '4';
//ALTER TABLE `fedha_youth_group_schema`.`deposits` DROP COLUMN `iddeposits`;
//ALTER TABLE `fedha_youth_group_schema`.`deposits`
//ADD COLUMN `iddeposits` INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;