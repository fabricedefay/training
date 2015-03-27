package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.domain.Library;

public class LibraryDAO extends BaseDAO{

	public LibraryDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void create(Library library) throws SQLException {
		
		save("insert into tbl_library_branch (branchName, branchAddress) values (?,?)",
				new Object[]{library.getBranchName(), library.getBranchAddress()});		
	}

	public void update(Library library) throws SQLException {
		save("update tbl_library_branch set branchName =? , branchAddress = ? where branchId =? ", 		
			new Object[] {library.getBranchName(),library.getBranchAddress(),library.getBranchId()});		
	}

	public void delete(Library library) throws SQLException {
		save("delete from tbl_library_branch where branchId =?", new Object[] {library.getBranchId()});
	}

}
