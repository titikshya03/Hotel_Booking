package packages;

import java.sql.*;

public class MealPrice {
	public static void main(String[] args) {
		try {
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String username= "system";
			String password="system";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,username,password);
			
			String qry ="CREATE TABLE meal_price(breakfast_price NUMBER(10,2),lunch_price NUMBER(10,2),dinner_price NUMBER(10,2))";
			PreparedStatement ps=con.prepareStatement(qry);
			int i=ps.executeUpdate();
			if(i==0) {
				System.out.println("Table created");
			}
			String insertDataQuery = "INSERT INTO meal_price(breakfast_price, lunch_price, dinner_price) VALUES (?, ?, ?)";
			PreparedStatement ps1=con.prepareStatement(insertDataQuery);
			Object[][] roomData = {
	                {300, 500, 700}
	            };
			for (Object[] row : roomData) {
                ps1.setDouble(1, ((Number) row[0]).doubleValue());  
                ps1.setDouble(2, ((Number) row[1]).doubleValue());  
                ps1.setDouble(3, ((Number) row[2]).doubleValue());  
                ps1.addBatch();
            }

			ps1.executeBatch();
			System.out.println("Data inserted successfully");
			ps.close();
			ps1.close();
			con.close();
		}catch(ClassNotFoundException c) {
			System.out.println(c);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
}