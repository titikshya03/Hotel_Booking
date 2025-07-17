package packages;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpPage implements ActionListener {
    JFrame frame;
    JTextField tf1, tf2, tf3;
    JPasswordField pass;
    JButton createbtn, back, signIn;
    JLabel headingLabel, nameLabel, contactLabel, emailLabel, passwordLabel, infoLabel;
    JPanel panel, buttonPanel;
    
    Connection con = null;
    PreparedStatement ps = null;

    public SignUpPage() {
        frame = new JFrame("Customer Sign-Up Page");
        frame.setSize(1800, 1000);
        frame.setLocationRelativeTo(null);  // Centering the UI on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 250, 205));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;  // Centers components in the panel

        // Heading Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        headingLabel = new JLabel("CUSTOMER SIGN-UP", JLabel.CENTER);
        headingLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        headingLabel.setForeground(Color.blue);
        panel.add(headingLabel, gbc);

        // Name Input
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        nameLabel = new JLabel("Enter Name:", JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        tf1 = new JTextField(20);
        panel.add(tf1, gbc);

        // Contact Input
        gbc.gridx = 0;
        gbc.gridy = 2;
        contactLabel = new JLabel("Contact No:", JLabel.CENTER);
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(contactLabel, gbc);

        gbc.gridx = 1;
        tf2 = new JTextField(20);
        panel.add(tf2, gbc);

        // Email Input
        gbc.gridx = 0;
        gbc.gridy = 3;
        emailLabel = new JLabel("E-mail ID:", JLabel.CENTER);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        tf3 = new JTextField(20);
        panel.add(tf3, gbc);

        // Password Input
        gbc.gridx = 0;
        gbc.gridy = 4;
        passwordLabel = new JLabel("Password:", JLabel.CENTER);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        pass = new JPasswordField(20);
        panel.add(pass, gbc);

        // Information Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        infoLabel = new JLabel("* All fields are mandatory", JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel.setForeground(Color.RED);
        panel.add(infoLabel, gbc);

        // Button Panel with Background Fix
        gbc.gridy = 6;
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(255, 250, 205));  // Consistent background under buttons

        back = new JButton("Back");
        back.setPreferredSize(new Dimension(150, 40));
        back.addActionListener(this);
        buttonPanel.add(back);

        createbtn = new JButton("Create");
        createbtn.setPreferredSize(new Dimension(150, 40));
        createbtn.addActionListener(this);
        buttonPanel.add(createbtn);

        signIn = new JButton("Sign-In");
        signIn.setPreferredSize(new Dimension(150, 40));
        signIn.addActionListener(this);
        buttonPanel.add(signIn);

        panel.add(buttonPanel, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            frame.dispose();
            new HomePage();
        } else if (ae.getSource() == signIn) {
            frame.dispose();
            new SignIn();
        } else if (ae.getSource() == createbtn) {
            String name = tf1.getText().trim();
            String mobile = tf2.getText().trim();
            String email = tf3.getText().trim();
            String password = new String(pass.getPassword()).trim();

            if (name.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields properly!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "system";
                String dbPassword = "system";

                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(url, username, dbPassword);

                String qry = "INSERT INTO customer_details (name, contact, email, password) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(qry);
                ps.setString(1, name);
                ps.setString(2, mobile);
                ps.setString(3, email);
                ps.setString(4, password);

                int i = ps.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(frame, "Customer Data Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Error in saving data!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}