package com.dataMiningAlgo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


@SuppressWarnings("serial")
public class Main_Frame extends JFrame implements ActionListener
{
	 JLabel l1;
	 JButton btn1,btn2,btn3;
	
	void frame_1()
	{
		JFrame frame = new JFrame("ECE 656 Project");
		  l1 = new JLabel("ECE 656 Project");
		  l1.setForeground(Color.blue);
		  l1.setFont(new Font("Serif", Font.BOLD, 50));
		 
		  btn1 = new JButton("Association rule");
		  btn2 = new JButton("Decision Tree");
		  btn3 = new JButton("Data Cleaning");
		 
		  l1.setBounds(100, 40, 400, 60);
		 
		  btn1.setBounds(170, 160, 150, 30);
		  btn2.setBounds(170, 200, 150, 30);
		  btn3.setBounds(170, 240, 150, 30);
		  btn1.addActionListener(this);
		  btn2.addActionListener(this);
		  btn3.addActionListener(this);
		  
		  frame.add(l1);
		  frame.add(btn1);
		  frame.add(btn2);
		  frame.add(btn3);
		 
		  frame.setSize(600, 400);
		  frame.setLayout(null);
		  frame.setVisible(true);
		
	}
	 
	public static void main(String[] args) {
	
		Main_Frame mf=new Main_Frame();
		mf.frame_1();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	
		if(e.getSource() == btn1)
		{
			Welcome wc=new Welcome();
			wc.frame_2();
			System.out.println("dd");
		}
		else if (e.getSource()==btn2)
		{
			DecisionTreeGUI dt=new DecisionTreeGUI();
			dt.frame_2();
		}
		else if (e.getSource()==btn3)
		{
			DataClean dc=new DataClean();
			dc.frame_2();
			System.out.println("rr");
		}
	}
}