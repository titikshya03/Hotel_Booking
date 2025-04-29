package packages;
       
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpPage implements ActionListener {
	JFrame frame;
	JTextField tf1,tf2,tf3;
	JPasswordField pass;
	JButton btn, back;
	JLabel l1,l2,l3,l4,l5,label;
	JPanel panel;
	
	public SignUpPage() {
		frame = new JFrame("Customer Sign Up Page");
		frame.setSize(400,400);
		frame.setLocation(700,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 250, 205));
		panel.setLayout(null);
		frame.add(panel);
		
		l1 = new JLabel("Customer Sign-Up");
		l1.setForeground(Color.RED);
		l1.setFont(new Font("Calibri",Font.BOLD,20));
		l1.setBounds(120,10,250,20);
		panel.add(l1);
		
		l2 = new JLabel("Enter Name: ");
		l2.setBounds(10,40,150,20);
		panel.add(l2);
		
		tf1 = new JTextField();
		tf1.setBounds(10,70,200,20);
		panel.add(tf1);
		
		l3 = new JLabel("Contact No: ");
		l3.setBounds(10,100,150,20);
		panel.add(l3);
		
		tf2 = new JTextField();
		tf2.setBounds(10,130,200,20);
		panel.add(tf2);
		
		l4 = new JLabel("E-mail Id: ");
		l4.setBounds(10,160,150,20);
		panel.add(l4);
		
		tf3 = new JTextField();
		tf3.setBounds(10,190,200,20);
		panel.add(tf3);
		
		l5 = new JLabel("Password: ");
		l5.setBounds(10,220,200,20);
		panel.add(l5);
		
		pass = new JPasswordField();
		pass.setBounds(10,250,200,20);
		panel.add(pass);
		
		label = new JLabel();
		label.setBounds(10,280,200,20);
		panel.add(label);
		
		btn = new JButton("Create");
		btn.setBounds(30,310,80,20);
		btn.addActionListener(this);
		panel.add(btn);
		
		back = new JButton("Back");
		back.setBounds(120,310,80,20);
		back.addActionListener(this);
		panel.add(back);
		
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		int count = 0;
		if(ae.getSource() == back) {
			frame.dispose();
			}
		else if(ae.getSource() == btn) {
			
			String name = tf1.getText();
			if(name.isEmpty()) {
				tf1.setText("Please Enter name");
				tf1.setForeground(Color.RED);
				count++;
			}
			
			String mobile = tf2.getText();
			if(mobile.isEmpty()) {
				tf2.setText("Please Enter Contact No" );
				tf2.setForeground(Color.RED);
				count++;
			}
			
			String email = tf3.getText();
			if(email.isEmpty()) {
				tf3.setText("Please Enter E-mail");
				tf3.setForeground(Color.RED);
				count++;
			}
			
			String password = new String(pass.getPassword());
			if(password.isEmpty()) {
				label.setText("Please Enter Password" );
				label.setForeground(Color.RED);
				count++;
			} else {
				label.setText(password);
			}
			
			if(count == 0 ) {
				JOptionPane.showMessageDialog(frame, "Customer Data Added Successfully!");
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				pass.setText("");
			} else {
	            JOptionPane.showMessageDialog(frame, "Please fill all fields properly!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main(String args[]){
		new SignUpPage();
	}
}
