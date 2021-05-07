package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class order {

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gbcompany", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertOrder(String name, String quantity, String unit, String date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into orders(`orderId`,`orderName`,`orderQuantity`,`orderUnit`,`orderDate`)"
					+ "values ( ?,  ?,  ?,  ?,  ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, quantity);
			preparedStmt.setString(4, unit);
			preparedStmt.setString(5, date);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newOrders = readOrder();
			output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}";
			
		} catch (Exception e) {
			
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the Order.\"}";
			System.err.println(e.getMessage());
		}
		return output;

	}
	
	public String readOrder() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order Name</th><th>Quantity</th><th>Unit</th><th>Date</th><th>Update</th><th>Remove</th></tr>"; 
			
			String query = "select * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String orderId = Integer.toString(rs.getInt("orderId"));
				String orderName = rs.getString("orderName");
				String orderQuantity = rs.getString("orderQuantity");
				String orderUnit = rs.getString("orderUnit");
				String orderDate = rs.getString("orderDate");
				
				// Add into the html table
				
				
				
				output += "<tr><td><input id='hidOrderIDUpdate' name='hidOrderIDUpdate' type='hidden' value='"+ orderId + "'>" + orderName + "</td>";
				output += "<td>" + orderQuantity + "</td>";
				output += "<td>" + orderUnit + "</td>";
				output += "<td>" + orderDate + "</td>";
				// buttons
				
				   output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
	                        + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger' data-orderid='"
	                        + orderId + "'>" + "</td></tr>";
				
				
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
		} 
		catch (Exception e) {
			output = "Error while reading the Orders.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateOrder(String orderId, String name, String quantity, String unit, String date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE orders SET orderName=?,orderQuantity=?,orderUnit=?,orderDate=? WHERE orderId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, quantity);
			preparedStmt.setString(3, unit);
			preparedStmt.setString(4, date);
			preparedStmt.setInt(5, Integer.parseInt(orderId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newOrders = readOrder();
			output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Order.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteOrder(String orderId) {
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from orders where orderId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(orderId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newOrders = readOrder();
			output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}";
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Order.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}

}
