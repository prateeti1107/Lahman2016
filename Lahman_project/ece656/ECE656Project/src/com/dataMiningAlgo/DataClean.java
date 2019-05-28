package com.dataMiningAlgo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
class DataClean extends JFrame
{

  JLabel l1;
  JButton bt1,btn2;
  
  void frame_2()
	{
	  setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	  setTitle("Data Cleaning");
	  setSize(600, 600);
	     
	  l1 = new JLabel("Data Cleaning");
	  l1.setForeground(Color.blue);
	  l1.setFont(new Font("Serif", Font.BOLD, 50));
	 
	
	     bt1=new JButton("<html><center><h3>Add Primary Key<br/>and<br/>Add Foreign Key<br/>with Data Cleaing</h3></center></html>");
	     btn2 = new JButton("<html><center><h3>Restore Database <br/>to<br/>Initial State");
	     
	
		 l1.setBounds(100, 40, 400, 60);
		 bt1.setBounds(170, 160, 200,100 );
		 btn2.setBounds(170, 290, 200, 60);
	     
	     add(l1);
	     add(bt1);
	     add(btn2);
	     
	    
	     bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					DataCleaning dc=new DataCleaning();
					if(e.getSource() == bt1)
					{
						dc.dataClean();//pk and fk and cleaning
					}
					else if (e.getSource()==btn2)
					{
						dc.dataRestore();
					}
				}});
	    	    
		 setLayout(null);
		 setVisible(true);
	}
	
  
 }