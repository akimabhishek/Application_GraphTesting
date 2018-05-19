package com.log.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView datafetch() {
		ModelAndView model = new ModelAndView("datasite");
		return model;
	}

	@RequestMapping(value = "/charts", method = RequestMethod.POST)
	public ModelAndView datastore(@RequestParam("Ctrl_Name") String Ctrl_Name) {
		
		ModelAndView model = new ModelAndView("datasite");
	//	JSONObject obj = new JSONObject();
		System.out.println(Ctrl_Name);
		Map<String,Map<String,Integer>> map = new HashMap<String,Map<String,Integer>>();

		ArrayList<String> arr = new ArrayList<String>();
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		
		MySQLConnect obj3 = (MySQLConnect) context.getBean("module");
		arr = obj3.SendData(Ctrl_Name);
		System.out.println(arr);
		

		// Keep it safe!!!
		/*
		 * try {
		 * 
		 * @SuppressWarnings("resource") ApplicationContext context=new
		 * ClassPathXmlApplicationContext("application.xml"); CountAllModule obj2 =
		 * (CountAllModule) context.getBean("fetchdata"); map_all = obj2.SQLConn();
		 * 
		 * if(map_all.containsKey(Ctrl_Name)) {
		 * 
		 * MySQLConnect obj1 = (MySQLConnect) context.getBean("module");
		 * ArrayList<String> arr = new ArrayList<String>(); arr =
		 * obj1.SendData(Ctrl_Name);
		 * 
		 * for(String str : arr) { try { FileInputStream fin = new
		 * FileInputStream("C:\\Users\\abhishek\\Desktop\\Output.txt");
		 * 
		 * @SuppressWarnings("resource") BufferedReader br = new BufferedReader(new
		 * InputStreamReader(fin)); String s; while ((s=br.readLine())!=null) {
		 * 
		 * if(s.contains(str)) { //System.out.println(s); String[] columns =
		 * s.split(" "); map.put(columns[0], Integer.parseInt(columns[1])); } } for
		 * (Map.Entry<String, Integer> entry : map.entrySet()) { String key =
		 * entry.getKey(); Integer value = entry.getValue(); obj.put(key, value); } }
		 * catch(FileNotFoundException exception) { System.out.println(exception); }
		 * catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 * 
		 * 
		 * } catch(IOException exception) { System.out.println(exception); }
		 */
		// Keep it safe!!!
		
		try {
			CountAllModule obj1 = (CountAllModule) context.getBean("fetchdata");
			map = obj1.SQLConn();
			for(String str : arr)
			{
				if(map.containsKey(str))
				{
					Map<String,Integer> redefine = map.get(str);
					RefineMap obj = (RefineMap) context.getBean("refine");
					redefine = obj.Refine(redefine);
				}
			}0
			

		} catch (Exception e) {
			System.out.println(e);
		}
		return model;
	}
}
