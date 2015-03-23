package com.gcit.training;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Borrower {
	
	private Connection conn;
	private String[] authorAction = {"Check out a book", "Return a Book"};
	
	public Borrower(Connection conn){
		this.conn =  conn;
		checkCardNumber();
		greet();
		chooseAction();
	}
	
	

	private void greet() {
		System.out.println("Welcome Borrower. Your actions are limited at this time.");
		//System.out.println("Please try again later.");
		
	}
	
	private void checkCardNumber() {
		showBorrowerTable();
		System.out.println("Enter your card number or enter quit to go back: ");
		Scanner sc = new Scanner(System.in); 
		String userIn = sc.nextLine();
		if(userIn.matches("\\d+")){
			int cardNo = Integer.parseInt(userIn);
			try{
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbl_borrower WHERE cardNo =?");
				stmt.setInt(1, cardNo);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					System.out.println("Card number verified.");
					//valid = true;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		else if (userIn.equals("quit")){
			new WelcomePage();
		}else{
			System.out.println("card number not valid.");
			checkCardNumber();
		}
		
	}
	
	private void showBorrowerTable() {
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from tbl_borrower");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println("Card No:" + rs.getInt("cardNo"));
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Address: " + rs.getString("address"));
				System.out.println("Phone" + rs.getString("phone"));
				System.out.println("------------");
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
	}



	public void chooseAction(){
		System.out.println("What number correspond to the action you'd like to perform: ");
		String choice = null;
		Scanner sc = new Scanner(System.in);


		for(int i = 1; i <= authorAction.length; i++){
			System.out.println(i + ") " + authorAction[i-1]);
		}
		System.out.println(0 +") Quit to previous");
		choice =  sc.nextLine();
		chooseActionHelper(choice);//call method corresponding to user's choice
	}

	//helper method to choose what action Librarian wants to perform
	public void chooseActionHelper(String choice){
		switch (choice){
		case "1":
			checkOutBook();
			break;
		case "2":
			returnBook();
			break;
		case "0":
			new WelcomePage();
			break;
		default:
			System.out.println("Please enter a valid option number: ");
			chooseAction();
		}
	}



	private void checkOutBook() {
		// TODO Auto-generated method stub
		System.out.println("Not able to check out book at this time. press any key to go back");
		Scanner sc = new Scanner(System.in);
		if(sc.hasNext()){}
	}



	private void returnBook() {
		// TODO Auto-generated method stub
		
	}
	
}
