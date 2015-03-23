package com.gcit.training;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator {

	Connection conn;
	String[] Field = {"book", "author", "publisher"/*, "library", "borrower", "loan"*/};
	String[] bookAction = {"Add book", "Update book","Delete book"};
	String[] authorAction = {"add Author", "update Author", "delete Author"};
	String[] publisherAction = {"add Publisher", "update Publisher", "delete Publisher"};
	/*String[] libraryAction = {"add Library Branches", "update Library Branches", "delete Library Branches"};
	String[] borrowerAction = {"delete Publishers","add Borrowers", "update Borrowers", "delete Borrowers"};
	String loanAction = "overide Loan Date";*/

	//Constructor ask for field to edit
	public Administrator(Connection conn){
		this.conn =  conn;
		greet();
		askForField();
	}

	private void greet() {
		System.out.println("Welcome administrator. Your actions are limited at this time.");
	}

	//first thing they choose
	private void askForField(){
		System.out.println("Choose the Field you want to work on: ");
		String choice = null;
		Scanner sc = new Scanner(System.in);
		for(int i = 1; i <= Field.length; i++){
			System.out.println(i + ") " + Field[i-1]);
		}
		System.out.println(0 +") Quit to previous");
		choice =  sc.nextLine();
		askForFieldHelper(choice);//call method corresponding to user's choice
	}

	private void askForFieldHelper(String choice){
		switch (choice){
		case "1":
			chooseBookAction();
			break;
		case "2":
			chooseAuthorAction();
			break;
		case "3":
			choosePublisherAction();
			break;
			/*case "4":
				chooseLibraryAction();
				break;
			case "5":
				chooseBorrowerAction();
				break;
			case "6":
				chooseLoanAction();
				break;*/
		case "0":
			new WelcomePage();//go back to choose another branch
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			askForField();
		}
	}

	//##############################BOOK#########################################
	//list the options on the book table
	public void chooseBookAction(){
		System.out.println("What number correspond to the action you'd like to perform: ");
		String choice = null;
		Scanner sc = new Scanner(System.in);


		for(int i = 1; i <= bookAction.length; i++){
			System.out.println(i + ") " + bookAction[i-1]);
		}
		System.out.println(0 +") Quit to previous");
		choice =  sc.nextLine();
		chooseBookActionHelper(choice);//call method corresponding to user's choice
	}

	//helper method to choose what action Librarian wants to perform
	public void chooseBookActionHelper(String choice){
		switch (choice){
		case "1":
			addBook();
			break;
		case "2":
			updateBook();
			break;
		case "3":
			deleteBook();
			break;
		case "0":
			askForField();//go back to choose another field
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			chooseBookAction();
		}
	}

	//************1 out of 3 main feature of administrator on Books*****************
	public void addBook(){
		System.out.println("You may enter quit at any time to go back.");
		String newBook = newBookTitle();
		int newPubId = getPubId();

		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO tbl_book (title, pubId) Values(?,?)");
			stmt.setString(1, newBook);
			stmt.setInt(2, newPubId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showBookTable();
		System.out.println("Book added!");
		chooseBookAction();
	}

	//get new title from user
	public String newBookTitle(){
		String newName;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Book Title:");
		newName = sc.nextLine();
		if (newName.equals("quit"))
			chooseBookAction();
		return newName;
	}

	//************2 out of 3 main feature of administrator on Books*****************
	public void updateBook(){
		System.out.println("You may enter quit at any time to go back.");
		int bookId = chooseBookToUpddate();
		String title = getBookTitle(bookId);
		int publisher = getPubId(bookId);

		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId =?");
			stmt.setString(1, title);
			stmt.setInt(2, publisher);
			stmt.setInt(3,bookId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showBookTable();
		System.out.println("Book Updated!");
		chooseBookAction();
	}

	private int chooseBookToUpddate() {
		int realBookId = 0;
		showBookTable();
		System.out.println("Enter book Id of the book to update or delete: ");
		Scanner sc = new Scanner(System.in);
		String bookId = sc.nextLine();
		if (bookId.equals("quit")){
			chooseBookAction();
		}else if(checkBookID(bookId)){
			realBookId = Integer.parseInt(bookId);
		}
		else chooseBookToUpddate();
		return realBookId;
	}

	private boolean checkBookID(String bookId) {
		boolean valid = false;
		if(bookId.matches("\\d+")){			
			int intBookId = Integer.parseInt(bookId);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_book WHERE bookId =?");
				stmt.setInt(1, intBookId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next())
					valid = true;
			}catch(SQLException e){
				e.printStackTrace();
			}
		}		
		if(!valid)
			System.out.println("BookId entered not valid");
		return valid;
	}

	//get Pub Id to add new book
	private Integer getPubId() {
		showPublisherTable();
		String userIn = null; 
		Integer PubId = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("You have to specify the publisher.");
		System.out.println("Please enter a valid Publisher Id:");
		userIn = sc.nextLine();

		if (userIn.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			chooseBookAction();//go back step before update book -->bookAction			
		}else if(!checkPubId(userIn)){
			System.out.println("Publisher Id entered not valid");
			getPubId();
		}else {
			PubId = Integer.parseInt(userIn);
			System.out.println("Publisher Id set to: " + PubId);
		}
		return PubId;		
	}

	//get Pub Id to update existing book
	private int getPubId(int bookId) {
		String pubId; 
		int realPubId = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new Publisher Id or enter N/A for no change:");
		pubId = sc.nextLine();

		if (pubId.equals("N/A"))/*TODO: Make it user insensitive*/{
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT pubId FROM tbl_book WHERE bookId =?");
				stmt.setInt(1, bookId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					realPubId =  rs.getInt("pubId");
					System.out.println("pub ID = "+ realPubId);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}System.out.println("Publisher Id stays the same.");

		}else if (pubId.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			chooseBookAction();//go back step before update book -->bookAction

		}else if(!checkPubId(pubId)){
			System.out.println("Publisher Id entered not valid");
			getPubId(bookId);
		}else {
			realPubId = Integer.parseInt(pubId);
			System.out.println("Publisher Id set to: " + realPubId);
		}
		return realPubId;		
	}

	private boolean checkPubId(String pubId) {
		boolean valid = false;
		if(pubId.matches("\\d+")){
			int intPubId = Integer.parseInt(pubId);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_publisher WHERE publisherId =?");
				stmt.setInt(1, intPubId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					System.out.println("Publisher Id verified.");
					valid = true;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		//if(!valid) System.out.println("Publisher Id entered not valid");
		return valid;
	}

	private String getBookTitle(int bookId) {
		String bookTitle;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Book Title or N/A for no change:");
		bookTitle = sc.nextLine();

		if (bookTitle.equals("N/A"))/*TODO: Make it user insensitive*/{
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT title FROM tbl_book WHERE bookId =?");
				stmt.setInt(1, bookId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next())
					bookTitle =  rs.getString("title");
			}catch(SQLException e){
				e.printStackTrace();
			}System.out.println("Name stays the same.");
		}else if (bookTitle.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			chooseBookAction();//go back step before update book -->bookAction
		}else System.out.println("New name is: " + bookTitle);
		return bookTitle;
	}

	//************3 out of 3 main feature of administrator on Books*****************
	public void deleteBook(){
		int bookId = chooseBookToUpddate();
		try{
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM tbl_book WHERE bookId = ? ");
			stmt.setInt(1, bookId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showBookTable();
		System.out.println("Book Deleted!");
		chooseBookAction();
	}

	private void showBookTable() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_book");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Book Id:" + rs.getInt("bookId"));
				System.out.println("Bool Title: " + rs.getString("title"));
				System.out.println("Publisher Id: " + rs.getString("pubId"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	private void showPublisherTable() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_publisher");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Publisher Id:" + rs.getInt("publisherId"));
				System.out.println("Publisher Name: " + rs.getString("publisherName"));
				System.out.println("Publisher Address: " + rs.getString("publisherAddress"));
				System.out.println("Publisher Phone: " + rs.getString("publisherPhone"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	//########################AUHTOR###################################
	public void chooseAuthorAction(){
		System.out.println("What number correspond to the action you'd like to perform: ");
		String choice = null;
		Scanner sc = new Scanner(System.in);


		for(int i = 1; i <= authorAction.length; i++){
			System.out.println(i + ") " + authorAction[i-1]);
		}
		System.out.println(0 +") Quit to previous");
		choice =  sc.nextLine();
		chooseAuthorActionHelper(choice);//call method corresponding to user's choice
	}


	private void chooseAuthorActionHelper(String choice) {
		switch (choice){
		case "1":
			addAuthor();
			break;
		case "2":
			updateAuthor();
			break;
		case "3":
			deleteAuthor();
			break;
		case "0":
			askForField();//go back to choose another field
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			chooseAuthorAction();
		}

	}

	//****************1 of 3 author functions******************
	public void addAuthor(){
		String newAuthor = newAuthorName();

		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO tbl_author (authorName) Values(?)");
			stmt.setString(1, newAuthor);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showAuthorTable();
		System.out.println("Author added!");
		chooseAuthorAction();
	}

	private String newAuthorName() {
		String newName;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Author's name:");
		newName = sc.nextLine();
		if (newName.equals("quit"))
			chooseAuthorAction();
		return newName;
	}

	//****************2 of 3 author functions******************
	public void updateAuthor(){
		System.out.println("You may enter quit at any time to go back.");
		int authorId = chooseAuthorToUpddate();
		String name = newAuthorName();

		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE tbl_author SET authorName = ? WHERE authorId =?");
			stmt.setString(1, name);
			stmt.setInt(2, authorId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showAuthorTable();
		System.out.println("Author Updated!");
		chooseAuthorAction();
	}

	private int chooseAuthorToUpddate() {
		int authorId = 0;
		showAuthorTable();
		System.out.println("Enter author Id of the author to update or delete: ");
		Scanner sc = new Scanner(System.in);
		String userIn = sc.nextLine();
		if (userIn.equals("quit")){
			chooseAuthorAction();
		}else if(checkAuthorID(userIn)){
			authorId = Integer.parseInt(userIn);
		}
		else chooseAuthorToUpddate();
		return authorId;
	}

	private boolean checkAuthorID(String userIn) {
		boolean valid = false;
		if(userIn.matches("\\d+")){			
			int authorId = Integer.parseInt(userIn);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_author WHERE authorId =?");
				stmt.setInt(1, authorId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next())
					valid = true;
			}catch(SQLException e){
				e.printStackTrace();
			}
		}		
		if(!valid)
			System.out.println("AuthorId entered not valid");
		return valid;
	}

	//****************3 of 3 author functions******************
	public void deleteAuthor(){
		int authorId = chooseAuthorToUpddate();
		try{
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM tbl_author WHERE authorId = ? ");
			stmt.setInt(1, authorId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showAuthorTable();
		System.out.println("Author Deleted!");
		chooseAuthorAction();
	}

	private void showAuthorTable() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_author");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Author Id:" + rs.getInt("authorId"));
				System.out.println("Author Name: " + rs.getString("authorName"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}		
	}

	//########################PUBLISHER#####################################
	public void choosePublisherAction(){
		System.out.println("What number correspond to the action you'd like to perform: ");
		String choice = null;
		Scanner sc = new Scanner(System.in);


		for(int i = 1; i <= publisherAction.length; i++){
			System.out.println(i + ") " + publisherAction[i-1]);
		}
		System.out.println(0 +") Quit to previous");
		choice =  sc.nextLine();
		choosePublisherActionHelper(choice);//call method corresponding to user's choice
	}

	private void choosePublisherActionHelper(String choice) {
		switch (choice){
		case "1":
			addPublishers();
			break;
		case "2":
			updatePublisher();
			break;
		case "3":
			deletePublisher();
			break;
		case "0":
			askForField();//go back to choose another field
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			choosePublisherAction();
		}
	}

	//****************1 of 3 publisher functions******************
	public void addPublishers(){
		String newPublisherName = newPublisherInput("name");
		String newPublisherAddress = newPublisherInput("address");
		String newPublisherPhone = newPublisherInput("phone");

		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) Values(?,?,?)");
			stmt.setString(1, newPublisherName);
			stmt.setString(2, newPublisherAddress);
			stmt.setString(3, newPublisherPhone);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showPublisherTable();
		System.out.println("Publisher added!");
		choosePublisherAction();
	}

	private String newPublisherInput(String info) {
		String newInput;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Publisher's "+ info);
		newInput = sc.nextLine();
		if (newInput.equals("quit"))
			chooseAuthorAction();
		return newInput;
	}

	//****************2 of 3 publisher functions******************
	public void updatePublisher(){
		System.out.println("You may enter quit at any time to go back.");
		int publisherId = choosePublisherToUpddate();
		String name = newPublisherInput("name");
		String address = newPublisherInput("address");
		String phone = newPublisherInput("phone");

		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId =?");
			stmt.setString(1, name);
			stmt.setString(2, address);
			stmt.setString(3, phone);
			stmt.setInt(4, publisherId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		showPublisherTable();
		System.out.println("Publisher Updated!");
		choosePublisherAction();
	}
	
	private int choosePublisherToUpddate() {
		int publisherId = 0;
		showPublisherTable();
		System.out.println("Enter publisher Id of the publisher to update or delete: ");
		Scanner sc = new Scanner(System.in);
		String userIn = sc.nextLine();
		if (userIn.equals("quit")){
			choosePublisherAction();
		}else if(checkPublisherID(userIn)){
			publisherId = Integer.parseInt(userIn);
		}
		else choosePublisherToUpddate();
		return publisherId;
	}

	private boolean checkPublisherID(String userIn) {
		boolean valid = false;
		if(userIn.matches("\\d+")){			
			int publisherId = Integer.parseInt(userIn);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_publisher WHERE publisherId =?");
				stmt.setInt(1, publisherId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next())
					valid = true;
			}catch(SQLException e){
				e.printStackTrace();
			}
		}		
		if(!valid)
			System.out.println("PublisherId entered not valid");
		return valid;
	}

	public void deletePublisher(){
		System.out.println("Cannot delete at the moment");
		System.out.println("Press any key to go back.");
		Scanner sc = new Scanner(System.in);
		boolean input = sc.hasNext();
		if (input)
			choosePublisherAction();
	}

	public void addLibraryBranches(){}

	public void updateLibraryBranches(){}

	public void deleteLibraryBranches(){}

	public void addBorrowers(){}

	public void updateBorrowers(){}

	public void deleteBorrowers(){}

	public void	overideLoanDate(){}



}
