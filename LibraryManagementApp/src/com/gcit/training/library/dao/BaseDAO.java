package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.gcit.training.library.domain.Author;
import com.mysql.jdbc.Statement;

public abstract class BaseDAO {

	protected Connection conn = null;

	public BaseDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	public int saveReturnGen(String query, Object[] vals) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		
		int loop = 1;
		for(Object obj : vals){
			if(obj == null)
				stmt.setNull(loop, Types.NULL);
			else 
				stmt.setObject(loop, obj);
			loop++;
		}
		
		stmt.execute();
		ResultSet rs =stmt.getGeneratedKeys();
		if(rs != null && rs.next()){
			return rs.getInt(1);
		}
		else return -1;
	}
	
	public void save(String query, Object[] vals) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		
		int loop = 1;
		for(Object obj : vals){
			if(obj == null)
				stmt.setNull(loop, Types.NULL);
			else 
				stmt.setObject(loop, obj);
			loop++;
		}
		
		stmt.execute();
	}

}


