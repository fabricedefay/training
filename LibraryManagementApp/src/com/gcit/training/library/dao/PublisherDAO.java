package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Publisher;

public class PublisherDAO extends BaseDAO {
	
	public PublisherDAO(Connection conn){
		super(conn);
	}
	
	public void create(Publisher publisher) throws SQLException{
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] {publisher.getPubName(),
				publisher.getPubAddress(), publisher.getPubPhone()});
	}
	
	public void update(Publisher publisher) throws SQLException{
		save("update tbl_publisher set publisherName =?, publisherAddress = ?, publisherPhone = ? "
				+ "where publisherId =?", new Object[] {publisher.getPubName(), publisher.getPubAddress(), 
						publisher.getPubPhone(), publisher.getPubId()});
	}
	
	public void delete(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId =?", new Object[] {publisher.getPubId()});
	}
}
