//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class Reports extends JFrame {
//    public Reports() {
//        setTitle("Fedha Member Report");
//        setSize(600, 450);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        // Create menu bar
//        JMenuBar menuBar = new JMenuBar();
//        setJMenuBar(menuBar);
//
//        // Create menu
//        JMenu menu = new JMenu("Menu");
//        menuBar.add(menu);
//
//        JMenuItem menuItemSignOut = new JMenuItem("Sign Out");
//        menu.add(menuItemSignOut);
//        menuItemSignOut.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new LoginAdmin().setVisible(true);
//                Reports.this.dispose();
//            }
//        });
//
//        JPanel panel = new JPanel();
//        panel.setLayout(null);
//        panel.setBackground(new Color(0x0800FF));
//        add(panel);
//
//        JButton btnMembers = new JButton("Members");
//        btnMembers.setBounds(0, 0, 100, 25);
//        btnMembers.setForeground(Color.BLACK);
//        btnMembers.setBackground(Color.WHITE);
//        btnMembers.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        panel.add(btnMembers);
//
//        JButton btnLoansBorrowed = new JButton("Loans Debt:");
//        btnLoansBorrowed.setBounds(100, 0, 120, 25);
//        btnLoansBorrowed.setForeground(Color.BLACK);
//        btnLoansBorrowed.setBackground(Color.WHITE);
//        btnLoansBorrowed.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        panel.add(btnLoansBorrowed);
//
//        JButton btnGuarantors = new JButton("Guarantors");
//        btnGuarantors.setBounds(220, 0, 120, 25);
//        btnGuarantors.setForeground(Color.BLACK);
//        btnGuarantors.setBackground(Color.WHITE);
//        btnGuarantors.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        panel.add(btnGuarantors);
//
//        btnMembers.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                MembersLists();
//            }
//        });
//
//        btnLoansBorrowed.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                LoansDebtLists();
//            }
//        });
//
//        btnGuarantors.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                GuarantorsLists();
//            }
//        });
//
//    }
//
//    private void MembersLists() {
//        JTable membersTable = new JTable();
//        // Use the correct method to fetch members data
//        FedhaDatabase.fetchMemberFromDatabase("SELECT memberId, surname, otherNames, registrationFees FROM members", membersTable);
//
//        // Wrap the table in a scroll pane
//        JScrollPane scrollPane = new JScrollPane(membersTable);
//
//        // Display the scroll pane in a new dialog
//        JDialog dialog = new JDialog(this, "Members Report", true);
//        dialog.add(scrollPane, BorderLayout.CENTER);
//        dialog.setSize(550, 350);
//        dialog.setLocationRelativeTo(null);
//        dialog.setVisible(true);
//    }
//    private void LoansDebtLists() {
//        JTable loansTable = new JTable();
//        // Updated query with subquery to avoid GROUP BY error
//        FedhaDatabase.fetchLoansDebtFromDatabase(
//                "SELECT m.memberId, m.surname, m.otherNames, loanSummary.totalLoan " +
//                        "FROM members m " +
//                        "JOIN (SELECT memberId, SUM(loanAmount) AS totalLoan FROM loans GROUP BY memberId) loanSummary " +
//                        "ON m.memberId = loanSummary.memberId",
//                loansTable
//        );
//
//        // Wrap the table in a scroll pane
//        JScrollPane scrollPane = new JScrollPane(loansTable);
//
//        // Display the scroll pane in a new dialog
//        JDialog dialog = new JDialog(this, "Loans Debt Report", true);
//        dialog.add(scrollPane, BorderLayout.CENTER);
//        dialog.setSize(550, 350);
//        dialog.setLocationRelativeTo(null);
//        dialog.setVisible(true);
//    }
//
//    private void GuarantorsLists() {
//        JTable guarantorTable = new JTable();
//        FedhaDatabase.fetchGuarantorFromDatabase("SELECT loans.memberId,loans.guarantor FROM loans JOIN members ON loans.memberId = members.memberId", guarantorTable);
//
//        // Set column widths: make 'memberId' smaller and 'guarantor' wider
//        guarantorTable.getColumnModel().getColumn(0).setPreferredWidth(0);
//        guarantorTable.getColumnModel().getColumn(1).setPreferredWidth(400);
//
//        // Wrap the table in a scroll pane
//        JScrollPane scrollPane = new JScrollPane(guarantorTable);
//        scrollPane.setBounds(30, 40, 500, 300);
//
//        // Display the scroll pane in a new dialog or a panel
//        JDialog dialog = new JDialog(this, "Guarantors Report", true);
//        dialog.add(scrollPane);
//        dialog.setSize(750, 450);
//        dialog.setLocationRelativeTo(null);
//        dialog.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Reports();
//            }
//        });
//    }
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class AdminBar extends JFrame {
    private JPanel mainPanel;
    private final String adminPassword = "";

    private Connection connection;

    public AdminBar() {
        setTitle("Fedha Member Report");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        // Connect to database
        connectToDatabase();

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create menu
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem menuItemSignOut = new JMenuItem("Sign Out");
        menu.add(menuItemSignOut);
        menuItemSignOut.addActionListener(e -> {
            new LoginAdmin().setVisible(true);
            AdminBar.this.dispose();
        });

        // Sidebar panel for list items with grey background
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(150, getHeight()));
        sidebarPanel.setBackground(new Color(0xD3D3D3));

        JLabel lblMembers = createSidebarLabel("Members");
        JLabel lblTypeOfLoans = createSidebarLabel("Type of Loans");
        JLabel lblLoansBorrowed = createSidebarLabel("Loans Debt");
        JLabel lblFixedDeposits = createSidebarLabel("Fixed Deposits");
        JLabel lblGuarantors = createSidebarLabel("Guarantors");
        JLabel lblShares = createSidebarLabel("Shares");

        sidebarPanel.add(lblMembers);
        sidebarPanel.add(lblTypeOfLoans);
        sidebarPanel.add(lblLoansBorrowed);
        sidebarPanel.add(lblFixedDeposits);
        sidebarPanel.add(lblGuarantors);
        sidebarPanel.add(lblShares);

        mainPanel = new JPanel(new BorderLayout());
        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Action listeners for each label to display data in the main panel
        lblMembers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayMembersList();
            }
        });

        lblLoansBorrowed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayLoansDebtList();
            }
        });
        lblTypeOfLoans.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayTypeOfLoansList();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        lblGuarantors.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayGuarantorsList();
            }
        });

        lblShares.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Ask the admin to input a memberId
                String memberIdInput = JOptionPane.showInputDialog(mainPanel, "Enter Member ID to check deposits:");

                // If a valid memberId is entered, fetch data for that member
                if (memberIdInput != null && !memberIdInput.isEmpty()) {
                    displayMemberSharesList(Integer.parseInt(memberIdInput)); // Pass memberId to filter
                }
            }
        });
    }

    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setOpaque(true);
                label.setBackground(new Color(0xDDDDDD));
                label.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setOpaque(false);
                label.repaint();
            }
        });
        return label;
    }

    private void displayMembersList() {
        mainPanel.removeAll();

        JTable membersTable = new JTable(new DefaultTableModel(new String[]{"Member ID", "Surname", "Other Names", "Registration Fees"}, 0));
        fetchMembersData(membersTable);

        JScrollPane scrollPane = new JScrollPane(membersTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnAddMember = new JButton("Add Member");
        JButton btnEditMember = new JButton("Edit Member");
        JButton btnDeleteMember = new JButton("Delete Member");

        bottomPanel.add(btnAddMember);
        bottomPanel.add(btnEditMember);
        bottomPanel.add(btnDeleteMember);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnAddMember.addActionListener(e -> new Members().setVisible(true));

        btnEditMember.addActionListener(e -> {
            int selectedRow = membersTable.getSelectedRow();
            if (selectedRow != -1) {
                if (verifyAdminPassword()) {
                    String memberId = membersTable.getValueAt(selectedRow, 0).toString();
                    editMember(memberId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a member to edit.");
            }
        });

        btnDeleteMember.addActionListener(e -> {
            int selectedRow = membersTable.getSelectedRow();
            if (selectedRow != -1) {
                if (verifyAdminPassword()) {
                    String memberId = membersTable.getValueAt(selectedRow, 0).toString();
                    deleteMember(memberId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            }
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayLoansDebtList() {
        mainPanel.removeAll();
        JTable loansTable = new JTable(new DefaultTableModel(new String[]{"Member ID", "Surname", "Other Names", "Total Loan"}, 0));
        fetchLoansDebtData(loansTable);
        JScrollPane scrollPane = new JScrollPane(loansTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnEditLoan = new JButton("Edit Loan");
        JButton btnDeleteLoan = new JButton("Delete Loan");

        bottomPanel.add(btnEditLoan);
        bottomPanel.add(btnDeleteLoan);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnEditLoan.addActionListener(e -> {
            int selectedRow = loansTable.getSelectedRow();
            if (selectedRow != -1) {
                if (verifyAdminPassword()) {
                    String memberId = loansTable.getValueAt(selectedRow, 0).toString();
                    editLoan(memberId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a loan to edit.");
            }
        });

        btnDeleteLoan.addActionListener(e -> {
            int selectedRow = loansTable.getSelectedRow();
            if (selectedRow != -1) {
                if (verifyAdminPassword()) {
                    String memberId = loansTable.getValueAt(selectedRow, 0).toString();
                    deleteLoan(memberId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a loan to delete.");
            }
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayTypeOfLoansList() {
        mainPanel.removeAll();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Type of Loan", "Interest Rate", "Repayment Period"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        JTable loansTable = new JTable(model);
        FedhaDatabase fetcher = new FedhaDatabase();
        fetcher.fetchTypeOfLoansData(model);
        loansTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                fetcher.updateTypeOfLoan(model, row, column);
            }
        });
        JScrollPane scrollPane = new JScrollPane(loansTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();

    }

    private void displayGuarantorsList() {
        mainPanel.removeAll();

        JTable guarantorTable = new JTable(new DefaultTableModel(new String[]{"Member ID", "Guarantor"}, 0));
        fetchGuarantorsData(guarantorTable);

        JScrollPane scrollPane = new JScrollPane(guarantorTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnEditGuarantor = new JButton("Edit Guarantor");
        JButton btnDeleteGuarantor = new JButton("Delete Guarantor");

        bottomPanel.add(btnEditGuarantor);
        bottomPanel.add(btnDeleteGuarantor);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnEditGuarantor.addActionListener(e -> {
            int selectedRow = guarantorTable.getSelectedRow();
            if (selectedRow != -1) {
                if (verifyAdminPassword()) {
                    String memberId = guarantorTable.getValueAt(selectedRow, 0).toString();
                    editGuarantor(memberId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a guarantor to edit.");
            }
        });

        btnDeleteGuarantor.addActionListener(e -> {
            int selectedRow = guarantorTable.getSelectedRow();
            if (selectedRow != -1) {
                if (verifyAdminPassword()) {
                    String memberId = guarantorTable.getValueAt(selectedRow, 0).toString();
                    deleteGuarantor(memberId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a guarantor to delete.");
            }
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayMemberSharesList(int memberId) {
        mainPanel.removeAll();
        JTable memberSharesTable = new JTable(new DefaultTableModel(new String[]{"Member ID", "Surname", "Other Names", "Deposit Amount", "Deposit Date"}, 0));

        // Pass the memberId to the method that fetches the data
        fetchMemberSharesData(memberSharesTable, memberId);

        JScrollPane scrollPane = new JScrollPane(memberSharesTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private boolean verifyAdminPassword() {
        String inputPassword = JOptionPane.showInputDialog(this, "Enter Admin Password:");
        return adminPassword.equals(inputPassword);
    }

    private void editMember(String memberId) {
        // Logic to edit member details
        JOptionPane.showMessageDialog(this, "Edit member with ID: " + memberId);
    }

    private void deleteMember(String memberId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM members WHERE memberId = ?");
            statement.setString(1, memberId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Member deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editLoan(String memberId) {
        // Logic to edit loan details
        JOptionPane.showMessageDialog(this, "Edit loan for member with ID: " + memberId);
    }

    private void deleteLoan(String memberId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM loans WHERE memberId = ?");
            statement.setString(1, memberId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Loan deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editGuarantor(String memberId) {
        // Logic to edit guarantor details
        JOptionPane.showMessageDialog(this, "Edit guarantor for member with ID: " + memberId);
    }

    private void deleteGuarantor(String memberId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM guarantors WHERE memberId = ?");
            statement.setString(1, memberId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Guarantor deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fedha_youth_group_schema", "root", "Mousa@muigai123!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed.");
        }
    }

    private void fetchMembersData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM members")) {
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("memberId"),
                        resultSet.getString("surname"),
                        resultSet.getString("otherNames"),
                        resultSet.getDouble("registrationFees")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchLoansDebtData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT members.memberId, members.surname, members.otherNames, SUM(loans.loanAmount) AS totalLoan " +
                     "FROM members JOIN loans ON members.memberId = loans.memberId " +
                     "GROUP BY members.memberId, members.surname, members.otherNames")) { // Added surname and otherNames to GROUP BY
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("memberId"),
                        resultSet.getString("surname"),
                        resultSet.getString("otherNames"),
                        resultSet.getDouble("totalLoan")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchGuarantorsData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT memberId, guarantor FROM loans WHERE guarantor IS NOT NULL")) {
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("memberId"),
                        resultSet.getString("guarantor")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void fetchMemberSharesData(JTable table, int memberId) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Modify the query to filter by the provided memberId
        String query = "SELECT deposits.memberId, members.surname, members.otherNames, deposits.sharesContribution, deposits.depositsDate " +
                "FROM deposits JOIN members ON members.memberId = deposits.memberId " +
                "WHERE deposits.memberId = ?"; // Use parameterized query for safety

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fedha_youth_group_schema", "root", "Mousa@muigai123!");
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the memberId parameter
            statement.setInt(1, memberId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getInt("memberId"),
                            resultSet.getString("surname"),
                            resultSet.getString("otherNames"),
                            resultSet.getString("sharesContribution"),
                            resultSet.getString("depositsDate")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminBar();
            }
        });
    }
}
