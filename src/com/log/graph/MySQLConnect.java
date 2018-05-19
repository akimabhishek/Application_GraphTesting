package com.log.graph;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLConnect {

	public ArrayList<String> SendData(String keyword) {

		ArrayList<String> name = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mappingdb?useSSL=true", "root","root");
			/*
			 * Statement stmt=con.createStatement(); ResultSet
			 * rs=stmt.executeQuery("select * from sub_module");
			 */

			String querycheck = "SELECT ID,NAME,PARENT_ID,DESCRIPTION FROM sub_module WHERE NAME = ?";
			PreparedStatement ps = con.prepareStatement(querycheck);
			ps.setString(1, keyword);
			ResultSet resultset = ps.executeQuery();
			
			if (resultset.next()) {
				Integer id = resultset.getInt(1);
				System.out.println(id);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from sub_module");

				while (rs.next()) {
					Integer id1 = rs.getInt(3);
					if (id1.equals(id)) {
						name.add(rs.getString(2));
					}
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return name;
	}

}
