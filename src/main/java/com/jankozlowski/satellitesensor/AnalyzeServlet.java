package com.jankozlowski.satellitesensor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;

import uk.me.g4dpz.satellite.GroundStationPosition;
import uk.me.g4dpz.satellite.Satellite;
import uk.me.g4dpz.satellite.TLE;

@WebServlet(
name="analyzeservlet",
urlPatterns={"/analyze"})
public class AnalyzeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DBConnection dbConnection = new DBConnection();
	
	public AnalyzeServlet() {
	     super();

	 }

	public void destroy() {
		dbConnection.close();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    		
			 String date1String = request.getParameter("datetimepicker");
			 String date2String = request.getParameter("datetimepicker2");
			 String boxnelat = request.getParameter("boxnelat");
			 String boxnelng = request.getParameter("boxnelng");
			 String boxswlat = request.getParameter("boxswlat");
			 String boxswlng = request.getParameter("boxswlng");
	 
			
		/*	 System.out.println(date1String);
			 System.out.println(date2String);

			 System.out.println("boxnelat"+boxnelat);
			 System.out.println("boxnelng"+boxnelng);
			 System.out.println("boxswlat"+boxswlat);
			 System.out.println("boxswlng"+boxswlng);*/

			 ArrayList<Satelita> satelity= new ArrayList<>();
			 
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			 Date date1=null;
			 Date date2=null;
			 try {
				date1 = formatter.parse(date1String);
				date2 = formatter.parse(date2String);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		//	 System.out.println("check");
			 
			 double x =Double.valueOf(boxnelat);
			 double y = Double.valueOf(boxnelng);
			 double xx = Double.valueOf(boxswlat);
			 double yy = Double.valueOf(boxswlng);
			 
			 if(xx<x){
				 double temp = x;
				 x=xx;
				 xx=temp;
			 }
			 if(yy<y){
				 double temp = y;
				 y=yy;
				 yy=temp;
			 }
			 
		//	 System.out.println("x "+x);
		//	 System.out.println("y "+y);
		//	 System.out.println("xx "+xx);
		//	 System.out.println("yy "+yy);
			 
			 
			 
			 
			 
			 satelity = checkAllSatelites(date1, date2, x, y, xx, yy);
			// satelity = checkAllSatelites(date1, date2, 2, 10, 10, 15);//y,x,yy,xx
			 
			 /*
			 FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Sariel\\Desktop\\data.xlsx");
			  Workbook wb = new XSSFWorkbook();
				Sheet sheet1 = wb.createSheet("sat num");
			 
			 int size=4;
			 int cellnum =0;
			 int filenumber = 33;
			 int allnumbers=0;
			 for(int b=-84; b<-80; b=b+size){
				 
					for(int a=-180; a<180; a=a+size){
						
						
			/*			 if(cellnum%100==0){
							 wb.write(fileOut);
							 fileOut.close();
							 wb=new XSSFWorkbook();
							 sheet1 = wb.createSheet("sat num");
							 fileOut =  new FileOutputStream("C:\\Users\\Sariel\\Desktop\\data"+filenumber+".xlsx");
							 filenumber++;
							  
						 }
						
				
			 System.out.println("Row: "+cellnum);
			 satelity = checkAllSatelites(date1, date2, b, a, b+size,a+size );
			 
				for(int c=0; c<satelity.size(); c++){
			   Row row = sheet1.createRow(allnumbers);
			   allnumbers++;
			    Cell cell = row.createCell(0);
			    cell.setCellValue(cellnum);
			    cell = row.createCell(1);
			    cell.setCellValue(satelity.get(c).getNoradId());
			    cell = row.createCell(2);
			    cell.setCellValue(satelity.get(c).getNazwa());
			    cell = row.createCell(3);
			    cell.setCellValue(satelity.get(c).getCel());
			    cell = row.createCell(4);
			    cell.setCellValue(satelity.get(c).getRodzaj());
			    cell = row.createCell(5);
			    cell.setCellValue(satelity.get(c).getUzytkownicy());
			    cell = row.createCell(6);
			    cell.setCellValue(satelity.get(c).getDataStartu());
			    cell = row.createCell(7);
			    cell.setCellValue(satelity.get(c).getKraj());
			    cell = row.createCell(8);
			    cell.setCellValue(satelity.get(c).getPojazdStartowy());
			    cell = row.createCell(9);
			    cell.setCellValue(satelity.get(c).getMasa());
			    cell = row.createCell(10);
			    cell.setCellValue(satelity.get(c).getOrbita().getApogeum());
			    cell = row.createCell(11);
			    cell.setCellValue(satelity.get(c).getOrbita().getCzasObiegu());
			    cell = row.createCell(12);
			    cell.setCellValue(satelity.get(c).getOrbita().getInklinacja());
			    cell = row.createCell(13);
			    cell.setCellValue(satelity.get(c).getOrbita().getKlasaWysokosci());
			    cell = row.createCell(14);
			    cell.setCellValue(satelity.get(c).getOrbita().getMimosrod());
			    cell = row.createCell(15);
			    cell.setCellValue(satelity.get(c).getOrbita().getOrbita());
			    cell = row.createCell(16);
			    cell.setCellValue(satelity.get(c).getOrbita().getPerygeum());
			    cell = row.createCell(17);
			    cell.setCellValue(satelity.get(c).getOrbita().getSEM());
			    cell = row.createCell(18);
			    cell.setCellValue(satelity.size());
			    
			    for(Sensor sen :satelity.get(c).getSensory() ){
			    	Row row2 = sheet1.createRow(allnumbers);
					   allnumbers++;
			    cell = row2.createCell(0);
			    cell.setCellValue(cellnum);
			    cell = row2.createCell(19);
			    cell.setCellValue(sen.getNazwa());
			    cell = row2.createCell(20);
			    cell.setCellValue(sen.getSzerokoscPasma());
			    cell = row2.createCell(21);
			    cell.setCellValue(sen.getTechnikaSkanowania());
			    cell = row2.createCell(22);
			    cell.setCellValue(cellnum);
			    for(Kanal kan :sen.getKanaly() ){
			    	Row row3 = sheet1.createRow(allnumbers);
					   allnumbers++;
					   cell = row3.createCell(0);
					   cell.setCellValue(cellnum);
					   cell = row3.createCell(19);
					   cell.setCellValue(kan.getCentralnaDlugosFal());
					   cell = row3.createCell(20);
					   cell.setCellValue(kan.getZakresFal());
					   cell = row3.createCell(21);
					   cell.setCellValue(kan.getRozdzielcosc());
					    cell = row3.createCell(22);
					    cell.setCellValue(cellnum);
			    }
			    }
			    
			    
			    
			    
			    
			    
				}
				cellnum++;
				}
				}
			    
			    wb.write(fileOut);
			    fileOut.close(); 
			 
			    System.out.println("Done");
			    
			    */
			    
			 ArrayList<Integer> rowspan= new ArrayList<>();
			 
			 for(Satelita sat:satelity){
				 int returnrows = 0;
				 
				for(Sensor sen:sat.getSensory()){
					int rows = sen.getKanaly().size();
					
					if(rows==0){
						returnrows+=1;
					}
					else{
						returnrows+=rows+1;
					}

				}
				rowspan.add(returnrows);
			 }
			 
			 
			 request.setAttribute("rows", rowspan);
			 request.setAttribute("satelity", satelity);
	    	 request.getRequestDispatcher("/analyze.jsp").forward(request, response);
			 
	}
	
	
	public ArrayList<Satelita> checkAllSatelites(Date startDate, Date endDate, double x, double y, double xx, double yy){
		List<Satelita> satelityObserwacyjne = myQuery();
		ArrayList<Satelita> satelityDostepne = new ArrayList<Satelita>();
		long diffInMillies = endDate.getTime() - startDate.getTime();
		int diffInSec = (int)(diffInMillies/1000);
	//	System.out.println("x"+x);
	//	System.out.println("y"+y);
	//	System.out.println("xx"+xx);
	//	System.out.println("yy"+yy);
//		 System.out.println(diffInMillies);
	//	 System.out.println(diffInSec);
		GroundStationPosition GSP = new GroundStationPosition(0,0,0);
	//	System.out.println("check2");
	//	System.out.println(satelityObserwacyjne.size());
		for(Satelita sat: satelityObserwacyjne){
			String TLE[] = new String[3];
			
			TLE[0]=sat.getNazwa();
 	        TLE[1]=sat.getTle1();
 	        TLE[2]=sat.getTle2();  
 
 	        TLE tle = new TLE(TLE);
	    		  
		    Satellite s = uk.me.g4dpz.satellite.SatelliteFactory.createSatellite(tle);
		
	//	    System.out.println(TLE[0]);
		   
		   
		    int time=0;
	    	while(time<diffInSec){
	    
	    		Calendar cal = Calendar.getInstance();
	    		cal.setTime(endDate);
	    		cal.add(Calendar.SECOND, time);
	    		Date future = cal.getTime();
	    		
	    		  
		    	s.calculateSatelliteVectors(future);
		    	
		    	s.calculateSatelliteGroundTrack();
		    	s.getPosition(GSP, future);
		    	double lat = s.getPosition(GSP, future).getLatitude()*180.0/Math.PI;
		    	double lon = s.getPosition(GSP, future).getLongitude()*180.0/Math.PI;
		    	if(lon>180.0f){
		    		lon = -(360.0f - lon);;
		    	}
		    	
		    	if(lat>x&&lat<xx&&lon>y&&lon<yy){
		    		
		    		satelityDostepne.add(sat);
		    		break;
		    	}
		    	time+=15;
	    	}
		}
		
		
	//	System.out.println(satelityDostepne.size());
		return satelityDostepne;
	}
	
	
	
    public <T> List<T> myQuery(){
    	
    	Session s = dbConnection.getSession();
    	
    	Query q = s.createQuery("from Satelita where rodzaj='Earth Observation'");
    	q.setMaxResults(1000);
    	List myList = q.list();
		
		s.close();
	//	System.out.println(myList.size());
		return myList;
    }
	
	
	 
}
