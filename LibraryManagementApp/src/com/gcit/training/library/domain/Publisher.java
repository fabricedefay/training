package com.gcit.training.library.domain;

import java.util.List;

public class Publisher extends AbstractDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pubId;
	private String pubName;
	private String pubAddress;
	private String pubPhone;	
	
	private List<Book> books;

	public int getPubId() {
		return pubId;
	}


	public void setPubId(int pubId) {
		this.pubId = pubId;
	}


	public String getPubName() {
		return pubName;
	}


	public void setPubName(String pubName) {
		this.pubName = pubName;
	}


	public String getPubAddress() {
		return pubAddress;
	}


	public void setPubAddress(String pubAddress) {
		this.pubAddress = pubAddress;
	}


	public String getPubPhone() {
		return pubPhone;
	}


	public void setPubPhone(String pubPhone) {
		this.pubPhone = pubPhone;
	}


	public List<Book> getBooks() {
		return books;
	}


	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
