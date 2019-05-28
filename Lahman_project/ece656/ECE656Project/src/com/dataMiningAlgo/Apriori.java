package com.dataMiningAlgo;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dataMiningAlgo.jdbc.JDBCConnection;

public class Apriori {
	JDBCConnection con = new JDBCConnection();
	Connection conn;
	CallableStatement cstmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	ArrayList<F2> finalF2 = new ArrayList<>();
	//inserting data into itemset according to year range
	public void createItemSet(int year) throws InstantiationException, IllegalAccessException {
		
		try {
			conn = con.getConnection();
			System.out.println(conn);
			String query = "{CALL insert_into_itemset(?)}";
			cstmt = conn.prepareCall(query);
			cstmt.setInt(1, year);
			int result = cstmt.executeUpdate();
			System.out.println("***" + result);
			
			//executing Apriori for the selected dataset by user
			createC1();
			createF1();
			createAprioriVertical();
			createFrequentItemset2();
			createC2();
			createF2();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//creating c1 table
	public void createC1() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL proc_create_c1}";
		cstmt = conn.prepareCall(query);
		int result = cstmt.executeUpdate();
		System.out.println("***" + result);
	}
	
	//create F1 table
	public void createF1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL proc_create_f1}";
		cstmt = conn.prepareCall(query);
		int result = cstmt.executeUpdate();
		System.out.println("***" + result);
	}
	
	//create Apriori Vertical table
	public void createAprioriVertical() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL proc_create_apriori_vertical}";
		cstmt = conn.prepareCall(query);
		int result = cstmt.executeUpdate();
		System.out.println("***" + result);
	}
		
	//create table Frequent_ItemSet_2
	public void createFrequentItemset2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL proc_create_freq_item_2}";
		cstmt = conn.prepareCall(query);
		int result = cstmt.executeUpdate();
		System.out.println("***" + result);
	}
	
	//Create table C2
	public void createC2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL proc_create_c2}";
		cstmt = conn.prepareCall(query);
		int result = cstmt.executeUpdate();
		System.out.println("***" + result);
	}
	
	//Create table F2
	public void createF2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL proc_create_f2}";
		cstmt = conn.prepareCall(query);
		int result = cstmt.executeUpdate();
		System.out.println("***" + result);
	}
	
	public ArrayList<F2> selectCompetetion(String comp) throws SQLException {
		System.out.println("Competetion " + comp);
		String sql = "select * from F2 where Item_1 = ? or Item_2 = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, comp);
		pstmt.setString(2, comp);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			F2 f2 = new F2(rs.getString("ITEM_1"), rs.getString("ITEM_2"), rs.getFloat("Support"), rs.getFloat("Confidence"), rs.getFloat("cnt"));
	        finalF2.add(f2);
		}
		
		for(int i =0; i<finalF2.size();i++) {
			System.out.println(finalF2.get(i).getConfidende());
		}
		
		return finalF2;
		
	}
	
	
		
}