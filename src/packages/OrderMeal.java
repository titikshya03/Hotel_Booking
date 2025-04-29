package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderMeal implements ActionListener {
	JFrame frame;
	JButton btn;
	JLabel l1, l2,l3,l4;
	JRadioButton rbYes, rbNo;
	Font f;
	
	public OrderMeal() {
		frame=new JFrame("Ordering");
		frame.setSize(400,400);
		frame.setLocation(300, 100);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		l1 = new JLabel("Ordering meals");
		f=new Font("Arial",Font.BOLD,18);
		l1.setFont(f);
		l1.setBounds(10, 50, 200, 20);
		l1.setForeground(Color.RED);
		frame.add(l1);
		
		l2 = new JLabel("Breakfast");
		l2.setBounds(10, 80, 200, 30);
		frame.add(l2);
		
		rbYes = new JRadioButton("Yes");
		rbYes.setBounds(110, 80, 70, 30);
		frame.add(rbYes);
		
		rbNo = new JRadioButton("No");
		rbNo.setBounds(180, 80, 90, 30);
		frame.add(rbNo);
		
		l3 = new JLabel("Lunch");
		l3.setBounds(10, 110, 200, 30);
		frame.add(l3);
		
		rbYes = new JRadioButton("Yes");
		rbYes.setBounds(110, 110, 70, 30);
		frame.add(rbYes);
		
		rbNo = new JRadioButton("No");
		rbNo.setBounds(180, 110, 90, 30);
		frame.add(rbNo);
		
		l4 = new JLabel("Dinner");
		l4.setBounds(10, 140, 200, 30);
		frame.add(l4);
		
		rbYes = new JRadioButton("Yes");
		rbYes.setBounds(110, 140, 70, 30);
		frame.add(rbYes);
		
		rbNo = new JRadioButton("No");
		rbNo.setBounds(180, 140, 90, 30);
		frame.add(rbNo);
		
		btn = new JButton("Generate Bill");
		btn.setBounds(50, 190, 170, 30);
		btn.addActionListener(this);
		frame.add(btn);
		
		frame.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
	}
	
	public static void main(String[] args) {
		new OrderMeal();
	}

}