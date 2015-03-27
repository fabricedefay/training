package com.gcit.training.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.training.library.domain.Genre;

public class GenreDAO extends BaseDAO {

	public GenreDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

public void create(Genre genre) throws SQLException {
		
		save("insert into tbl_genre (genreName) values (?)",
				new Object[]{genre.getGenreName()});		
	}

	public void update(Genre genre) throws SQLException {
		save("update tbl_genre set genreName =? where genreId =?", 		
			new Object[] {genre.getGenreName(),genre.getGenreId()});		
	}

	public void delete(Genre genre) throws SQLException {
		save("delete from tbl_genre where genreId =?", new Object[] {genre.getGenreId()});
	}
}
