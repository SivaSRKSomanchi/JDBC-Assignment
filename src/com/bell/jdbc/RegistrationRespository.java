package com.bell.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
	private static final String UPDATE_QUERY = "UPDATE register SET password = ? WHERE name = ? AND email = ?";
	private static final String DELETE_QUERY = "delete from register where exists (select 1 from register duplicateregister where duplicateregister.name = register.name and duplicateregister.email = register.email)";
	private static String DELETE_SINGLE_RECORD_QUERY = "";

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
			DatabaseMetaData dbm = con.getMetaData();
			// check if "register" table is there
			ResultSet tables = dbm.getTables(null, null, "register", null);
			if (tables.next()) {
				// Table exists
				System.out.println("Table already created...skiping it");
			} else {
				// Table does not exist
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
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery(SELECT_ALL_QUERY);
			System.out.println("Retrieving Information..");
			System.out.println("=========CUSTOMER DETAILS=========");
			while (rs.next()) {
				String name = rs.getString(1);
				String password = rs.getString(2);
				String gender = rs.getString(3);
				int age = rs.getInt(4);
				String email = rs.getString(5);

				CustInfo info = new CustInfo(name, password, gender, age, email);
				System.out.println(info.toString());
			}
			System.out.println("SELECTION OPERARION SUCCESSFULL. Thank U!");

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void updateRegister(String name, String password, String email)
			throws SQLException {
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform UPDATE Operation.");
		getConnection();
		System.out
				.println("Updating Password corresponding to give Name and Email id.");
		try {
			ps = con.prepareStatement(UPDATE_QUERY);
			ps.setString(2, name);
			ps.setString(1, password);
			ps.setString(3, email);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException se) {
			throw se;
		}
	}

	public boolean selectNameandEmail(String name, String email) {
		boolean result = false;
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM register WHERE name = ? AND email = ?");
			ps.setString(1, name);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();
			System.out.println("Retrieving Information..");
			System.out.println("=========CUSTOMER DETAILS=========");
			System.out.println("\n **Update Record**");
			while (rs.next()) {
				result = true;
				String name1 = rs.getString(1);
				String password = rs.getString(2);
				String gender = rs.getString(3);
				int age = rs.getInt(4);
				String email1 = rs.getString(5);

				CustInfo info = new CustInfo(name1, password, gender, age,
						email1);
				System.out.println(info.toString());

			}

		} catch (SQLException e) {
			System.out.println("Retrieval Problem.. " + e);
		}
		return result;
	}

	public void deleteDUPICATERecord() {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
		try {
			ps = con.prepareStatement(DELETE_QUERY);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				System.out
						.println("=========DELETED DUPLICATE RECORD=========");
			}
		} catch (SQLException e) {
			System.out.println("Retrieval Problem.. " + e);
		}
	}

	public void deleteRecord() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the query: ");
		DELETE_SINGLE_RECORD_QUERY = scanner.nextLine();
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
		try {
			ps = con.prepareStatement(DELETE_SINGLE_RECORD_QUERY);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				System.out.println("=========DELETED Specific RECORD=========");
			}
		} catch (SQLException e) {
			System.out.println("Retrieval Problem.. " + e);
		}
		scanner.close();
	}

	public boolean successfullLogin(String name, String password) {
		// TODO Auto-generated method stub
		boolean result = false;
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
		try {
			ps = con.prepareStatement("SELECT FROM register WHERE name=? AND email=?");
			ps.setString(1, name);
			ps.setString(2, password);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return result;
	}


	public boolean deleteAccount(String name, String email) {
		boolean result = false;
		PreparedStatement ps = null;
		System.out
				.println("Connecting to PostgreSQL DB to perform SELECTION Operation.");
		getConnection();
		try {
			ps = con.prepareStatement("DELETE FROM register WHERE name=? AND email=?");
			ps.setString(1, name);
			ps.setString(2, email);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				result = true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return result;
	}
}
