package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OrderMeal implements ActionListener {
    private JFrame frame;
    private JButton btn, back, edit;
    private ButtonGroup bgBreakfast, bgLunch, bgDinner;
    private JLabel headingLabel, emailLabel, l2, l3, l4;
    private JRadioButton rbBreakfastYes, rbBreakfastNo, rbLunchYes, rbLunchNo, rbDinnerYes, rbDinnerNo;
    private JPanel panel;
    private JTextField emailField;

    private Connection con = null;
    private PreparedStatement ps = null, ps1 = null,ps2=null,psPrice,checkPs1,ps4,checkPs;
    private ResultSet rs,rsCheck1;

    public OrderMeal() {
        frame = new JFrame("Ordering");
        frame.setSize(1800, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Heading Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        headingLabel = new JLabel("ORDER MEAL", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headingLabel.setForeground(Color.blue);
        panel.add(headingLabel, gbc);

        // Email Input Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        emailLabel = new JLabel("Enter Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Breakfast Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        l2 = new JLabel("Breakfast:");
        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(l2, gbc);

        gbc.gridx = 1;
        rbBreakfastYes = new JRadioButton("Yes");
        rbBreakfastNo = new JRadioButton("No");
        bgBreakfast = new ButtonGroup();
        bgBreakfast.add(rbBreakfastYes);
        bgBreakfast.add(rbBreakfastNo);
        panel.add(rbBreakfastYes, gbc);

        gbc.gridx = 2;
        panel.add(rbBreakfastNo, gbc);

        // Lunch Selection
        gbc.gridx = 0;
        gbc.gridy = 3;
        l3 = new JLabel("Lunch:");
        l3.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(l3, gbc);

        gbc.gridx = 1;
        rbLunchYes = new JRadioButton("Yes");
        rbLunchNo = new JRadioButton("No");
        bgLunch = new ButtonGroup();
        bgLunch.add(rbLunchYes);
        bgLunch.add(rbLunchNo);
        panel.add(rbLunchYes, gbc);

        gbc.gridx = 2;
        panel.add(rbLunchNo, gbc);

        // Dinner Selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        l4 = new JLabel("Dinner:");
        l4.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(l4, gbc);

        gbc.gridx = 1;
        rbDinnerYes = new JRadioButton("Yes");
        rbDinnerNo = new JRadioButton("No");
        bgDinner = new ButtonGroup();
        bgDinner.add(rbDinnerYes);
        bgDinner.add(rbDinnerNo);
        panel.add(rbDinnerYes, gbc);

        gbc.gridx = 2;
        panel.add(rbDinnerNo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        btn = new JButton("Generate Bill");
        btn.setPreferredSize(new Dimension(180, 30));
        btn.addActionListener(this);
        panel.add(btn, gbc);

        gbc.gridy = 6;
        edit = new JButton("Update");
        edit.setPreferredSize(new Dimension(180, 30));
        edit.addActionListener(this);
        panel.add(edit, gbc);

        gbc.gridy = 7;
        back = new JButton("Back");
        back.setPreferredSize(new Dimension(180, 30));
        back.addActionListener(this);
        panel.add(back, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            frame.dispose();
            new Dashboard();
        } else if (ae.getSource() == edit) {
        	updateMealOrder();
            frame.dispose();
            new Dashboard();
        } else if (ae.getSource() == btn) {
            placeMealOrder();
        }
    }
    
    private void updateMealOrder() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";
            String password = "system";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, username, password);

            String email = emailField.getText().trim();
            String breakfastOrder = rbBreakfastYes.isSelected() ? "Yes" : "No";
            String lunchOrder = rbLunchYes.isSelected() ? "Yes" : "No";
            String dinnerOrder = rbDinnerYes.isSelected() ? "Yes" : "No";

            if (email.isEmpty() || (!rbBreakfastYes.isSelected() && !rbBreakfastNo.isSelected()) ||
                (!rbLunchYes.isSelected() && !rbLunchNo.isSelected()) ||
                (!rbDinnerYes.isSelected() && !rbDinnerNo.isSelected())) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String checkEmailQuery = "SELECT cust_email FROM customer_meals WHERE cust_email = ?";
            checkPs1 = con.prepareStatement(checkEmailQuery);
            checkPs1.setString(1, email);
            rsCheck1 = checkPs1.executeQuery();

            if (!rsCheck1.next()) {
                JOptionPane.showMessageDialog(frame, "Error: Email not found in meal records!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double breakfastPrice = 0, lunchPrice = 0, dinnerPrice = 0, totalPrice = 0;
            String fetchPricesQuery = "SELECT breakfast_price, lunch_price, dinner_price FROM meal_price";
            psPrice = con.prepareStatement(fetchPricesQuery);
            ResultSet rsPrice = psPrice.executeQuery();

            if (rsPrice.next()) {
                breakfastPrice = breakfastOrder.equals("Yes") ? rsPrice.getDouble("breakfast_price") : 0;
                lunchPrice = lunchOrder.equals("Yes") ? rsPrice.getDouble("lunch_price") : 0;
                dinnerPrice = dinnerOrder.equals("Yes") ? rsPrice.getDouble("dinner_price") : 0;
                totalPrice = breakfastPrice + lunchPrice + dinnerPrice;
            }
            
            String updateQuery = "UPDATE customer_meals SET breakfast_order = ?, lunch_order = ?, dinner_order = ?, total_price = ? WHERE cust_email = ?";
            ps4 = con.prepareStatement(updateQuery);
            ps4.setString(1, breakfastOrder);
            ps4.setString(2, lunchOrder);
            ps4.setString(3, dinnerOrder);
            ps4.setDouble(4, totalPrice);
            ps4.setString(5, email);

            int rowsAffected = ps4.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, 
                    "Meal Order Updated Successfully!\n" +
                    "Updated Total Price: ₹" + totalPrice, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Error: Meal Order update failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            rsCheck1.close();
            rsPrice.close();
            ps4.close();
            psPrice.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating meal order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void placeMealOrder() {
        try {
        	String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";
            String password = "system";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, username, password);
            
            String checkEmailQuery = "SELECT email FROM customer_details WHERE email = ?";
            checkPs = con.prepareStatement(checkEmailQuery);
            checkPs.setString(1, emailField.getText());
            ResultSet rsCheck = checkPs.executeQuery();

            if (!rsCheck.next()) {
                JOptionPane.showMessageDialog(frame, "Error: Email not found in customer records!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
           String check_EmailQuery = "SELECT cust_email FROM customer_meals WHERE cust_email = ?";
            PreparedStatement check_Ps = con.prepareStatement(check_EmailQuery);
            check_Ps.setString(1, emailField.getText());
            ResultSet rs_Check = check_Ps.executeQuery();

            if (rs_Check.next()) { 
                JOptionPane.showMessageDialog(frame, "Error: Email already exists in customer meals!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
 
            String insertQuery = "INSERT INTO customer_meals (cust_email, breakfast_order, lunch_order, dinner_order) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(insertQuery);

            String email = emailField.getText();
            String breakfastOrder = rbBreakfastYes.isSelected() ? "Yes" : "No";
            String lunchOrder = rbLunchYes.isSelected() ? "Yes" : "No";
            String dinnerOrder = rbDinnerYes.isSelected() ? "Yes" : "No";
            
            ps.setString(1, email);
            ps.setString(2, breakfastOrder);
            ps.setString(3, lunchOrder);
            ps.setString(4, dinnerOrder);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Meal Order Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            double breakfastPrice = 0, lunchPrice = 0, dinnerPrice = 0, totalPrice = 0;
            String fetchPricesQuery = "SELECT breakfast_price, lunch_price, dinner_price FROM meal_price";
            ps1 = con.prepareStatement(fetchPricesQuery);
            rs = ps1.executeQuery();
                
            if (rs.next()) {
                breakfastPrice = breakfastOrder.equals("Yes") ? rs.getDouble("breakfast_price") : 0;
                lunchPrice = lunchOrder.equals("Yes") ? rs.getDouble("lunch_price") : 0;
                dinnerPrice = dinnerOrder.equals("Yes") ? rs.getDouble("dinner_price") : 0;
                totalPrice = breakfastPrice + lunchPrice + dinnerPrice;
                
            JOptionPane.showMessageDialog(frame,
                "Customer Email: " + email + "\n" +
                "Breakfast Ordered: " + breakfastOrder + "\n" +
                "Lunch Ordered: " + lunchOrder + "\n" +
                "Dinner Ordered: " + dinnerOrder + "\n" +
                "Breakfast Price: ₹" + breakfastPrice + "\n" +
                "Lunch Price: ₹" + lunchPrice + "\n" +
                "Dinner Price: ₹" + dinnerPrice + "\n" +
                "Total Meal Bill: ₹" + totalPrice);
            
            String updateBill = "UPDATE customer_meals SET total_price = ? WHERE cust_email = ?";
            ps2 = con.prepareStatement(updateBill);
            ps2.setDouble(1, totalPrice);
            ps2.setString(2, email); // Ensure 'email' contains the correct customer email
            int rowsAffected = ps2.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Total price updated successfully for email: " + email);
            } else {
                System.out.println("No record found for email: " + email);
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if(ps1!=null)ps1.close();
            if(ps2!=null)ps2.close();
            if (con != null) con.close();
        } catch (SQLException s) {
            System.out.println(s);
            }
        }
    }

    public static void main(String[] args) {
        new OrderMeal();
    }
}