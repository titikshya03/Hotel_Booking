package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Dashboard implements ActionListener {
    JFrame frame;
    JButton btn1, btn2, logOut,btn3;
    JLabel label, welcomeLabel;
    JPanel panel;
    
    Connection con;
    //PreparedStatement ps;

    public Dashboard() {
        frame = new JFrame("Dashboard");
        frame.setSize(1800, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        label = new JLabel("Dashboard", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label, gbc);

        welcomeLabel = new JLabel("Welcome to Your Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 23));
        welcomeLabel.setForeground(Color.RED);
        gbc.gridy = 1;
        panel.add(welcomeLabel, gbc);

        btn1 = new JButton("Order Meal");
        btn1.setPreferredSize(new Dimension(180, 40));
        btn1.setFont(new Font("Calibri", Font.BOLD, 16));
        btn1.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(btn1, gbc);

        btn2 = new JButton("Book Room");
        btn2.setPreferredSize(new Dimension(180, 40));
        btn2.setFont(new Font("Calibri", Font.BOLD, 16));
        btn2.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btn2, gbc);
        
        gbc.gridy = 4;
        btn3 = new JButton("Generate Final Bill");
        btn3.setPreferredSize(new Dimension(180, 40));
        btn3.setFont(new Font("Calibri", Font.BOLD, 16));
        btn3.addActionListener(this);
        panel.add(btn3, gbc);


        logOut = new JButton("Log - Out");
        logOut.setPreferredSize(new Dimension(180, 40));
        logOut.setFont(new Font("Calibri", Font.BOLD, 16));
        logOut.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(logOut, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == logOut) {
            frame.dispose();
            new HomePage();
        } else if (ae.getSource() == btn1) {
            frame.dispose();
            new OrderMeal();
        } else if (ae.getSource() == btn2) {
            frame.dispose();
            new BookRoom();
        }else if(ae.getSource()==btn3) {
            generateFinalBill();
        }
    }
    private void generateFinalBill() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";
            String password = "system";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, username, password);
            String email = JOptionPane.showInputDialog(frame, "Enter your Email:", "Retrieve Final Bill", JOptionPane.PLAIN_MESSAGE);

            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String bookingQuery = "SELECT total_price FROM customer_bookings WHERE customer_email = ?";
            PreparedStatement psBooking = con.prepareStatement(bookingQuery);
            psBooking.setString(1, email);
            ResultSet rsBooking = psBooking.executeQuery();

            double bookingTotal = 0;
            if (rsBooking.next()) {
                bookingTotal = rsBooking.getDouble("total_price");
            }

            String mealQuery = "SELECT total_price FROM customer_meals WHERE cust_email = ?";
            PreparedStatement psMeal = con.prepareStatement(mealQuery);
            psMeal.setString(1, email);
            ResultSet rsMeal = psMeal.executeQuery();
            
            double mealTotal = 0;
            if (rsMeal.next()) {
                mealTotal = rsMeal.getDouble("total_price");
            }

            double finalBill = bookingTotal + mealTotal;

            JOptionPane.showMessageDialog(frame,
                "Customer Email: " + email + "\n" +
                "Room Booking Bill: ₹" + bookingTotal + "\n" +
                "Meal Order Bill: ₹" + mealTotal + "\n" +
                "Final Bill Amount: ₹" + finalBill,
                "Final Bill", JOptionPane.INFORMATION_MESSAGE);
            	
            rsBooking.close();
            rsMeal.close();
            psBooking.close();
            psMeal.close();
            con.close();

        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error retrieving final bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new Dashboard();
    }
}