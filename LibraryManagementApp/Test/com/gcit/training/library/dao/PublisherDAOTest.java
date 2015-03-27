package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Publisher;

public class PublisherDAOTest {

	Connection conn= null;
	
	@Before
	public void connect(){

		try{    		
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPublisherDAO() {
		new PublisherDAO(conn);
	}

	//@Test
	public void testCreate() throws SQLException {
		Publisher publisher = new Publisher(); 
		publisher.setPubName("Claire");
		publisher.setPubAddress("23 Algue St");
		publisher.setPubPhone("3875930298");
		new PublisherDAO(conn).create(publisher);
	}
	
	//@Test
	public void testUpdate() throws SQLException {
		Publisher publisher = new Publisher(); 
		publisher.setPubName("Beethoven");
		publisher.setPubAddress("221 Cambria St");
		publisher.setPubPhone("9847857593");
		publisher.setPubId(111);
		new PublisherDAO(conn).update(publisher);
	}
	
	@Test
	public void testDelete() throws SQLException {
		Publisher publisher =  new Publisher();
		publisher.setPubId(115);
			new PublisherDAO(conn).delete(publisher);
	}
}
