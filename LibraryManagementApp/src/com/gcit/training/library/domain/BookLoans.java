package com.gcit.training.library.domain;

import java.util.Calendar;


public class BookLoans extends AbstractDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4475746059316156913L;
	private Calendar dateOut;
	private Calendar dueDate;
	private Book book;
	private Library branch;
	private Borrower borrower;
	
	public Calendar getDateOut() {
		return dateOut;
	}
	public void setDateOut(Calendar dateOut) {
		this.dateOut = dateOut;
	}
	public Calendar getDueDate() {
		return dueDate;
	}
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Library getBranch() {
		return branch;
	}
	public void setBranch(Library branch) {
		this.branch = branch;
	}
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

}
