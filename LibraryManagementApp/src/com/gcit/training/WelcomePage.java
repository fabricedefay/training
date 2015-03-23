package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class WelcomePage {
	
	String[] userType = {"Librarian", "Administrator", "Borrower"}; //add more if program expand
	
	Connection conn = null;
	
	public WelcomePage(){		
		connect();
		greet();
		askUserType();
		
	}
	
	public void connect(){
		
		try{    		
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");
	    }catch(SQLException e){
	        e.printStackTrace();
	    }
	}
	
	public void greet(){
		System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you?");
		System.out.println("At the moment, your options are: ");
	}
	
	public void askUserType(){
		System.out.println("Please choose the appropriate number for your category: ");
		String choice = new String();
		Scanner sc = new Scanner(System.in);
		for(int i = 1; i <= userType.length; i++){
			System.out.println(i + ") " + userType[i-1]);
		}
		System.out.println("0) Quit Application");
		choice =  sc.nextLine();
		beginAction(choice);
	}
	
	
	public void beginAction(String choice){
		switch (choice){
		case "1":
			new Librarian(conn);
			this.endProgram();
			break;
		case "2":
			new Administrator(conn);
			break;
		case "3":
			new Borrower(conn);
			break;
		case "0":
			endProgram();
			break;
			default:
				askUserType();			
		}
	}

	private void endProgram() {
		System.out.println("Goodbye!");
		System.exit(0);
		
	}

}
