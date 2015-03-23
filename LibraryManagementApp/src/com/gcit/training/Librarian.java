package com.gcit.training;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Librarian {

	ArrayList<String> LibBranch = new ArrayList<String>();
	ArrayList<Integer> branchId = new ArrayList<Integer>();
	String[] LibMethods = {"Update the details of the Library" , "Add copies of Book to the Branch"};
	Connection conn;

	//Constructor ask for branch directed
	public Librarian(Connection conn){
		this.conn = conn;
		populateLibBranch();
		populatebranchId();
		askBranch();
	}

	//populate libBranch with library name from DB
	private void populateLibBranch() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select branchName from tbl_library_branch");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				LibBranch.add(rs.getString("branchName"));
			}
			//LibBranch.add("Boston");

		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	//populate libBranch with library name from DB
	private void populatebranchId() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select branchId from tbl_library_branch");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				branchId.add(rs.getInt("branchId"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	//list all the branch and prompt user to choose the one he/she directs
	private void askBranch(){
		System.out.println("Choose the appropriate id for your branch: ");

		String choice = new String();
		Scanner sc = new Scanner(System.in);

		for(int i = 0; i < LibBranch.size(); i++){
			System.out.println(branchId.get(i) + ") " + LibBranch.get(i));
		}
		System.out.println(0+ ") go back");
		choice =  sc.nextLine();
		askBranchHelper(choice);//helper method of askBranch to help select the right branch for user
	}

	//helper method of askBranch. depending on choice, ask what action to perform
	private void askBranchHelper(String choice){
		int branchId = 0;		
		if(choice.equals("0")){
			new WelcomePage();
		}else if(checkbranchID(choice)){
			branchId = Integer.parseInt(choice);
			askForAction(branchId);
		}else askBranch();
	}

	private boolean checkbranchID(String branchId) {
		boolean valid = false;
		if(branchId.matches("\\d+")){			
			int realBranchId = Integer.parseInt(branchId);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_library_branch WHERE branchId =?");
				stmt.setInt(1, realBranchId);
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

	//list the options a librarian have over their branch
	public void askForAction(int branchId){
		System.out.println("What do you want to do?");
		String choice = new String();
		Scanner sc = new Scanner(System.in);
		for(int i = 1; i <= LibMethods.length; i++){
			System.out.println(i + ") " + LibMethods[i-1]);
		}
		//int goBack = LibMethods.length + 1;
		System.out.println(0 +") Quit to previous");
		choice =  sc.nextLine();
		askForActionHelper(choice, branchId);//call method corresponding to user's choice
	}

	//helper method to choose what action Librarian wants to perform
	public void askForActionHelper(String choice, int branchId){
		switch (choice){
		case "1":
			updateLibDetails(branchId);
			break;
			case "2":
			addBookCopieToBranch(branchId);
			break;
		case "0":
			askBranch();//go back to choose another branch
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			askForAction(branchId);
		}

	}


	//***********1 of 2 main librarian features**************/
	public void updateLibDetails(int branchId){

		System.out.println("You have chosen to update the Branch with Branch ID: " + branchId +" and Branch Name: "+ getOriginalBranchName(branchId) +". ");
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.");

		String newName = getNewName(branchId);		
		String newAddress = getNewAddress(branchId);

		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE tbl_library_branch SET branchName = ?,"
					+ " branchAddress = ? WHERE branchName = ?");
			stmt.setString(1, newName);
			stmt.setString(2, newAddress);
			stmt.setString(3, getOriginalBranchName(branchId));
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}		

		showLibDetails();
		new Librarian(conn);
	}

	//helper method for update library details. get new library name from user
	private String getNewName(int branchId){
		String newName;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new branch name or enter N/A for no change:");
		newName = sc.nextLine();

		if (newName.equals("N/A"))/*TODO: Make it user insensitive*/{
			newName = getOriginalBranchName(branchId);
			System.out.println("Name stays the same.");
		}else if (newName.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made.");
			askForAction(branchId);
		}else System.out.println("New name is: " + newName);
		return newName;
	}

	//return user's desired address for library. helper method for update library details. 
	private String getNewAddress(int branchId){
		String newAddress;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new branch address or enter N/A for no change:");
		newAddress = sc.nextLine();

		if (newAddress.equals("N/A"))/*TODO: Make it user insensitive*/{
			newAddress = getOriginalBranchAddress(branchId);
			System.out.println("Address stays the same.");
		}else if (newAddress.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			askForAction(branchId);
		}else System.out.println("New address is:" + newAddress);
		return newAddress;
	}

	//method to get library's name based on branch Id. 
	private String getOriginalBranchName(int branchId) {
		String branchName = new String();		
		try{    		
			PreparedStatement stmt = conn.prepareStatement("SELECT branchName FROM tbl_library_branch where branchId =?");
			stmt.setInt(1, branchId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				branchName = rs.getString("branchName");
		} catch(SQLException e){
			e.printStackTrace();
		}
		return branchName;		
	}

	//method to get library's address based on branch Id. 
	private String getOriginalBranchAddress(int branchId){
		String address = new String();		
		try{    		
			PreparedStatement stmt = conn.prepareStatement("SELECT branchAddress FROM tbl_library_branch where branchId =?");
			stmt.setInt(1, branchId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				address = rs.getString("branchAddress");
		} catch(SQLException e){
			e.printStackTrace();
		}
		return address;		
	}

	//method to get library's branchID based on the Original name.
	/*public String getLibId(String libName){
		String libId = new String();

		try{ 
			PreparedStatement stmt = conn.prepareStatement("SELECT branchId FROM tbl_library_branch WHERE branchName = ? ");
			stmt.setString(1, libName);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				libId =""+rs.getInt("branchId");		
		} catch(SQLException e){
			e.printStackTrace();
		}
		return libId;
	}*/

	//method to show change in table after updating library branch
	public void showLibDetails(){
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_library_branch");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Branch Id:" + rs.getInt("branchId"));
				System.out.println("Branch Name: " + rs.getString("branchName"));
				System.out.println("Branch Address: " + rs.getString("branchAddress"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
	}



	//**************2nd main feature of a librarian***********/ 
	public void addBookCopieToBranch(int branchId){
		int bookId = chooseBookId(branchId);
		int numberOfCopie = chooseNumCopie(branchId);
		
		if (bookIdAlreadyIn(bookId, branchId))
			updateBookCopie(numberOfCopie, branchId);
		else insertBookCopie(branchId, bookId, numberOfCopie);
		System.out.println("Book copies updated!");
		showBookCopies();
		new Librarian(conn);
	}

	private boolean bookIdAlreadyIn(int bookId, int branchId) {
		boolean bookAlreadyIn = false;
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_book_copies WHERE bookId =? AND branchId = ?");
			stmt.setInt(1, bookId);
			stmt.setInt(2, branchId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				bookAlreadyIn = true;
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return bookAlreadyIn;
	}

	private void updateBookCopie(int numberOfCopie, int branchId) {
		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE tbl_book_copies SET noOfCopies = ? WHERE branchId = ?");
			stmt.setInt(1, numberOfCopie);
			stmt.setInt(2, branchId);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}		

	}

	private void insertBookCopie(int branchId, int bookId, int numberOfCopie) {
		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO tbl_book_copies Values(?,?,?)");
			stmt.setInt(1, bookId);
			stmt.setInt(2, branchId);
			stmt.setInt(3, numberOfCopie);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	private int chooseNumCopie(int branchId) {
		String userIn;
		boolean valid = false;
		int numberOfCopie =0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a positive number of copie:");
		userIn = sc.nextLine();
		if (userIn.matches("\\d+"))/*TODO: Make it user insensitive*/{
			numberOfCopie = Integer.parseInt(userIn);
			if(numberOfCopie >= 0){
				System.out.println("Number of copies set to " + numberOfCopie);
				valid = true;
			}
		}else if (userIn.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made.");
			askForAction(branchId);
		}
		if (!valid)
			chooseNumCopie(branchId);
		return numberOfCopie;
	}

	private int chooseBookId(int branchId) {
		int bookId = 0;
		showBookTable();
		System.out.println("Enter book Id of the book to update: ");
		Scanner sc = new Scanner(System.in);
		String userIn = sc.nextLine();
		if(checkBookID(userIn)){
			bookId = Integer.parseInt(userIn);
		}
		else chooseBookId(branchId);
		return bookId;
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
	
	private void showBookCopies() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_book_copies");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Book Id:" + rs.getInt("bookId"));
				System.out.println("Branch Id: " + rs.getInt("branchId"));
				System.out.println("Number Of Copies: " + rs.getString("noOfCopies"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
	}

}
