package com.bell.jdbc;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class RegisterDemoHardCoded {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String name = "", paswd = "", gender = "", email = "", s = "";
		int age = 0, n = 1;
		Scanner scanner = new Scanner(System.in);
		RegistrationRespository rr = new RegistrationRespository();
		while (n != 0 ) {
			if(s.equalsIgnoreCase("no")){
				n=0;
			}
			if (n == 1) {
				System.out
						.println("Please Enter the customer information. Say 'yes' or 'no'..");
				s = scanner.next();
			} else if(n>1){
				System.out
						.println("Do you want to insert another customer details.. Please say 'yes'/'no'. Thank You once again!");
				s = scanner.next();
			}

			if(s.equalsIgnoreCase("yes")) {
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
				
				try {
					rr.createRegistration();
					rr.insertCustInfo(custInfo);
					
				} catch (PSQLException e) {
					// TODO Auto-generated catch block
					System.out
							.println("As the table already exists, Let's insert another column..");
				}
				++n;
			}else if(n>0){
				System.out.println("Thanks for providing information! We appreciate your time and patience!");
			}
		}
		System.out.println("\nDo U want to view all records from Register Table. Say 'yes' or 'no'?");
		String p = scanner.next();
		if(p.equalsIgnoreCase("yes")){
			try {
				rr.selectCustInfo();
			} catch (PSQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);;
			}
		}else{
			System.out.println("Thanks for visiting..");
		}
		
		scanner.close();

	}

}
