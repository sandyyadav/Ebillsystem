package jdbc;
import java.sql.*;
public class Conn {
	public
	Connection con;
   // Statement stmt;
	public Conn(){
	    
	try{  

	    //step1 load the driver class
	    Class.forName("oracle.jdbc.driver.OracleDriver");


	    //step2 create  the connection object  
	    con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","12345");  

	    }catch(Exception e){ System.out.println(e);}  

	    }
}
