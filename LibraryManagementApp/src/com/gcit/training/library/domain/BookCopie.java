package com.gcit.training.library.domain;


public class BookCopie extends AbstractDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3167815963963341942L;
	
	private Book book;
	private Library library;
	private int numOfCopy;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Library getLibrary() {
		return library;
	}
	public void setLibrary(Library library) {
		this.library = library;
	}
	public int getNumOfCopy() {
		return numOfCopy;
	}
	public void setNumOfCopy(int numOfCopy) {
		this.numOfCopy = numOfCopy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((library == null) ? 0 : library.hashCode());
		result = prime * result + numOfCopy;
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
		BookCopie other = (BookCopie) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (library == null) {
			if (other.library != null)
				return false;
		} else if (!library.equals(other.library))
			return false;
		if (numOfCopy != other.numOfCopy)
			return false;
		return true;
	}

}
