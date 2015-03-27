package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.BookCopie;
import com.gcit.training.library.domain.Library;

public class BookCopieDAOTest {
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
	public void testBookCopieDAO() {
		new BookCopieDAO(conn);
	}
	
	//@Test
	public void testCreate() throws SQLException {
		BookCopie bCopie = new BookCopie();
		Book book = new Book();
		book.setBookId(22);
		Library branch = new Library();
		branch.setBranchId(55);
		bCopie.setBook(book);
		bCopie.setLibrary(branch);
		bCopie.setNumOfCopy(7);
		new BookCopieDAO(conn).create(bCopie);
	}

	//@Test
	public void testUpdate() throws SQLException{
		BookCopie bCopie = new BookCopie();
		Book book = new Book();
		book.setBookId(22);
		Library branch = new Library();
		branch.setBranchId(55);
		bCopie.setBook(book);
		bCopie.setLibrary(branch);
		bCopie.setNumOfCopy(10);
		new BookCopieDAO(conn).update(bCopie);
	}

	@Test
	public void testDelete() throws SQLException {
		BookCopie bCopie = new BookCopie();
		Book book = new Book();
		book.setBookId(22);
		Library branch = new Library();
		branch.setBranchId(55);
		bCopie.setBook(book);
		bCopie.setLibrary(branch);
		bCopie.setNumOfCopy(10);
		new BookCopieDAO(conn).delete(bCopie);
	}
	
	@After
	public void destroy() throws SQLException{
		conn.close();
	}


}
