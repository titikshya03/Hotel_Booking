package packages;

import java.sql.*;

public class CustomerBookings {
	public static void main(String[] args) {
		try {
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String username= "system";
			String password="system";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,username,password);
			
			String qry ="CREATE TABLE customer_bookings(customer_email VARCHAR2(30),room_type VARCHAR2(10),bed_type VARCHAR2(10),cleaning VARCHAR2(5),room_count NUMBER(3), booking_date DATE DEFAULT SYSDATE,total_price NUMBER(10,2), FOREIGN KEY (customer_email) REFERENCES customer_details(email))";
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