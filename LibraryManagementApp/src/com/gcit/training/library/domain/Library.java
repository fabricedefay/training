package com.gcit.training.library.domain;

import java.util.List;

public class Library extends AbstractDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2205129769128665249L;
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	private List<Book> bookCopies;

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public List<Book> getBookCopies() {
		return bookCopies;
	}

	public void setBookCopies(List<Book> bookCopies) {
		this.bookCopies = bookCopies;
	}
	
}
 