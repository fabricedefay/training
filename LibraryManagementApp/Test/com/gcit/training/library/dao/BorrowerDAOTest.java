package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Borrower;

public class BorrowerDAOTest {

	Connection conn;

	@Before
	public void connect(){

		try{    		
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testborrowerDAO() {
		new BorrowerDAO(conn);
	}

	//@Test
	public void testCreate() throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setName("New borrower");
		borrower.setAddress("New Address");
	
		new BorrowerDAO(conn).create(borrower);

	}

	//@Test
	public void testUpdate() throws SQLException{
			Borrower borrower = new Borrower();
			borrower.setPhone("000-000-0000");
			borrower.setCardNo(211);
			new BorrowerDAO(conn).update(borrower);
	}

	@Test
	public void testDelete() throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNo(211);
		new BorrowerDAO(conn).delete(borrower);
	}
	
	@After
	public void destroy() throws SQLException{
		conn.close();
	}

}
