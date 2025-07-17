package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage implements ActionListener {
    JFrame homeframe;
    JLabel headingLabel, welcomeLabel, featureLabel1, featureLabel2, featureLabel3, featureLabel4;
    JPanel panel;
    JButton btn1, btn2, back;

    public HomePage() {
        homeframe = new JFrame("Evergreen Haven - Room Booking System");
        homeframe.setSize(1800, 1000);
        homeframe.setLocationRelativeTo(null); 
        homeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        headingLabel = new JLabel("HOTEL ROOM BOOKING", JLabel.CENTER);
        headingLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(headingLabel, gbc);

        welcomeLabel = new JLabel("Welcome to Evergreen Haven Booking System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 1;
        panel.add(welcomeLabel, gbc);

        featureLabel1 = new JLabel("✔ Quick & Easy Room Booking");
        featureLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 2;
        panel.add(featureLabel1, gbc);

        featureLabel2 = new JLabel("✔ Delicious Meal Orders");
        featureLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 3;
        panel.add(featureLabel2, gbc);

        featureLabel3 = new JLabel("✔ Seamless Hospitality Services");
        featureLabel3.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 4;
        panel.add(featureLabel3, gbc);

        featureLabel4 = new JLabel("✔ 24/7 Customer Support");
        featureLabel4.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 5;
        panel.add(featureLabel4, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        btn1 = new JButton("Create Account");
        btn1.setPreferredSize(new Dimension(150, 40));
        btn1.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(btn1, gbc);

        btn2 = new JButton("Log - In");
        btn2.setPreferredSize(new Dimension(150, 40));
        btn2.addActionListener(this);
        gbc.gridx = 1;
        panel.add(btn2, gbc);

        back = new JButton("Back");
        back.setPreferredSize(new Dimension(150, 40));
        back.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(back, gbc);

        homeframe.add(panel);
        homeframe.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn1) {
        	homeframe.dispose();
            new SignUpPage();
        } else if (ae.getSource() == btn2) {
        	homeframe.dispose();
            new SignIn();
        } else if (ae.getSource() == back) {
            homeframe.dispose();
        }
    }

    public static void main(String[] args) {
        new HomePage();
    }
}