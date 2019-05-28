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
class Welcome extends JFrame
{
  
  JLabel lb1,lb2;
  JButton bt1;
  
  void frame_2()
	{
	  setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	    setTitle("Association Rules");
	    setSize(600, 400);
	     
	     JComboBox<String> comboBox = new JComboBox<String>();
	     comboBox.addItem( "1995" );
	     comboBox.addItem( "1996" );
	     comboBox.addItem( "1997" );
	     comboBox.addItem( "1998" );
	     comboBox.addItem( "1999" );
	     comboBox.addItem( "2000" );

	     JComboBox<String> comboBox1 = new JComboBox<String>();
	     comboBox1.addItem( "Division Winner" );
	     comboBox1.addItem( "Wild-card Winner" );
	     comboBox1.addItem( "League Champion" );
	     comboBox1.addItem( "World Series Winner" );
	 
	     lb1 = new JLabel("Select Year");
	     lb2 = new JLabel("Select competition");
	     bt1=new JButton("Submit");
	     
	     lb1.setBounds(100, 100, 150, 30);
	     comboBox.setBounds(230, 100, 100, 30);
	        
	     lb2.setBounds(100, 200, 150, 30);
	     comboBox1.setBounds(230, 200, 100, 30);
	     
	     bt1.setBounds(150, 250, 150, 30);
	     
	     add(lb1);
	     add(comboBox);
	     add(lb2);
	     add(comboBox1);
	     add(bt1);
	     
	    
	     bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					String year = String.valueOf(comboBox.getSelectedItem());
					String comp = String.valueOf(comboBox1.getSelectedItem());
					if (comp=="Division Winner")
						comp="DivWin";
					else if(comp=="Wild-card Winner")
						comp="WcWin";
					else if(comp=="League Champion")
						comp="LgWin";
					else
						comp="WsWin";
					
					ArrayList<F2> finalF2 = new ArrayList<>();
					Apriori apriori = new Apriori();
					try {
						apriori.createItemSet(Integer.parseInt(year));
						finalF2=apriori.selectCompetetion(comp);
					} catch (InstantiationException | IllegalAccessException  | SQLException e) {
						e.printStackTrace();
					}
					
					
					System.out.println("ss"+year+comp);
					
					JFrame f; 
					JTable j; 
					f = new JFrame();
					j = new JTable(); 
			        j.setModel(new TableModel(finalF2));
			        
			        JTableHeader header = j.getTableHeader();
					header.setBackground(Color.pink);
					header.setFont(new Font("Dialog", Font.BOLD, 18));
			        j.setBounds(30, 40, 200, 300); 
			  
			        String header1[] = {"ITEM_1","ITEM_2","support","confidence","cnt"};
			        for(int i=0;i<j.getColumnCount();i++)
			        {
			        TableColumn column1 = j.getTableHeader().getColumnModel().getColumn(i);
			          
			        column1.setHeaderValue(header1[i]);
			        
			        } 
			        // adding it to JScrollPane 
			        JScrollPane sp = new JScrollPane(j); 
			        f.add(sp); 
			        // Frame Size 
			        f.setSize(500, 200); 
			        // Frame Visible = true 
			        f.setVisible(true);
					
					
				      
				}});
	    	    
		 setLayout(null);
		 setVisible(true);
	}
	
  
 }