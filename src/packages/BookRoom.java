package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookRoom implements ActionListener {
    JFrame frame;
    JButton btn, back, cancel;
    JLabel headingLabel, emailLabel, room, bed, cleaning, noOfRooms;
    JTextField emailField, tf1;
    JPanel panel;
    JRadioButton acButton, nonAcButton, single, doubleBed, yesButton, noButton;
    ButtonGroup roomGroup, bedGroup, cleaningGroup;

    Connection con = null;
    PreparedStatement ps = null, ps1 = null,ps2=null,ps3=null;
    ResultSet rs;

    public BookRoom() {
        frame = new JFrame("Book Room");
        frame.setSize(1800, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(193, 255, 193));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        headingLabel = new JLabel("BOOK ROOM", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headingLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(headingLabel, gbc);

        // Email Input Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        emailLabel = new JLabel("Enter Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Room selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        room = new JLabel("Select Room:");
        room.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(room, gbc);

        acButton = new JRadioButton("AC");
        acButton.setBackground(new Color(193, 255, 193));
        nonAcButton = new JRadioButton("Non-AC");
        nonAcButton.setBackground(new Color(193, 255, 193));

        roomGroup = new ButtonGroup();
        roomGroup.add(acButton);
        roomGroup.add(nonAcButton);

        gbc.gridx = 1;
        panel.add(acButton, gbc);
        gbc.gridx = 2;
        panel.add(nonAcButton, gbc);

        // Bed selection
        gbc.gridx = 0;
        gbc.gridy = 3;
        bed = new JLabel("Select Bed Type:");
        bed.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(bed, gbc);

        single = new JRadioButton("Single");
        single.setBackground(new Color(193, 255, 193));
        doubleBed = new JRadioButton("Double");
        doubleBed.setBackground(new Color(193, 255, 193));

        bedGroup = new ButtonGroup();
        bedGroup.add(single);
        bedGroup.add(doubleBed);

        gbc.gridx = 1;
        panel.add(single, gbc);
        gbc.gridx = 2;
        panel.add(doubleBed, gbc);

        // Cleaning Service selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        cleaning = new JLabel("Cleaning Service:");
        cleaning.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(cleaning, gbc);

        yesButton = new JRadioButton("Yes");
        yesButton.setBackground(new Color(193, 255, 193));
        noButton = new JRadioButton("No");
        noButton.setBackground(new Color(193, 255, 193));

        cleaningGroup = new ButtonGroup();
        cleaningGroup.add(yesButton);
        cleaningGroup.add(noButton);

        gbc.gridx = 1;
        panel.add(yesButton, gbc);
        gbc.gridx = 2;
        panel.add(noButton, gbc);

        // Number of Rooms Input
        gbc.gridx = 0;
        gbc.gridy = 5;
        noOfRooms = new JLabel("No Of Rooms:");
        noOfRooms.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(noOfRooms, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        tf1 = new JTextField(5);
        panel.add(tf1, gbc);

        // Buttons Placement
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;

        btn = new JButton("Generate Bill");
        btn.setPreferredSize(new Dimension(200, 40));
        btn.addActionListener(this);
        panel.add(btn, gbc);

        gbc.gridy = 7;
        cancel = new JButton("Cancel Booking");
        cancel.setPreferredSize(new Dimension(200, 40));
        cancel.addActionListener(this);
        panel.add(cancel, gbc);

        gbc.gridy = 8;
        back = new JButton("Back");
        back.setPreferredSize(new Dimension(200, 40));
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
        } else if (ae.getSource() == cancel) {
            int response = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to cancel your Booking?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
               try {
            	   String url = "jdbc:oracle:thin:@localhost:1521:xe";
                   String username = "system";
                   String pass = "system";

                   Class.forName("oracle.jdbc.driver.OracleDriver");
                   con = DriverManager.getConnection(url, username, pass);
	                String q="DELETE FROM customer_bookings WHERE customer_email=?";
	                ps3=con.prepareStatement(q);
	                String t=emailField.getText();
	                
	                if(t.isEmpty()) {
	                	 JOptionPane.showMessageDialog(frame, "Please enter email field !", "Error", JOptionPane.ERROR_MESSAGE);
	                     return;
	                }
	                ps3.setString(1, t);
	                int j=ps3.executeUpdate();
	                if(j>0) {
		                JOptionPane.showMessageDialog(frame, "Booking Cancelled Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		                frame.dispose();
		                new Dashboard();
	                }
               } catch (ClassNotFoundException| SQLException e) {
                   JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
               }
            }
        } else if (ae.getSource() == btn) {
            try {
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "system";
                String pass = "system";

                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(url, username, pass);
                
                String checkEmailQuery = "SELECT email FROM customer_details WHERE email = ?";
                PreparedStatement checkPs = con.prepareStatement(checkEmailQuery);
                checkPs.setString(1, emailField.getText());
                ResultSet rsCheck = checkPs.executeQuery();

                if (!rsCheck.next()) {
                    JOptionPane.showMessageDialog(frame, "Error: Email not found in customer records!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String check_EmailQuery = "SELECT customer_email FROM customer_bookings WHERE customer_email = ?";
                PreparedStatement check_Ps = con.prepareStatement(check_EmailQuery);
                check_Ps.setString(1, emailField.getText());
                ResultSet rs_Check = check_Ps.executeQuery();

                if (rs_Check.next()) { 
                    JOptionPane.showMessageDialog(frame, "Error: Email already exists in customer bookings!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String qry = "INSERT INTO customer_bookings(customer_email,room_type,bed_type,cleaning,room_count) VALUES(?,?,?,?,?)";
                ps = con.prepareStatement(qry);

                String cEmail = emailField.getText();
                String roomType = acButton.isSelected() ? "AC" : "Non-AC";
                String bedType = single.isSelected() ? "Single" : "Double";
                String cleaningService = yesButton.isSelected() ? "Yes" : "No";
                int roomCount = Integer.parseInt(tf1.getText());

                ps.setString(1, cEmail);
                ps.setString(2, roomType);
                ps.setString(3, bedType);
                ps.setString(4, cleaningService);
                ps.setInt(5, roomCount);
                int i=ps.executeUpdate();
		
		if(i>0) {
    				System.out.println(i+" row inserted succesfully");
    			}
    			
	            if ((acButton.isSelected() || nonAcButton.isSelected()) && (single.isSelected() || doubleBed.isSelected() && !tf1.getText().isEmpty())) {
	                JOptionPane.showMessageDialog(frame, "Room Booking Details Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(frame, "Filling Room Details is Mandatory!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            
	            String fetchquery="SELECT price_per_night, cleaning_price FROM room_details WHERE room_type=?  AND bed_type = ?";
	            ps1=con.prepareStatement(fetchquery);
	            ps1.setString(1, roomType);
                ps1.setString(2, bedType);
                //ps1.setString(3, cleaningService);
                rs = ps1.executeQuery();

		if (rs.next()) {
	                double pricePerNight = rs.getDouble("price_per_night");
	                double cleaningPrice = rs.getDouble("cleaning_price");
	                
	                double totalBill = (pricePerNight + cleaningPrice) * roomCount;

	               
	                JOptionPane.showMessageDialog(frame,
	                    "Room Type: " + roomType + "\n" +
	                    "Bed Type: " + bedType + "\n" +
	                    "Cleaning Service: " + cleaningService + "\n" +
	                    "No. of Rooms: " + roomCount + "\n" +
	                    "Total Bill: ₹" + totalBill);
	                
	                String updateBill = "UPDATE customer_bookings SET total_price = ? WHERE customer_email = ?";
	                ps2 = con.prepareStatement(updateBill);
	                ps2.setDouble(1, totalBill);
	                ps2.setString(2, cEmail);  // Ensure 'email' contains the correct customer email
	                int rowsAffected = ps2.executeUpdate();

	                if (rowsAffected > 0) {
	                    System.out.println("Total price updated successfully for email: " + cEmail);
	                } else {
	                    System.out.println("No record found for email: " + cEmail);
	                }
	            } else {
                JOptionPane.showMessageDialog(frame, "Room Booking Details Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            }
		
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if(ps1!=null)ps1.close();
                    if(ps2!=null)ps2.close();
                    if(ps3!=null)ps3.close();
                    if (con != null) con.close();
                } catch (SQLException s) {
                    System.out.println(s);
                }
            }
        }
    }

    public static void main(String[] args) {
        new BookRoom();
    }
}