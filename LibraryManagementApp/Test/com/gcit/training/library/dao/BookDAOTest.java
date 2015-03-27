package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Publisher;

public class BookDAOTest {

	Connection conn = null;
	
	@Before
	public void connect(){

		try{    		
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBookDAO() {
		new BookDAO(conn);
	}

	//@Test
	public void testCreate() {
		Book book = new Book();
		Publisher publisher = new Publisher();
		book.setBookTitle("From Seven Hell");
		publisher.setPubId(100);
		book.setPublisher(publisher);
		try {
			new BookDAO(conn).create(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testUpdate() throws SQLException {
		
			Book book = new Book();
			Publisher publisher = new Publisher(); 
			book.setBookTitle("Beethoven's Symphony");
			book.setBookId(21);
			publisher.setPubId(101);
			book.setPublisher(publisher);
			new BookDAO(conn).update(book);
	
	}

	//@Test
	public void testDelete() {
		Book book =  new Book();
		book.setBookId(44);
		try {
			new BookDAO(conn).delete(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
