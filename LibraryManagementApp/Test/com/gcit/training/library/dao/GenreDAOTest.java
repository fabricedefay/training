package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Genre;

public class GenreDAOTest {

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
	public void testGenreDAO() {
		new GenreDAO(conn);
	}

	//@Test
	public void testCreate() throws SQLException {
		Genre genre = new Genre();
		genre.setGenreName("New Genre");
			new GenreDAO(conn).create(genre);

	}

	//@Test
	public void testUpdate() throws SQLException{
			Genre genre = new Genre();
			genre.setGenreName("LA rOMANIA");
			genre.setGenreId(161);
				new GenreDAO(conn).update(genre);
	}

	@Test
	public void testDelete() throws SQLException {
		Genre genre = new Genre();
		genre.setGenreId(162);
		new GenreDAO(conn).delete(genre);
	}
	
	@After
	public void destroy() throws SQLException{
		conn.close();
	}

}
