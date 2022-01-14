package com;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



import java.sql.ResultSet;

public class Movies {		
		
	public static void main(String args[])
	{
		  MovieDatabase md=new MovieDatabase();
		  md.seedDatabase();
		  System.out.println("\nList of all movies.................\n");
		  md.getAllMovies();
		  System.out.println("\nList of Movies of Tom Holland..................\n");
		  md.getMoviesOfActor("Tom Holland");
	}
}

class MovieDatabase{
	 
	private Connection connect() {
	        String url = "jdbc:sqlite:D:/sqllite/databases/Filmography.db";
	    Connection conn = null;
	    try {
	        conn = DriverManager.getConnection(url);
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return conn;
	}
	
	 public void seedDatabase() {  
	      String moviesList[][]= {{"Spider-Man: No Way Home","Tom Holland","Zendaya","2021","Jon Watts"},
	    		  				{"Spider-Man: Far from Home","Tom Holland","Zendaya","2019","Jon Watts"},
	    		  				{"Descendants","Mitchell Hope","Dove Cameron","2015","Kenny Ortega"},
	    		  				{"Cloud 9","Luke Benward","Dove Cameron","2014","Paul Hoen"},
	    		  				{"The Avengers","Robert Downey Jr.","Scarlett Johansson","2012","Joss Whedon"},
	    		  				{"Iron Man","Robert Downey Jr.","Gwyneth Paltrow","2008","Jon Favreau"},
	    		  				{"Sky High","Nicholas Braun","Mary Elizabeth Winstead","2005","Mike Mitchell"},
	    		  				{"Space Jam","Michael Jordan","Theresa Randle","1996","Joe Pytka"},
	    		  				{"Captain America: The First Avenger","Chris Evans","Hayley Atwell","2011","Joe Johnston"},
	    		  				{"Spider-Man","Tobey Maguire","Kirsten Dunst","2002","Sam Raimi"},
	    		  				{"Spider-Man: Homecoming","Tom Holland","Zendaya","2017","Jon Watts"}};  
		  try {
	        	String sql = "CREATE TABLE IF NOT EXISTS MOVIES (\n"
		                + "	id integer PRIMARY KEY,"
		                + "	Movie_name text NOT NULL,"
		                + "	Lead_Actor text,"
		                + "	Lead_Actress text,"
		                + "	Year_Of_Release text,"
		                + "	Director text"
		                + ")";  
	        	Connection conn = this.connect();
	        	Statement stmt = conn.createStatement();
	        	stmt.execute(sql);
	        	sql = "INSERT INTO MOVIES VALUES(?,?,?,?,?,?)";
	            PreparedStatement pstmt = conn.prepareStatement(sql); 
	            for(int i=0;i<moviesList.length;i++)
	            {
	            	pstmt.setInt(1, i+1);
	            	pstmt.setString(2, moviesList[i][0]);
	            	pstmt.setString(3, moviesList[i][1]);
	            	pstmt.setString(4, moviesList[i][2]);
	            	pstmt.setString(5, moviesList[i][3]);
	            	pstmt.setString(6, moviesList[i][4]);
		            pstmt.executeUpdate();
	            }
	            conn.close();
	       
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	      
	    }
	 
	 public void getAllMovies(){
	        String sql = "SELECT * FROM MOVIES";
	    
	    try {
	    	Connection conn = this.connect();
	    
	         Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql);
	        
	        System.out.println("Id\tMovie_name"+" ".repeat(34-10)+"\tLead_Actor"+" ".repeat(17-10)+"\tLead_Actress"+" ".repeat(23-12)+"\tYear_Of_Release\tDirector");
	        String formattedString;
	        
	        while (rs.next()) {
	            formattedString="";
	        	formattedString+=rs.getInt("id")+"\t";
	            formattedString+=rs.getString("movie_name")+" ".repeat(34-rs.getString("movie_name").length())+"\t";
	            formattedString+=rs.getString("lead_actor")+" ".repeat(17-rs.getString("lead_actor").length())+"\t";
	            formattedString+=rs.getString("lead_actress")+" ".repeat(23-rs.getString("lead_actress").length())+"\t"; 
	            formattedString+=rs.getString("year_of_release")+" ".repeat(15-4)+"\t"; 
	            formattedString+=rs.getString("Director");
	            System.out.println(formattedString);
	        }
	    conn.close();
	    }
	    catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	 
  
  
  public void getMoviesOfActor(String actorName){
        String sql = "SELECT * FROM MOVIES WHERE Lead_Actor=?";
        
        try {
        	Connection conn = this.connect();
        
             PreparedStatement pstmt  = conn.prepareStatement(sql);
             pstmt.setString(1, actorName);
             ResultSet rs    = pstmt.executeQuery();
            String formattedString;
            System.out.println("Id\tMovie_name"+" ".repeat(34-10)+"\tLead_Actor"+" ".repeat(17-10)+"\tLead_Actress"+" ".repeat(23-12)+"\tYear_Of_Release\tDirector");
            while (rs.next()) {
            	 formattedString="";
	            	formattedString+=rs.getInt("id")+"\t";
	                formattedString+=rs.getString("movie_name")+" ".repeat(34-rs.getString("movie_name").length())+"\t";
	                formattedString+=rs.getString("lead_actor")+" ".repeat(17-rs.getString("lead_actor").length())+"\t";
	                formattedString+=rs.getString("lead_actress")+" ".repeat(23-rs.getString("lead_actress").length())+"\t"; 
	                formattedString+=rs.getString("year_of_release")+" ".repeat(15-4)+"\t"; 
	                formattedString+=rs.getString("Director");
	                System.out.println(formattedString);
            }
        conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
}


