package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.domain.BookLoans;;

public class BookLoansDAO extends BaseDAO {

	public BookLoansDAO(Connection conn) {
		super(conn);
	}
	
public void create(BookLoans bookLoan) throws SQLException {
		
		save("insert into tbl_book_loans values (?,?,?,?,?)",
				new Object[]{bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo(),
				bookLoan.getDateOut(), bookLoan.getDueDate()});		
	}

	public void update(BookLoans bookLoan) throws SQLException {
		save("update tbl_book_loans set bookId =?, branchId =?, cardNo =?, dateOut =?, dueDate =? where bookId =? and branchId = ? and cardNo =?", 		
			new Object[] {bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo(),
			bookLoan.getDateOut(), bookLoan.getDueDate(),bookLoan.getBook().getBookId(),
			bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo()});		
	}

	public void delete(BookLoans bookLoan) throws SQLException {
		save("delete from tbl_bookLoan where bookId =? and branchId = ? and cardNo =?", new Object[] {bookLoan.getBook().getBookId(),
				bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getCardNo()});
	}

}
