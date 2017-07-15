package com.bell.jdbc;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class RegisterDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "", paswd = "", gender = "", email = "";
		int age = 0;
		int n = 1; // just to run the loop. to ask admin to skip the current step or not.
		String s = "";
		Scanner scanner = new Scanner(System.in);
		RegistrationRespository rr = new RegistrationRespository();
		RegisterDemo rd = new RegisterDemo();
		

		/************************************************/
		/* Insert values into PostgreSQL through user input */
		while (n != 0) {
			if (s.equalsIgnoreCase("no")) {
				n = 0;
			}
			if (n == 1) {
				System.out.println("Please Enter the customer information. Say 'yes' or 'no'..");
				s = scanner.next();
			} else if (n > 1) {
				System.out.println("Do you want to insert another customer details.. Please say 'yes'/'no'. Thank You once again!");
				s = scanner.next();
			}
			if (s.equalsIgnoreCase("yes")) {
				try {
					System.out.println("Enter Customer Name: ");
					scanner.nextLine();
					name = scanner.nextLine();
					System.out.println("Enter Customer Password: ");
					paswd = scanner.next();
					System.out.println("Enter Customer Gender: ");
					gender = scanner.next();
					System.out.println("Enter Customer Age: ");
					age = scanner.nextInt();
					System.out.println("Enter Customer Email Id: ");
					email = scanner.next();
					n++;
				} catch (NumberFormatException | InputMismatchException e) {
					System.out.println("Please Re-Enter Carefully.. Thank You!");
				}
				CustInfo custInfo = new CustInfo(name, paswd, gender, age, email);
				try {
					rr.createRegistration();  /* Creating Table and Inserting Values in that Table */
					rr.insertCustInfo(custInfo);

				} catch (PSQLException e) {
					// TODO Auto-generated catch block
					System.out.println("As the table already exists, Let's insert another column..");
				}

			} else if (n > 0) {
				System.out.println("Thanks for providing information! We appreciate your time and patience!");
			}
		}

		/************************************************/
		/* Retrieving DataBase using SELECT statement..*/
		System.out.println("\nDo U want to view all records from Register Table. Say 'yes' or 'no'?");
		String p = scanner.next();
		if (p.equalsIgnoreCase("yes")) {
			try {
				rr.selectCustInfo(); // displays all records from the table
			} catch (PSQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		} else {
			System.out.println("Thanks for visiting..");
		}
		
		/************************************************/
		/* CHECKING USERNAME AND PASSWORD. (LOGIN) */
		System.out.println("Please enter the name: ");
		String name1=scanner.next();
		System.out.println("Please enter you password:");
		String password = scanner.next();
		boolean value = rr.successfullLogin(name1, password);
		if(value)
			System.out.println("Login successful");
		else{
			System.out.println("Login FAILED. YOU CAN'T PERFORM ANY 'CRUD' OPERATIONS ON THIS DATABASE.\n DELETE YOUR RECORD AND REGISTER AGAIN. (SAY Yes/No): ");
			if(scanner.next().equalsIgnoreCase("yes")){
				System.out.println("Please enter the USERNAME: ");
				name=scanner.next();
				System.out.println("Please enter you password:");
				email = scanner.next();
				boolean result = rr.deleteAccount(name1, email);
				if(result)
					System.out.println("Deleted YOUR RECORD IN DATABASE, PLEASE INSERT AGAIN");
				else
					System.out.println("FAILED TO DELETE.");
			}
		}
		
		
		/************************************************/
		/* Updating DataBase */
		System.out.println("\nDo U want to Update Password in Register Table. Say 'yes' or 'no'?");
		String pp = scanner.next();
		if (pp.equalsIgnoreCase("yes")) {
			
			/*Updates the password in selected record.*/
			System.out.println("Enter Customer Name (you want to upadte): ");
			scanner.nextLine();
			name = scanner.nextLine();
			System.out.println("Enter Customer Email Id (you want to upadte): ");
			email = scanner.next();
			System.out.println("Enter Customer's New Password (this will be reflected in DataBase): ");
			paswd = scanner.next();
			try {
				rr.updateRegister(name, paswd, email);
			} catch (SQLException e) {
				System.out.println("Updating Table fot some problem... " + e);
			}
			System.out.println("UPDATED PASSWORD. \nDo U want to view Updated Record from Register Table. Say 'yes' or 'no'?");
			if (scanner.next().equalsIgnoreCase("yes")) {
				rr.selectNameandEmail(name, email);
			} else {
				System.out.println("Your Update was successfull. Thank U!");
			}
		} else {
			System.out.println("Thanks for visiting..");
		}

		
		/************************************************/
		/* DUPLICATE RECORD DELETION */
		System.out.println("\nDo U want to DELETE a duplicate record in Register Table. Say 'yes' or 'no'?");
		String ppp = scanner.next();
		if (ppp.equalsIgnoreCase("yes")) {
			rd.deleteDUPLICATE_Operation(); // Deletes the duplicate records in the table
		}

		
		/************************************************/
		/* DELETE OPERATION */
		System.out.println("\nDo U want to DELETE any SPECIFIC record in Register Table. Say 'yes' or 'no'?");
		String pppp = scanner.next();
		if (pppp.equalsIgnoreCase("yes")) {
			rr.deleteRecord(); // Deletes the records in the table. Give the query through console...
		}
		System.out.println("\nDo U want to view all records from Register Table. Say 'yes' or 'no'?");
		String p1 = scanner.next();
		if (p1.equalsIgnoreCase("yes")) {
			try {
				rr.selectCustInfo(); // displays all records from the table
			} catch (PSQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		} else {
			System.out.println("Thanks for visiting..");
		}
		scanner.close();

	}
	/************************************************/
	/* main method ends here */

	
	
	/************************************************/
	public void deleteDUPLICATE_Operation() {
		RegistrationRespository rr = new RegistrationRespository();
		rr.deleteDUPICATERecord();
	}
}
