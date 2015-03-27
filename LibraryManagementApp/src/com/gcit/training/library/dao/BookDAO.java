package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.gcit.training.library.domain.Author;
import com.gcit.training.library.domain.Book;
import com.gcit.training.library.domain.Genre;
import com.mysql.jdbc.Statement;

public class BookDAO extends BaseDAO {

	public BookDAO(Connection conn){
		
		super(conn);
	}

	public void create(Book book)  throws SQLException{
		int bookId = -1;
		if(book.getPublisher() == null) {
			bookId = saveReturnGen("insert into tbl_book (title, pubId) values (?, ?)",
					new Object[] {book.getBookTitle(), null});
		}else {
			bookId = saveReturnGen("insert into tbl_book (title, pubId) values (?, ?)",
					new Object[] {book.getBookTitle(), book.getPublisher().getPubId()});
		}

		if(book.getBookAuthors() != null && book.getBookAuthors().size() >0){
			for(Author a : book.getBookAuthors()){
				save("insert into tbl_book_authors values (?,?)",
						new Object[] {bookId, a.getAuthorId()});
			}
		}		
	}

	public void update(Book book/*Author[] authors, Genre[] genres*/) throws SQLException{
		save("update tbl_book set title =?, pubId = ?  where bookId =?", new Object[] {book.getBookTitle(),
				book.getPublisher().getPubId(), book.getBookId()});
		/*for(Author a : authors){
			save("insert into tbl_book_authors values (?,?)",
					new Object[] {book.getBookId(), a.getAuthorId()});
		}	
		for(Genre g : genres){
			save("insert into tbl_book_genres values (?,?)",
					new Object[] {g.getGenreId(), book.getBookId()});
		}*/

	}

	public void delete (Book book) throws SQLException{
		save("delete from tbl_book where bookId =?",new Object[] { book.getBookId()});
	}

}
