package org.nchc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


@WebServlet( name="FlowEntryHandler" , urlPatterns={"/flowentry.do"})
@MultipartConfig
public class FlowEntryHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		resp.setContentType("application/json");
		
		String jsonString = req.getParameter("json");
		PrintWriter out = resp.getWriter();
		
		try {
			JSONObject jso = new JSONObject(jsonString);
			
			out.print(jso);
			out.flush();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}
