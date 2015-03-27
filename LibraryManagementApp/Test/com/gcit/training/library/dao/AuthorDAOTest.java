package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Author;

public class AuthorDAOTest {

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
	public void testAuthorDAO() {
		
		new AuthorDAO(conn);
	}
	

	@Test
	public void testCreate() throws SQLException {
		Author author = new Author();
		author.setAuthorName("Stephen Jackson");
			new AuthorDAO(conn).create(author);

	}

	//	@Test
		public void testUpdate() {
			
				Author author = new Author();
				author.setAuthorName("Beethoven");
				author.setAuthorId(19);
				try {
					new AuthorDAO(conn).update(author);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	
		//@Test
		public void testDelete() {
			Author author =  new Author();
			author.setAuthorId(22);
			try {
				new AuthorDAO(conn).delete(author);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Author update failed");
			}
		}
		
		@After
		public void destroy() throws SQLException{
			conn.close();
		}

}
