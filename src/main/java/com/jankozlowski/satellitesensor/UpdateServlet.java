package com.jankozlowski.satellitesensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import uk.me.g4dpz.satellite.Satellite;
import uk.me.g4dpz.satellite.TLE;

@WebServlet(
name="updateservlet",
urlPatterns={"/update" })

public class UpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DBConnection dbConnection = new DBConnection();
	
	public UpdateServlet() {
		super();

	}
	
	public void destroy() {
		dbConnection.close();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 synchronized(this){
			 try{
				 SatelliteSensorServlet.update=true;
			 updateTLE(response);
			 }
			 catch(Exception e){
				 
			 }
			 finally{
				 SatelliteSensorServlet.update=false;
			 }
		 }
		
	}

	private void updateTLE(HttpServletResponse response) throws IOException {
    	
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<p>Updated Satellites:</p>");
		out.flush();
		
		
    	String baseURL = "https://www.space-track.org";
		String authPath = "/auth/login";
		String userName = "*******";
		String password = "*******";
		String query = "/basicspacedata/query/class/tle_latest/ORDINAL/1/EPOCH/%3Enow-30/orderby/NORAD_CAT_ID/format/3le";
 
		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
		
		URL url = new URL(baseURL+authPath);
		
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		String input = "identity="+userName+"&password="+password;

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))); //login

		String output;

		url = new URL(baseURL + query);

		br = new BufferedReader(new InputStreamReader((url.openStream()))); //getData
		
		Session s = dbConnection.getSession();
		int i=0;
		
		while ((output = br.readLine()) != null) {
			String tle[] = new String[3];
			tle[0] = output;
			tle[1] = br.readLine();
			tle[2] = br.readLine();


			Transaction tx = s.beginTransaction();
			TLE tleObject = new TLE(tle);
			
			Satellite sat = uk.me.g4dpz.satellite.SatelliteFactory.createSatellite(tleObject);
				
			Object cat[] = {sat.getTLE().getCatnum()};
			List<Satelita> satelita  = myQuery("from Satelita where norad_id=? ",cat);
			 	
			
			if(satelita.size()==1){
			
				satelita.get(0).setTle1(tle[1]);
				satelita.get(0).setTle2(tle[2]);
				s.merge(satelita.get(0));
			}
			
			else{
				Satelita satelite = new Satelita();
				satelite.setNoradId(sat.getTLE().getCatnum());
				satelite.setTle1(tle[1]);
				satelite.setTle2(tle[2]);
				
				if(sat.getTLE().getName().contains("DEB")){
					satelite.setRodzaj("Debris");
				}
				else if(sat.getTLE().getName().contains("R/B")){
					satelite.setRodzaj("Rocket Body");
				}
				else{
					satelite.setRodzaj("Not Defined");
				}
				
				Orbita orbita = new Orbita();
				orbita.setNoradId(sat.getTLE().getCatnum());
				
				double semimajoraxis = 86400/sat.getTLE().getMeanmo();
				semimajoraxis = Math.pow(semimajoraxis,2);
				semimajoraxis = semimajoraxis*(6.67e-11);		
				semimajoraxis = semimajoraxis*(5.98e24);		
				semimajoraxis = semimajoraxis/(4*Math.pow(Math.PI, 2));	
				semimajoraxis = Math.pow(semimajoraxis, 1/3f);		
				semimajoraxis = semimajoraxis/1000;
				
				orbita.setPerygeum(semimajoraxis*(1 - sat.getTLE().getEccn()) - 6371);
		    	orbita.setApogeum(semimajoraxis*(1 + sat.getTLE().getEccn()) - 6371);
		    	orbita.setInklinacja(sat.getTLE().getIncl());
				orbita.setMimosrod(sat.getTLE().getEccn());
				orbita.setSEM(semimajoraxis);
				orbita.setCzasObiegu(86400/sat.getTLE().getMeanmo()/60);
				
				if(sat.getTLE().getMeanmo()>=0.99&&sat.getTLE().getMeanmo()<=1.01&&sat.getTLE().getEccn()<0.01)
					orbita.setKlasaWysokosci("GEO");
				else if(sat.getTLE().getEccn()<0.25&&sat.getTLE().getMeanmo()>=3&&sat.getTLE().getMeanmo()<=4){
					orbita.setKlasaWysokosci("MEO");
				}
				else if(sat.getTLE().getMeanmo()>11.25&&sat.getTLE().getEccn()<0.25){
					orbita.setKlasaWysokosci("LEO");
				}
				else if(sat.getTLE().getEccn()>0.25){
					orbita.setKlasaWysokosci("HEO");
				}
				else{
					orbita.setKlasaWysokosci("UNC");
				}
				
				satelite.setOrbita(orbita);
				
				satelita.add(satelite);
				s.saveOrUpdate(satelita.get(0));
			}
			
			
			s.flush();
			tx.commit();
			
			i++;
			
			
			
			if(i%100==0){
				out.println("<p>Satellites updated "+i+"</p>");
				out.flush();
				
			}
		//	if(i%1000==0){
			//	System.out.println("update "+ i);
			//}
			
		}
		s.close();
	
		url = new URL(baseURL + "/ajaxauth/logout");
		br = new BufferedReader(new InputStreamReader((url.openStream()))); //logout
		conn.disconnect();
		
		out.println("<p>All TLE data have been updated</p>");
		out.println("</body></html>");
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
