package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Library;

public class LibraryDAOTest {

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
	public void testLibraryDAO() {
		new LibraryDAO(conn);
	}

	//@Test
	public void testCreate() {
		Library library = new Library();
		library.setBranchName("Jackson Ville Lib ");
		library.setBranchAddress("837 green bay St");
		try {
			new LibraryDAO(conn).create(library);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Create author failed");
		}
	}

	//@Test
	public void testUpdate() {
		Library library = new Library();
		library.setBranchName("Minisota Lib ");
		library.setBranchAddress("103 Princeton St");
		library.setBranchId(60);
		try {
			new LibraryDAO(conn).update(library);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Update library failed");
		}
	}

	@Test
	public void testDelete() throws SQLException {
		Library library =  new Library();
		library.setBranchId(63);
			new LibraryDAO(conn).delete(library);
	}

}
