package billfn;
import jdbc.Conn;
import java.util.*;
import java.sql.*;
public class Customer
{
	String ID="";
  public void customerMenu()
  {
	  System.out.println("******************");
	  System.out.println("*1.see bill      *");
	  System.out.println("*2.pay bill      *");
	  System.out.println("*3.change password*");
	  System.out.println("*4.main menu      *");
	  System.out.println("******************");
	  System.out.println("enter your choice");
	  int choice;
	  Scanner sc=new Scanner(System.in);
	  choice=sc.nextInt();
	  switch(choice)
	  {
	  case 1:
		  seeBill();
		  break;
	  case 2:
		  payBill();
		  break;
	  case 3:
		  changePassword();
		  break;
	  case 4:
		  MainMenu mm=new MainMenu();
		  mm.mainmenu();
		  break;
	  }
	  
  }
  
  public void payBill()
  {
      Conn con1=new Conn();
      try
      {
          Statement st = con1.con.createStatement();
          String query = "SELECT UNIT, AMOUNT,STATUS FROM customer where meterNo="+ID;
          ResultSet rs = st.executeQuery(query);
          if(rs.next())
          {
        	 if(rs.getFloat(2)>0)
        	 {
        		 if(rs.getInt(3)==0)
        		 {

        			 System.out.println("Your Unit Conusmed is : "+rs.getInt(1)+" and Respective Bill is : "+rs.getFloat(2));
        			 String upq = "UPDATE customer set Status = "+1+"where meterNo = " +ID;
        			 st.executeUpdate(upq);
        			 System.out.println("Your Bill is marked as paid.");
        		 }
        		 else {
        			 System.out.println("Your Bill is Already Paid.");
        		 }
        	 }
        	 else
        	 {
        		 System.out.println("Your Bill is not yet genrated please ask admin to generate bill.");
        	 }
             
             
          }

          con1.con.close();
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
  }
  
  
  public void seeBill()
  {
      Conn con1=new Conn();
      try
      {
          Statement st = con1.con.createStatement();
          String query = "SELECT * FROM customer where meterNo="+ID;
          ResultSet rs = st.executeQuery(query);
          if(rs.next())
          {
             System.out.println("Hello : "+rs.getString(2)+
            		 			"\nMobile Number : "+rs.getString(4)+
            		 			"\nAddress : "+rs.getString(3)+
            		 			"\nYour Unit Conusmed is : "+rs.getInt(7)
             					+"\nRespective Bill is : "+rs.getFloat(8)
             					+"\nBill status is : "+((rs.getInt(9)==0)?"Unpaid":"Paid")
             					);
             if(rs.getFloat(8)==0.0f)
            	 System.out.println("\nPlease ask Admin to Generate your Bill.");
             if(rs.getInt(9)==0)
            	 System.out.println("\nPlease pay your Bill asap.");
          }
          con1.con.close();
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
  }
  
  public void customerLogin()
  {
	  String id;
      String pwd;
      System.out.println("Enter your meterNo. as your id ");
      Scanner sc=new Scanner(System.in);
      id=sc.next();

      try {
        Conn con1=new Conn();
        String query="select MeterNo,password from Customer where meterNo="+id;
        Statement smtm=con1.con.createStatement();
        ResultSet rs=smtm.executeQuery(query);
        if(rs.next())
        {
        	ID=rs.getString(1);
            System.out.println("enter your password");
            pwd=sc.next();
            if(pwd.equals(rs.getString(2)))
            {
                customerMenu();
            }
            else
            {
                System.out.println("Password INCORRECT...please try again!");
            }
        }
        else
        {
            System.out.println("Please enter the correct meter number...");
        }
        con1.con.close();
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
   }
  
  
  //Change password
  public void changePassword()
  {

      Scanner sc = new Scanner(System.in);
      String oldpass,newpass;
      System.out.println("Please Enter your old password : ");
      oldpass = sc.next();
      Conn con1=new Conn();
      try
      {
          Statement st = con1.con.createStatement();
          String query = "SELECT password FROM customer where meterNo="+ID;
          ResultSet rs = st.executeQuery(query);
          if(rs.next())
          {
              if(oldpass.equals(rs.getString(1)))
              {
                  System.out.println("Enter the new password : ");
                  newpass = sc.next();
                  String upq = "UPDATE customer set password = "+newpass+"where meterNo = " +ID;

                  int res = st.executeUpdate(upq);
                  if(res==1)
                  {
                      System.out.println("Your password is updated successfully...");
                  }
                  else
                  {
                      System.out.println("Password is not updated...please try again");
                  }
              }
              else
              {
                  System.out.println("Your old password is wrong...Please try again");
              }
          }
          else
          {
              System.out.println("Please enter the valid meter number...");
          }
          con1.con.close();
      }
      catch(Exception e)
      {
          System.out.println(e);
      }

  }
}
