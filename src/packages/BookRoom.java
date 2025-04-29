package packages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookRoom implements ActionListener{
	JFrame frame;
	JLabel l1,room,bed,cleaning;
	JPanel Panel,panel1,panel2;
	JRadioButton acButton,NonacButton,Single,Double,YesButton,NoButton;
	ButtonGroup roomGroup,bedGroup,cleaningGroup;
	
	public BookRoom(){
		
		frame = new JFrame("Book room page");
        frame.setSize(500,600);
        frame.setLocation(300,40);
        frame.setLayout(null);//new GridLayout(9, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        l1=new JLabel("Book Room");
        l1.setBounds(20,10,200,30);
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        l1.setForeground(Color.RED);
        frame.add(l1);
        
        room=new JLabel("Select room");
        room.setBounds(20,60,100,30);
        frame.add(room);
		
		Panel = new JPanel();
        Panel.setBounds(140,60,200,30);
        acButton = new JRadioButton("AC");
        NonacButton = new JRadioButton("NON-AC");
        roomGroup = new ButtonGroup();
        roomGroup.add(acButton);
        roomGroup.add(NonacButton);
        Panel.add(acButton);
        Panel.add(NonacButton);
        frame.add(Panel);
        
        bed=new JLabel("Select bed type");
        bed.setBounds(20,100,100,30);
        frame.add(bed);
        
        panel1= new JPanel();
        panel1.setBounds(140,100,200,30);
        Single = new JRadioButton("Single");
        Double = new JRadioButton("Double");
        bedGroup = new ButtonGroup();
        bedGroup.add(Single);
        bedGroup.add(Double);
        panel1.add(Single);
        panel1.add(Double);
        frame.add(panel1);
        
        cleaning=new JLabel("Cleaning service");
        cleaning.setBounds(20,140,100,30);
        frame.add(cleaning);
        
        panel2 = new JPanel();
        panel2.setBounds(140,140,200,30);
        YesButton = new JRadioButton("Yes");
        NoButton = new JRadioButton("No");
        cleaningGroup = new ButtonGroup();
        cleaningGroup.add(YesButton);
        cleaningGroup.add(NoButton);
        panel2.add(YesButton);
        panel2.add(NoButton);
        frame.add(panel2);
        
        frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
	}

	public static void main(String[] args) {
		new BookRoom();
	}
}
