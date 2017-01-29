package com.jankozlowski.satellitesensor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet(
name="loginservlet",
urlPatterns={"/login" })

public class LoginServlet extends HttpServlet {

	DBConnection dbConnection = new DBConnection();
	
	private static final long serialVersionUID = 1L;
	public LoginServlet() {
	     super();

	 }
	public void destroy() {
		dbConnection.close();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
/*		Session s = dbConnection.getSession();
   	 Transaction tx = s.beginTransaction();
		s.createSQLQuery("DROP TABLE IF EXISTS satelliteuser").executeUpdate();
    	 
    	
    	 s.createSQLQuery("create table satelliteuser (userid int, nazwa varchar(250),password varchar(250))").executeUpdate();

    	 
		SatelliteUser user= new SatelliteUser();
		user.setNazwa("admin");
		user.setPassword("*******");
		
		s.save(user);
		tx.commit();*/
    	 
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String login = req.getParameter("login");
	    String password = req.getParameter("password");
	    
	    
	    Object userObj[] = new Object[1];
	    userObj[0] =login;

    	List<SatelliteUser> user  = myQuery("from SatelliteUser where nazwa=? ",userObj);
    	if(user.size()>0&&user.get(0).getPassword().equals(password)){
    	
    		resp.sendRedirect(req.getContextPath()+"/adminpanel.jsp");
	    }
    	else{
    		resp.sendRedirect(req.getContextPath()+"/login.jsp");
    	}
    	
	}
	
public <T> List<T> myQuery(String query, Object[] parameters){
    	
    	Session s = dbConnection.getSession();
    	
    	Query q = s.createQuery(query);
		for(int a=0; a<parameters.length; a++){
			q.setParameter(a, parameters[a]);
		}
		
		List myList = q.list();
		
		s.close();
		return myList;
    }
}
