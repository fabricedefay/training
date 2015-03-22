package com.gcit.training;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator {

	Connection conn;
	String[] Field = {"book"/*, "author", "publisher", "library", "borrower", "loan"*/};
	String[] bookAction = {"Add book", "Update book","Delete book"};
	/*String[] authorAction = {"add Author", "update Author", "delete Author"};
	String[] publisherAction = {"add Publishers", "update Publishers", "delete Publishers"};
	String[] libraryAction = {"add Library Branches", "update Library Branches", "delete Library Branches"};
	String[] borrowerAction = {"delete Publishers","add Borrowers", "update Borrowers", "delete Borrowers"};
	String loanAction = "overide Loan Date";*/

	//Constructor ask for field to edit
	public Administrator(Connection conn){
		this.conn =  conn;
		greet();
		askForField();
	}

	private void greet() {
		System.out.println("Welcome administrator.");
	}
	
	//first thing they choose
		public void askForField(){
			System.out.println("Choose the Field you want to work on: ");
			String choice = null;
			Scanner sc = new Scanner(System.in);
			for(int i = 1; i <= Field.length; i++){
				System.out.println(i + ") " + Field[i-1]);
			}
			int goBack = Field.length + 1;
			System.out.println(goBack +") Quit to previous");
			choice =  sc.nextLine();
			askForFieldHelper(choice);//call method corresponding to user's choice
		}


		public void askForFieldHelper(String choice){
			switch (choice){
			case "1":
				chooseBookAction();
				break;
			/*case "2":
				chooseBookAction();
				break;
			case "3":
				chooseBookAction();
				break;
			case "4":
				chooseBookAction();
				break;
			case "5":
				chooseBookAction();
				break;
			case "6":
				chooseBookAction();
				break;*/
			case "2":
				new WelcomePage();//go back to choose another branch
				break;
			default:
				System.out.println("Please enter a valid option number: ");
				askForField();
			}
		}

		//list the options on the book table
		public void chooseBookAction(){
			System.out.println("What number correspond to the action you'd like to perform: ");
			String choice = null;
			Scanner sc = new Scanner(System.in);
			for(int i = 1; i <= bookAction.length; i++){
				System.out.println(i + ") " + bookAction[i-1]);
			}
			int goBack = bookAction.length + 1;
			System.out.println(goBack +") Quit to previous");
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
			case "4":
				askForField();//go back to choose another field
				break;
			default:
				System.out.println("Please enter a valid option number: ");
				chooseBookAction();
			}
		}

	public void addBook(){
		String newBook = newBookTitle();		
		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO tbl_book (title) Values(?)");
			stmt.setString(1, newBook);
		}catch(SQLException e){
			e.printStackTrace();
		}
		showBookTable();
	}

	//get new title from user
	public String newBookTitle(){
		String newName;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new branch name or enter N/A for no change:");
		newName = sc.nextLine();
		return newName;
	}

	public void updateBook(){
		System.out.println("You may enter quit at any time to go back.");
		int bookId = chooseBookToUpddate();
		String title = getBookTitle(bookId);
		int publisher = getPubId(bookId);

		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE INTO tbl_book SET title = ?, pubId = ? WHERE bookId =?");
			stmt.setString(1, title);
			stmt.setInt(2, publisher);
			stmt.setInt(3,bookId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		showBookTable();
	}

	private int chooseBookToUpddate() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_book");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Book Id:" + rs.getInt("branchId"));
				System.out.println("Title Name: " + rs.getString("branchName"));
				System.out.println("PubId: " + rs.getString("branchAddress"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

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
				//if(rs.next)
				realPubId =  rs.getInt("pubId");
			}catch(SQLException e){
				e.printStackTrace();
			}System.out.println("Name stays the same.");
		}else if (pubId.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			chooseBookAction();//go back step before update book -->bookAction
		}else if(!checkPubId(pubId)){
			
			System.out.println("Publisher Id entered not valid");
			getPubId(bookId);
		}else System.out.println("New Publishere Id is: " + realPubId);
		return realPubId;		
	}

	private boolean checkPubId(String pubId) {
		boolean valid = false;
		if(pubId.matches("\\d+")){
			valid = true;
			int intPubId = Integer.parseInt(pubId);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_book WHERE publisherId =?");
				stmt.setInt(1, intPubId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					System.out.println("Publisher Id entered already exists.");
					valid = false;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else System.out.println("Publisher Id entered not valid");
		return valid;
	}

	private String getBookTitle(int bookId) {
		String bookTitle;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new branch name or enter N/A for no change:");
		bookTitle = sc.nextLine();

		if (bookTitle.equals("N/A"))/*TODO: Make it user insensitive*/{
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT title FROM tbl_book WHERE bookId =?");
				stmt.setInt(1, bookId);
				ResultSet rs = stmt.executeQuery();
				//if(rs.next)
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

	//list the all the books' title and id so the user can pick
	private int getBookId() {

		return 0;
	}

	//display book so user can choose which one to delete
	public void deleteBook(){
		int bookId = getBookId();
		try{
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM tbl_book WHERE bookId = ? ");
			stmt.setInt(1, bookId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		showBookTable();
	}

	public void showBookTable() {}

	public void addAuthor(){}

	public void updateAuthor(){}

	public void deleteAuthor(){}

	public void addPublishers(){}

	public void updatePublishers(){}

	public void deletePublishers(){}

	public void addLibraryBranches(){}

	public void updateLibraryBranches(){}

	public void deleteLibraryBranches(){}

	public void addBorrowers(){}

	public void updateBorrowers(){}

	public void deleteBorrowers(){}

	public void	overideLoanDate(){}

	

	




}
