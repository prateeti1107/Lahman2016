package com.dataMiningAlgo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.dataMiningAlgo.jdbc.JDBCConnection;

public class DecisionTree {

	JDBCConnection con = new JDBCConnection();
	Connection conn;
	CallableStatement cstmt = null;
	ResultSet rs = null;
	float resultEntropy = 0;
	float outEntr = 0;
	PreparedStatement pstmt = null;
	
	ArrayList<Info> finalInfo = new ArrayList<>();
	
	
	public void calculateEntropy(String tableName, String column, String predicate, String outputEntropy) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL CalcEntropy(?,?,?,?)}";
		cstmt = conn.prepareCall(query);
		cstmt.setString(1, tableName);
		cstmt.setString(2, column);
		cstmt.setString(3, predicate);
		cstmt.registerOutParameter(4, Types.VARCHAR);
		//cstmt.setString(4, outputEntropy);
		int r = cstmt.executeUpdate();
		System.out.println(cstmt.getString(4));
		conn.close();
//		resultEntropy = cstmt.getFloat(4);
//		System.out.println(resultEntropy);
//		return resultEntropy;
	}
	
	public String calculateTableEntropy(String tableName, String predicate, String maxGain) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		//String maxInfoGain = null;
		
		conn = con.getConnection();
		System.out.println(conn);
		String query = "{CALL TableEntropy(?,?,?)}";
		cstmt = conn.prepareCall(query);
		cstmt.setString(1, tableName);
		cstmt.setString(2, predicate);
		
		cstmt.registerOutParameter(3, Types.VARCHAR);
		//cstmt.setString(4, outputEntropy);
		int r = cstmt.executeUpdate();
		System.out.println("Best Attribute for Split:"+cstmt.getString(3));
		conn.close();
		return cstmt.getString(3);
		
		
		
	}
	
	public ArrayList<Info> selectAttribute() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException 
	{
		conn = con.getConnection();
		//String sql1 = "select @name";
		//pstmt = conn.prepareStatement(sql1);
		//rs = pstmt.executeQuery();
		//String pridicted=rs.getString("name");
		//System.out.println("name "+pridicted);
		
		
		String sql = "select * from info";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			Info info = new Info(rs.getString("col"), rs.getString("entropy"), rs.getString("info_gain"));
	        finalInfo.add(info);
		}
		
		conn.close();
//		for(int i = 0; i< finalInfo.size();i++) {
//			System.out.println("Final info:-" + finalInfo.get(i).getEntropy());
//		}
		return finalInfo;
		
	}
}