package com.gcit.training.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.BookLoans;
import com.gcit.training.library.domain.Borrower;
import com.gcit.training.library.domain.Library;

public class BookLoansDAOTest {
	
	private Connection conn;

	@Before
	public void connect(){

		try{    		
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testBookLoansDAO() {
		new BookLoansDAO(conn);
	}

	@Test
	public void testCreate() throws SQLException {
		BookLoans bookLoan = new BookLoans();
		Book book = new Book();
		Library branch = new Library();
		Borrower borrower = new Borrower();
		Calendar dateOut = new GregorianCalendar(2004, 4, 18);
		Calendar dueDate = new GregorianCalendar(2010, 4, 18);
		book.setBookId(21);
		branch.setBranchId(55);
		borrower.setCardNo(203);
		bookLoan.setBook(book);
		bookLoan.setBranch(branch);
		bookLoan.setBorrower(borrower);
		bookLoan.setDateOut(dateOut);
		bookLoan.setDateOut(dueDate);
		new BookLoansDAO(conn).create(bookLoan);
	}

	//@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	//@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
