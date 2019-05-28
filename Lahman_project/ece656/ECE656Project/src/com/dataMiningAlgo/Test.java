package com.dataMiningAlgo;

import java.sql.Connection;
import java.sql.SQLException;

import com.dataMiningAlgo.jdbc.JDBCConnection;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		JDBCConnection con = new JDBCConnection();
		Connection conn = con.getConnection();
		System.out.println(conn);
		
		Apriori apriori = new Apriori();
		apriori.createItemSet(2000);
		apriori.selectCompetetion("DivWin");
		
		DecisionTree dt = new DecisionTree();
		dt.calculateEntropy("interview", "level", "inter", "@a");
		dt.calculateTableEntropy("new", "inducted", "@name");
		
		dt.selectAttribute();
		
	}

}
