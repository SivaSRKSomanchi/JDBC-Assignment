package com.bell.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class RegistrationRespository {

	private static final String CREATE_QUERY = "create table register(name character varying(40) NOT NULL, password character varying(40) NOT NULL, gender character varying(40) NOT NULL, age character varying(40) NOT NULL, email character varying(40) NOT NULL)";
	private static final String INSERT_QUERY = "INSERT INTO register (name, password, gender, age, email) values(?,?,?,?,?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM register";
	private Connection con = null;

	private void getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("PostgreSQL JDBC Driver Registered!");
			con = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/b6", "postgres",
					"sivaS123");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out
					.println("Where is your MySQL JDBC Driver? Check URL, UserName and Password once again. Thank U! ");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check @ DB");
		}
		if (con != null) {
			System.out.println("Connection made successfully.....");
		}

	}

	public boolean createRegistration() throws PSQLException {
		boolean result = false;
		PreparedStatement ps = null;
		Statement st = null;
		System.out.println("Connecting to PostgreSQL DB to CREATE TABLE.");
		getConnection();
		try {
			st = con.createStatement();
			boolean istableexist = st
					.execute("SELECT EXISTS (SELECT 1 FROM pg_tables where schemaname='public' AND tablename='register1')");
			if (!istableexist) {
				System.out.println("Table already created...skiping it");
			} else {
				ps = con.prepareStatement(CREATE_QUERY);
				result = ps.execute();
				System.out.println("Successfully created");
			}

		} catch (SQLException e) {
			System.out.println("Exception in creation");
			System.out.println(e);
		}
		return result;

	}

	public int insertCustInfo(CustInfo info) {
		int result = 0;
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform INSERTION Operation.");
		getConnection();
		try {
			ps = con.prepareStatement(INSERT_QUERY);
			ps.setString(1, info.getName());
			ps.setString(2, info.getPassword());
			ps.setString(3, info.getGender());
			ps.setInt(4, info.getAge());
			ps.setString(5, info.getEmail());

			result = ps.executeUpdate();
			System.out.println("Insertion Done Successfully. Thank U!");
		} catch (SQLException e) {
			System.out.println("exception in insertion");
		} finally {

			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Resources ps and con not closed properly!");
			}
		}

		return result;
	}

	public void selectCustInfo() throws PSQLException {
		Statement s = null;
//		Scanner scanner = new Scanner(System.in);
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
//		System.out.println("Do you want view all records. Say YES/NO?");
//		String sp = scanner.next();
//		if (sp.equalsIgnoreCase("yes")) {
			try {
				s = con.createStatement();
				ResultSet rs = s.executeQuery(SELECT_ALL_QUERY);
				System.out.println("Retrieving Information..");
				while (rs.next()) {
					String name = rs.getString(1);
					String password = rs.getString(2);
					String gender = rs.getString(3);
					int age = rs.getInt(4);
					String email = rs.getString(5);
					System.out.println("=========CUSTOMER DETAILS=========");
					CustInfo info = new CustInfo(name, password, gender, age,
							email);
					System.out.println(info.toString());
				}
				System.out.println("SELECTION OPERARION SUCCESSFULL. Thank U!");

			} catch (SQLException e) {
				System.out.println(e);
			}
//		}else{
//			System.out.println("Enter following details to view your perspective details.");
//			
//		}

	}

}