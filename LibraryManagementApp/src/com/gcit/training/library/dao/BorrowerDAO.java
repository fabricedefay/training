package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.domain.Borrower;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	
public void create(Borrower borrower) throws SQLException {
		
		save("insert into tbl_borrower (name, address, phone)values (?,?,?)",
				new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone()});		
	}

	public void update(Borrower borrower) throws SQLException {
		save("update tbl_borrower set name =?, address =?, phone = ? where cardNo =?", 		
			new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});		
	}

	public void delete(Borrower borrower) throws SQLException {
		save("delete from tbl_borrower where cardNo =?", new Object[] {borrower.getCardNo()});
	}

}
