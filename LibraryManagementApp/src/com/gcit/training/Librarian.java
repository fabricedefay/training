package com.gcit.training;

import java.sql.*;
import java.util.Scanner;

public class Librarian {

	//TODO: change this to arraylist to hold all the library from tbl_library_branch
	String[] LibBranch = {"University Library, Boston", "State Library, New York" , "Federal Library, Washington DC",
	"County Library, McLean VA"}; //add more if program expand
	String[] LibMethods = {"Update the details of the Library" /*, "Add copies of Book to the Branch"*/};
	Connection conn;

	//Constructor ask for branch directed
	public Librarian(Connection conn){
		this.conn = conn;
		askBranch();
	}

	//list all the branch and prompt user to choose the one he/she directs
	public void askBranch(){
		System.out.println("Choose the appropriate number for your branch: ");
		
		String choice = new String();
		Scanner sc = new Scanner(System.in);
		
		for(int i = 1; i <= LibBranch.length; i++){
			System.out.println(i + ") " + LibBranch[i-1]);
		}
		int goBack = LibBranch.length +1;
		System.out.println(goBack +") Quit to previous");
		
		choice =  sc.nextLine();
		askBranchHelper(choice);//helper method of askBranch to help select the right branch for user
	}

	//helper method of askBranch. depending on choice, ask what action to perform
	public void askBranchHelper(String choice){
		switch (choice){
		case "1":
			askForAction("University Library, Boston");
			break;
		case "2":
			askForAction("State Library, New York");
			break;
		case "3":
			askForAction("Federal Library, Washington DC");
			break;
		case "4":
			askForAction("County Library, McLean VA");
			break;
		case "5":
			new WelcomePage();//go back one step
			break;
		default:
			askBranch();//illegal answer
		}
	}

	//list the options a librarian have over their branch
	public void askForAction(String libBranch){
		System.out.println("What do you want to do?");
		String choice = new String();
		Scanner sc = new Scanner(System.in);
		for(int i = 1; i <= LibMethods.length; i++){
			System.out.println(i + ") " + LibMethods[i-1]);
		}
		int goBack = LibMethods.length + 1;
		System.out.println(goBack +") Quit to previous");
		choice =  sc.nextLine();
		askForActionHelper(choice, libBranch);//call method corresponding to user's choice
	}

	//helper method to choose what action Librarian wants to perform
	public void askForActionHelper(String choice, String libBranch){
		switch (choice){
		case "1":
			updateLibDetails(libBranch, getLibAddress(libBranch));
			break;
		/*case "2":
			addBookCopieToBranch(libBranch);
			break;*/
		case "2":
			askBranch();//go back to choose another branch
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			askForAction(libBranch);
		}

	}


	//***********1 of 2 main librarian features**************/
	public void updateLibDetails(String originalName, String originalAddress){

		System.out.println("You have chosen to update the Branch with Branch ID: " +getLibId(originalName)+" and Branch Name: "+originalName+". ");
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.");

		String newName = getNewName(originalName);		
		String newAddress = getNewAddress(originalName, originalAddress);

		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE tbl_library_branch SET branchName = ?,"
					+ " branchAddress = ? WHERE branchName = ?");
			stmt.setString(1, newName);
			stmt.setString(2, newAddress);
			stmt.setString(3, originalName);
		}catch(SQLException e){
			e.printStackTrace();
		}		

		showLibDetails();	
	}

	//helper method for update library details. get new library name from user
	private String getNewName(String originalName){
		String newName;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new branch name or enter N/A for no change:");
		newName = sc.nextLine();

		if (newName.equals("N/A"))/*TODO: Make it user insensitive*/{
			newName = originalName;
			System.out.println("Name stays the same.");
		}else if (newName.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			askForAction(originalName);
		}else System.out.println("New name is: " + newName);
		return newName;
	}

	//return user's desired address for library. helper method for update library details. 
	private String getNewAddress(String originalName, String originalAddress){
		String newAddress;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter new branch address or enter N/A for no change:");
		newAddress = sc.nextLine();

		if (newAddress.equals("N/A"))/*TODO: Make it user insensitive*/{
			newAddress = originalAddress;
			System.out.println("Address stays the same.");
		}else if (newAddress.equals("quit"))/*TODO: Make it user insensitive*/{
			System.out.println("No changes made");
			askForAction(originalName);
		}else System.out.println("New address is:" + newAddress);
		return newAddress;
	}

	//method to get library's address based on the Original name. 
	public String getLibAddress(String libName){
		String address = new String();		
		try{    		
			PreparedStatement stmt = conn.prepareStatement("SELECT branchAddress FROM tbl_library_branch where branchName =?");
			stmt.setString(1, libName);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				address = rs.getString("branchAddress");
		} catch(SQLException e){
			e.printStackTrace();
		}
		return address;		
	}

	//method to get library's branchID based on the Original name.
	public String getLibId(String libName){
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
	}

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
	public void addBookCopieToBranch(String libName){

	}
	

		
}
