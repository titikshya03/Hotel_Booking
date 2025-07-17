package packages;

import java.sql.*;

public class RoomDetails {
	public static void main(String[] args) {
		try {
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String username= "system";
			String password="system";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,username,password);
			
			String qry ="CREATE TABLE room_details(room_type VARCHAR2(10),bed_type VARCHAR2(10),price_per_night NUMBER(10,2), cleaning_price NUMBER(10,2))";
			Statement smt=con.createStatement();
			int i=smt.executeUpdate(qry);
			if(i==0) {
				System.out.println("Table created");
			}
			String insertDataQuery = "INSERT INTO room_details (room_type, bed_type, price_per_night, cleaning_price) VALUES (?, ?, ?, ?)";
			PreparedStatement ps=con.prepareStatement(insertDataQuery);
			Object[][] roomData = {
	                {"AC", "Single", 2000,  200},
	                {"AC", "Double", 2500, 250},
	                {"Non-AC", "Single", 1500, 180},
	                {"Non-AC", "Double", 1800, 200}
	            };
			for (Object[] row : roomData) {
                ps.setString(1, (String)row[0]); 
                ps.setString(2,(String) row[1]); 
                ps.setDouble(3, ((Number) row[2]).doubleValue());  
                ps.setDouble(4, ((Number) row[3]).doubleValue());  
                ps.addBatch();
            }

			ps.executeBatch();
			System.out.println("Data inserted successfully");
			ps.close();
			con.close();
		}catch(ClassNotFoundException c) {
			System.out.println(c);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
}