//this is home page
//abcd
package packages;
//Excuse me!!!
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage implements ActionListener {
	JFrame homeframe;
	JLabel l1;
	JButton bt1,bt2,bt3,bt4;
	
	HomePage(){
		homeframe=new JFrame("Hotel room booking system");
		homeframe.setSize(400,400);
		homeframe.setLocation(200,200);
		homeframe.setLayout(null);
		homeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		l1=new JLabel("Hotel Room Booking ",JLabel.CENTER);
		l1.setBounds(50,20,200,30);
		l1.setForeground(Color.RED);
		homeframe.add(l1);
		
		bt1 = new JButton("Create user");
		bt1.setBounds(50, 70, 200, 30);
		bt1.addActionListener(this);
		homeframe.add(bt1); 
		
//		bt2 = new JButton("Existing user");
//		bt2.setBounds(50, 120, 200, 30);
//		bt2.addActionListener(this);
//		homeframe.add(bt2);
		
		bt3 = new JButton("Book Room");
		bt3.setBounds(50, 170, 200, 30);
		bt3.addActionListener(this);
		homeframe.add(bt3);
		
		bt4 = new JButton("Order meals");
		bt4.setBounds(50, 220, 200, 30);
		bt4.addActionListener(this);
		homeframe.add(bt4);
		
	homeframe.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		try{
			if(ae.getSource()==bt1) {
				new SignUpPage();
			}else if(ae.getSource()==bt3) {
				new BookRoom();
			}else if(ae.getSource()==bt4) {
				new OrderMeal();
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		new HomePage();
	}

}