package com.dataMiningAlgo;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	private ArrayList<F2> F2list;
	 
	public TableModel(ArrayList<F2> F2list) {
		this.F2list = F2list;
		}
	
	@Override
	public int getRowCount() {
		return F2list.size();
	}

	@Override
	public int getColumnCount() {
		
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		F2 f2 = F2list.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return f2.getITEM_1();
        case 1:
            return f2.getITEM_2();
        case 2:
        	return f2.getSupport();
        case 3:
        	return f2.getConfidende();
        case 4:
        	return f2.getCnt();
        default:
            return null;
        }
	}

}