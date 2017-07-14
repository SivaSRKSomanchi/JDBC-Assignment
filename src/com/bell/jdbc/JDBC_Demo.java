package com.bell.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/b6","postgres","sivaS123");
			System.out.println("CONNECTED..");
			Statement statement = con.createStatement();
			String sql = "SELECT name,password FROM cust_info";
			ResultSet rs = statement.executeQuery(sql);
			//System.out.println(rs);
			while(rs.next()){
				
				String name = rs.getString(1);
				String paswd = rs.getString(2);
				System.out.println("Credentails stored::" + name +" "+paswd);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't able to connect 1..");
		}catch (SQLException e){
			e.printStackTrace();
			System.out.println("Couldn't able to connect 2..");
		}
		

	}

}
class Cust{
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPaswd() {
		return paswd;
	}
	public void setPaswd(String paswd) {
		this.paswd = paswd;
	}
	String paswd;
}