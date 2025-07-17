package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignIn implements ActionListener {
    JFrame frame;
    JButton logIn, back;
    JLabel l1, l2, l;
    JTextField tf1;
    JPasswordField pf2;  // Changed from JTextField to JPasswordField
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public SignIn() {
        frame = new JFrame("Sign in");
        frame.setSize(1800, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 204, 153));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        l = new JLabel("Customer Sign In", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 28));
        l.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(l, gbc);

        l1 = new JLabel("Email:");
        l1.setFont(new Font("Calibri", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(l1, gbc);

        tf1 = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(tf1, gbc);

        l2 = new JLabel("Password:");
        l2.setFont(new Font("Calibri", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(l2, gbc);

        pf2 = new JPasswordField(15);  // Changed from JTextField to JPasswordField
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(pf2, gbc);

        logIn = new JButton("Log In");
        logIn.setFont(new Font("Calibri", Font.BOLD, 16));
        logIn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(logIn, gbc);

        back = new JButton("Back");
        back.setFont(new Font("Calibri", Font.BOLD, 16));
        back.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(back, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String email = tf1.getText();
        String inputpassword = new String(pf2.getPassword());  // Extract password securely
        
        if (ae.getSource() == back) {
            frame.dispose();
            new HomePage();
        } else if (ae.getSource() == logIn) {
            try {
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "system";
                String pass = "system";
                
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(url, username, pass);
                String qry = "SELECT password FROM customer_details WHERE email=?";
                ps = con.prepareStatement(qry);
                ps.setString(1, email);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    String storedpassword = rs.getString("password");
                    if (storedpassword.equalsIgnoreCase(inputpassword)) {
                        JOptionPane.showMessageDialog(frame, "Login successful");
                        frame.dispose();
                        new Dashboard();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Email or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found");
                }
            } catch (ClassNotFoundException c) {
                System.out.println(c);
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                try {
                    rs.close();
                    ps.close();
                    con.close();
                } catch (SQLException se) {
                    System.out.println(se);
                }
            }
        }
    }

    public static void main(String[] args) {
        new SignIn();
    }
}