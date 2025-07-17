package packages;

import java.sql.*;

public class CreateTable {
	public static void main(String[] args) {
		try {
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String username= "system";
			String password="system";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,username,password);
			
			String qry ="CREATE TABLE customer_details(name VARCHAR2(50) NOT NULL,contact NUMBER(10) NOT NULL,email VARCHAR2(30) PRIMARY KEY,password VARCHAR2(15))";
			PreparedStatement ps=con.prepareStatement(qry);
			int i=ps.executeUpdate();
			if(i==0) {
				System.out.println("Table created");
			}
			ps.close();
			con.close();
		}catch(ClassNotFoundException c) {
			System.out.println(c);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
}