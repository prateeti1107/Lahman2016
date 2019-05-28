package com.dataMiningAlgo;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class DTTableModel extends AbstractTableModel {

	private ArrayList<Info> Infolist;
	String headerList[] = new String[]{"Columns","Entropy","Information Gain"};
	 
	public DTTableModel(ArrayList<Info> F2list) {
		this.Infolist = F2list;
		
		}
	
	@Override
	public int getRowCount() {
		return Infolist.size();
	}

	@Override
	public int getColumnCount() {
		
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Info f2 = Infolist.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return f2.getCol();
        case 1:
            return f2.getEntropy();
        case 2:
        	return f2.getInfo_gain();
        default:
            return null;
        }
	}

}