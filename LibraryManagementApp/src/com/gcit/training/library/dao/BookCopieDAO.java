package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.domain.BookCopie;

public class BookCopieDAO extends BaseDAO {//working

	public BookCopieDAO(Connection conn) {
		super(conn);
	}
	
public void create(BookCopie bookCopie) throws SQLException {
		
		save("insert into tbl_book_copies values (?,?,?)",
				new Object[]{bookCopie.getBook().getBookId(), bookCopie.getLibrary().getBranchId(), bookCopie.getNumOfCopy()});		
	}

	public void update(BookCopie bookCopie) throws SQLException {
		save("update tbl_book_copies set noOfCopies =? where bookId =? and branchId =?", 		
			new Object[] {bookCopie.getNumOfCopy(), bookCopie.getBook().getBookId(), bookCopie.getLibrary().getBranchId()});		
	}

	public void delete(BookCopie bookCopie) throws SQLException {
		save("delete from tbl_book_copies where bookId =? and branchId =?", 
				new Object[] {bookCopie.getBook().getBookId(), bookCopie.getLibrary().getBranchId()});
	}

}
