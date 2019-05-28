package com.dataMiningAlgo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dataMiningAlgo.jdbc.JDBCConnection;

public class DataCleaning {

	String s = new String();
    StringBuffer sb = new StringBuffer();
    JDBCConnection con = new JDBCConnection();
	Connection conn;
	CallableStatement cstmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void dataClean() {
		
		
		//System.out.println(conn);
		String query = "{CALL unknownplayer}";
		try {
			conn = con.getConnection();
			cstmt = conn.prepareCall(query);
			int result = cstmt.executeUpdate();
			System.out.println("***" + result);
			
			addPrimaryAndForeignKeys();
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addPrimaryAndForeignKeys() {
		String scriptFilePath = "C://MEng//656//Project//primaryKeyAndForeignKey.sql";//provide the absolute path
				
			String q = "";
			File f = new File(scriptFilePath); // source path is the absolute path of sql file.
			try {
				conn = con.getConnection();
			    BufferedReader bf = new BufferedReader(new FileReader(f));
			        String line = null;
			        line = bf.readLine();
			        while (line != null) {
			            q = q + line + "\n";
			            line = bf.readLine();
			        }
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
			//System.out.println("222" + q);
			// Now we have the content of the dumpfile in 'q'.
			// We must separate the queries, so they can be executed. And Java Simply does this:
			String[] commands = q.split(";");
	
			try {
			    Statement statement = conn.createStatement();
			    for (String s : commands) {
			        statement.execute(s);
			        System.out.println("executed statement-" + s);
			    }
			} catch (Exception ex) {
			}
	}
	
	public void dataRestore() {
		String scriptFilePath = "C://MEng//656//Project//lahman2016.sql";
		
		String q = "";
		File f = new File(scriptFilePath); // source path is the absolute path of dumpfile.
		try {
			conn = con.getConnection();
		    BufferedReader bf = new BufferedReader(new FileReader(f));
		        String line = null;
		        line = bf.readLine();
		        while (line != null) {
		            q = q + line + "\n";
		            line = bf.readLine();
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		//System.out.println("222" + q);
		// Now we have the content of the dumpfile in 'q'.
		// We must separate the queries, so they can be executed. And Java Simply does this:
		String[] commands = q.split(";");

		try {
		    Statement statement = conn.createStatement();
		    for (String s : commands) {
		        statement.execute(s);
		        System.out.println("executed statement-" + s);
		    }
		} catch (Exception ex) {
		}
		
	}
}