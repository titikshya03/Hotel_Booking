package packages;

import java.sql.*;

public class CustomerMealBooking {
	public static void main(String[] args) {
		try {
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String username= "system";
			String password="system";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,username,password);
			
			String qry ="CREATE TABLE customer_meals( cust_email VARCHAR2(50), breakfast_order VARCHAR2(5), lunch_order VARCHAR2(5),  dinner_order VARCHAR2(5),  total_price NUMBER(10,2), FOREIGN KEY (cust_email) REFERENCES customer_details(email))";
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