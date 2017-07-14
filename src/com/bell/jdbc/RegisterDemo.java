package com.bell.jdbc;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class RegisterDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String name = "", paswd = "", gender = "", email = "", s = "";
		int age = 0, n = 1;
		Scanner scanner = new Scanner(System.in);
		RegistrationRespository rr = new RegistrationRespository();
		RegisterDemo rd = new RegisterDemo();

		// Insert values into PostgreSQL through user input
		while (n != 0) {
			if (s.equalsIgnoreCase("no")) {
				n = 0;
			}
			if (n == 1) {
				System.out
						.println("Please Enter the customer information. Say 'yes' or 'no'..");
				s = scanner.next();
			} else if (n > 1) {
				System.out
						.println("Do you want to insert another customer details.. Please say 'yes'/'no'. Thank You once again!");
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
				} catch (NumberFormatException | InputMismatchException e) {
					System.out
							.println("Please Re-Enter Carefully.. Thank You!");
				}
				CustInfo custInfo = new CustInfo(name, paswd, gender, age,
						email);

				// Creating Table and Inserting Values in that Table
				try {
					rr.createRegistration();
					rr.insertCustInfo(custInfo);

				} catch (PSQLException e) {
					// TODO Auto-generated catch block
					System.out
							.println("As the table already exists, Let's insert another column..");
				}
				++n;
			} else if (n > 0) {
				System.out
						.println("Thanks for providing information! We appreciate your time and patience!");
			}
		}

		// Retrieving DataBase using SELECT statement..
		System.out
				.println("\nDo U want to view all records from Register Table. Say 'yes' or 'no'?");
		String p = scanner.next();
		if (p.equalsIgnoreCase("yes")) {
			try {
				rr.selectCustInfo();
			} catch (PSQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				;
			}
		} else {
			System.out.println("Thanks for visiting..");
		}

		// Updating DataBase
		System.out
				.println("\nDo U want to Update Password and Age in Register Table. Say 'yes' or 'no'?");
		String pp = scanner.next();
		if (pp.equalsIgnoreCase("yes")) {

			System.out.println("Enter Customer Name (you want to upadte): ");
			scanner.nextLine();
			name = scanner.nextLine();
			System.out
					.println("Enter Customer Email Id (you want to upadte): ");
			email = scanner.next();
			System.out
					.println("Enter Customer's New Password (this will be reflected in DataBase): ");
			paswd = scanner.next();
			try {
				rr.updateRegister(name, paswd, email);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Updating Table fot some problem... " + e);
			}
			System.out
					.println("UPDATED PASSWORD. \nDo U want to view Updated Record from Register Table. Say 'yes' or 'no'?");
			if (scanner.next().equalsIgnoreCase("yes")) {
				rr.selectNameandEmail(name, email);
			} else {
				System.out.println("Your Update was successfull. Thank U!");
			}
		} else {
			System.out.println("Thanks for visiting..");
		}

		System.out
				.println("\nDo U want to DELETE a duplicate record in Register Table. Say 'yes' or 'no'?");
		String ppp = scanner.next();
		if (ppp.equalsIgnoreCase("yes")) {
			rd.deleteOperation();
		} 
		scanner.close();

	}

	public void deleteOperation() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Customer Name (you want to delete): ");
		scanner.nextLine();
		String name = scanner.nextLine();
		System.out.println("Enter Customer Email Id (you want to delete): ");
		String email = scanner.next();
		RegistrationRespository rr = new RegistrationRespository();
		rr.deleteRecord(name, email);
	}

}
