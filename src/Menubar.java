//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.SQLException;
//
//public class Menubar extends JFrame{
//    public Menubar(){
//        setTitle("Fedha Menubar");
//        setSize(711,480);
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
//        // Create menu items
//        JMenuItem menuItemGoBack = new JMenuItem("Back");
//        JMenuItem menuItemSignOut = new JMenuItem("Sign Out");
//
//        // Add menu items to menu
//        menu.add(menuItemGoBack);
//        menu.add(menuItemSignOut);
//
//        menuItemGoBack.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Implement go back functionality here
//                new Login().setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//
//        menuItemSignOut.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Implement sign out functionality here
//                new Login().setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//
//        // Create a panel with null layout for custom component positioning
//        JPanel panel = new JPanel();
//        panel.setLayout(null);
//        panel.setBackground(new Color(0x0CFFD9));
//        add(panel);
//
//        JButton btnMember=new JButton("Member");
//        btnMember.setBounds(0, 0, 100, 30);
//        btnMember.setBackground(new Color(0xCDD2D6));
//        panel.add(btnMember);
//
//        JButton btnLoan=new JButton("Loan");
//        btnLoan.setBounds(100, 0, 100, 30);
//        btnLoan.setBackground(new Color(0xCDD2D6));
//        panel.add(btnLoan);
//
//        JButton btnDeposits=new JButton("Deposits");
//        btnDeposits.setBounds(200, 0, 100, 30);
//        btnDeposits.setBackground(new Color(0xCDD2D6));
//        panel.add(btnDeposits);
//
//        JButton btnMonthlyRepayment=new JButton("Monthly Repayments");
//        btnMonthlyRepayment.setBounds(300, 0, 190, 30);
//        btnMonthlyRepayment.setBackground(new Color(0xCDD2D6));
//        panel.add(btnMonthlyRepayment);
//
//        JButton btnInfo=new JButton("Information");
//        btnInfo.setBounds(490, 0, 150, 30);
//        btnInfo.setBackground(new Color(0xCDD2D6));
//        panel.add(btnInfo);
//
//        JButton btnExit=new JButton("Exit");
//        btnExit.setBounds(640, 0, 70, 30);
//        btnExit.setBackground(new Color(0xCDD2D6));
//        panel.add(btnExit);
//
//        btnMember.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Members members=new Members();
//                members.setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//
//        btnLoan.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Loan loan=new Loan();
//                loan.setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//
//        btnDeposits.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Deposits deposits=new Deposits();
//                deposits.setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//
//        btnMonthlyRepayment.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MonthlyRepayment monthlyRepayment=new MonthlyRepayment();
//                monthlyRepayment.setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//
//        btnInfo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String memberId = JOptionPane.showInputDialog("Please enter your Member ID:");
//                if (memberId != null && !memberId.trim().isEmpty()) {
//                    try {
//                        if (FedhaDatabase.isMemberIdExists(memberId)) {
//                            MemberInformation memberInformation = new MemberInformation(memberId);
//                            memberInformation.setVisible(true);
//                            Menubar.this.dispose();
//                        } else {
//                            JOptionPane.showMessageDialog(null, "You must be a member to view your information.");
//                        }
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Member ID cannot be empty.");
//                }
//            }
//        });
//
//        btnExit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Exit exit=new Exit();
//                exit.setVisible(true);
//                Menubar.this.dispose();
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Menubar();
//            }
//        });
//    }
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Menubar extends JFrame {
    public Menubar() {
        setTitle("Fedha Menubar");
        setSize(711, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create menu
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        // Create menu items
        JMenuItem menuItemGoBack = new JMenuItem("Back");
        JMenuItem menuItemSignOut = new JMenuItem("Sign Out");

        // Add menu items to menu
        menu.add(menuItemGoBack);
        menu.add(menuItemSignOut);

        menuItemGoBack.addActionListener(e -> {
            new Login().setVisible(true);
            Menubar.this.dispose();
        });

        menuItemSignOut.addActionListener(e -> {
            new Login().setVisible(true);
            Menubar.this.dispose();
        });

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x0CFFD9));
        add(mainPanel);

        // Sidebar panel for labels
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(0xCDD2D6));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Create labels for each "button" with spacing
        sidebar.add(createSidebarLabel("Member"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing

        sidebar.add(createSidebarLabel("Loan"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(createSidebarLabel("Deposits"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(createSidebarLabel("Monthly Repayments"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(createSidebarLabel("Information"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(createSidebarLabel("Exit"));

        // Add listeners to labels
        sidebar.getComponent(0).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Members members = new Members();
                members.setVisible(true);
                Menubar.this.dispose();
            }
        });

        sidebar.getComponent(2).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Loan loan = new Loan();
                loan.setVisible(true);
                Menubar.this.dispose();
            }
        });

        sidebar.getComponent(4).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Deposits deposits = new Deposits();
                deposits.setVisible(true);
                Menubar.this.dispose();
            }
        });

        sidebar.getComponent(6).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MonthlyRepayment monthlyRepayment = new MonthlyRepayment();
                monthlyRepayment.setVisible(true);
                Menubar.this.dispose();
            }
        });

        sidebar.getComponent(8).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String memberId = JOptionPane.showInputDialog("Please enter your Member ID:");
                if (memberId != null && !memberId.trim().isEmpty()) {
                    try {
                        if (FedhaDatabase.isMemberIdExists(memberId)) {
                            MemberInformation memberInformation = new MemberInformation(memberId);
                            memberInformation.setVisible(true);
                            Menubar.this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "You must be a member to view your information.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        sidebar.getComponent(10).addMouseListener(new LabelMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Exit exit = new Exit();
                exit.setVisible(true);
                Menubar.this.dispose();
            }
        });

        setVisible(true);
    }

    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 40));
        label.setOpaque(true);
        label.setBackground(new Color(0xCDD2D6));
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private abstract static class LabelMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            ((JLabel) e.getSource()).setBackground(new Color(0xAAB0B5));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((JLabel) e.getSource()).setBackground(new Color(0xCDD2D6));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menubar::new);
    }
}

