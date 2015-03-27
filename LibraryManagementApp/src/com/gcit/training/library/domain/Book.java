package com.gcit.training.library.domain;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8811447112052323711L;
	private int bookId;
	private String bookTitle;
	private Publisher publisher;
	
	private List <Genre> bookGenre;
	private List<Author> bookAuthors;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public List<Genre> getBookGenre() {
		return bookGenre;
	}
	public void setBookGenre(List<Genre> bookGenre) {
		this.bookGenre = bookGenre;
	}
	public List<Author> getBookAuthors() {
		return bookAuthors;
	}
	public void setBookAuthors(List<Author> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bookAuthors == null) ? 0 : bookAuthors.hashCode());
		result = prime * result
				+ ((bookGenre == null) ? 0 : bookGenre.hashCode());
		result = prime * result + bookId;
		result = prime * result
				+ ((bookTitle == null) ? 0 : bookTitle.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookAuthors == null) {
			if (other.bookAuthors != null)
				return false;
		} else if (!bookAuthors.equals(other.bookAuthors))
			return false;
		if (bookGenre == null) {
			if (other.bookGenre != null)
				return false;
		} else if (!bookGenre.equals(other.bookGenre))
			return false;
		if (bookId != other.bookId)
			return false;
		if (bookTitle == null) {
			if (other.bookTitle != null)
				return false;
		} else if (!bookTitle.equals(other.bookTitle))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		return true;
	}
	
}
