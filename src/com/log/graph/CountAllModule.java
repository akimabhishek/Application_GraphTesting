package com.log.graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CountAllModule {

	public Map<String, Map<String, Integer>> SQLConn() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<String> arr = new ArrayList<String>();
		Map<String, Map<String, Integer>> map = new HashMap<String, Map<String, Integer>>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mappingdb?useSSL=true", "root", "root");
			st = conn.createStatement();
			rs = st.executeQuery("select * from sub_module where PARENT_ID is not null");

			while (rs.next()) {
				arr.add(rs.getString(2));
				//System.out.println(rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		for (String module : arr) {
			try {
				System.out.println(module);
				String s;
				String filepath = "C:\\Users\\akimabhishek\\Desktop\\Test2.txt";
				FileInputStream fin = new FileInputStream(filepath);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new InputStreamReader(fin));

				while ((s = br.readLine()) != null) {
					if (s.toLowerCase().contains("entered")) {
						if (s.toLowerCase().contains(module.toLowerCase())) {
							
							/*String regex = "(.*) NewArticlesController (.*) THE SMUSER is (.*)";
							Pattern p = Pattern.compile(regex);
							Matcher m = p.matcher(s);*/
							//if (s.toLowerCase().contains(module)) {
								int index = s.toLowerCase().indexOf("the smuser is");
								int len = s.length();
								
								String user = s.toUpperCase().substring(index+14, len);
								if (!map.containsKey(module)) {
									Map<String, Integer> mod_count = new HashMap<String, Integer>();
									mod_count.put(user, 1);
									map.put(module, mod_count);
								}

								else {
									Map<String, Integer> getUser = map.get(module);
									if (!getUser.containsKey(user)) {
										getUser.put(user, 1);
										map.put(module, getUser);

									}

									else {
										getUser.put(user, getUser.get(user) + 1);

									}
								}

							}

						}
					}
				}
			 catch (Exception e) {
				System.out.println(e);
			}

		}
		System.out.println(map);
		return map;
	}

}

/*
 * try(Writer writer = new BufferedWriter(new OutputStreamWriter( new
 * FileOutputStream(new File("C:\\Users\\abhishek\\Desktop\\Output.txt")),
 * StandardCharsets.UTF_8));) { map1.forEach((key, value) -> { try {
 * writer.write(key + " " + value + System.lineSeparator()); } catch
 * (IOException ex) { throw new UncheckedIOException(ex); } }); }
 * catch(UncheckedIOException ex) { throw ex.getCause(); }
 */
