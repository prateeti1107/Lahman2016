package com.dataMiningAlgo;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

class DecisionTreeGUI extends JFrame
{
  
  JLabel lb1,lb2;
  JButton bt1;
  
  void frame_2()
	{
	  	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	    setTitle("Decision Tree");
	    setSize(600, 400);
	     
	     JComboBox<String> comboBox = new JComboBox<String>();
	     comboBox.addItem( "HallofFame-Master" ); 

	     JComboBox<String> comboBox1 = new JComboBox<String>();
	     comboBox1.addItem( "inducted" );
	 
	     lb1 = new JLabel("Select Table");
	     lb2 = new JLabel("Select label Attribute");
	     bt1=new JButton("<html><center> validate <br/>and<br/> Predict</center></html>");
	     
	     lb1.setBounds(100, 100, 150, 30);
	     comboBox.setBounds(230, 100, 200, 30);
	        
	     lb2.setBounds(100, 200, 150, 30);
	     comboBox1.setBounds(230, 200, 200, 30);
	     
	     bt1.setBounds(150, 250, 150, 60);
	     
	     add(lb1);
	     add(comboBox);
	     add(lb2);
	     add(comboBox1);
	     add(bt1);
	     
	    
	     bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					ArrayList<Info> finalF2 = new ArrayList<>();
					DecisionTree dt = new DecisionTree();
					Apriori apriori = new Apriori();
					try {
						String bestsplit=dt.calculateTableEntropy("new", "inducted", "@name");
						//OptionPane.showMessageDialog(null, bestsplit, "Best Attribute to split", JOptionPane.INFORMATION_MESSAGE);
				    
						finalF2=dt.selectAttribute();
						
					} catch (InstantiationException | IllegalAccessException  | SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					JFrame f; 
					JTable j; 
					JLabel l1;
					l1 = new JLabel("ECE 656 Project");
					f = new JFrame();
					j = new JTable(); 
			        j.setModel(new DTTableModel(finalF2));
			        JTableHeader header = j.getTableHeader();
			        
					header.setBackground(Color.pink);
					header.setFont(new Font("Dialog", Font.BOLD, 18));
			        j.setBounds(30, 40, 200, 300); 
			        String header1[] = {"Columns","Entropy","Information Gain"};
			        for(int i=0;i<j.getColumnCount();i++)
			        {
			        TableColumn column1 = j.getTableHeader().getColumnModel().getColumn(i);
			          
			        column1.setHeaderValue(header1[i]);
			        
			        } 
			        // adding it to JScrollPane 
			        JScrollPane sp = new JScrollPane(j); 
			        f.add(sp); 
			        //l1.setBounds(100, 40, 400, 60);
			        //f.add(l1);
			        // Frame Size 
			        f.setSize(500, 200); 
			        // Frame Visible = true 
			        f.setVisible(true);	
				      
				}});
	    	    
		 setLayout(null);
		 setVisible(true);
	}
	
  
 }