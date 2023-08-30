package billfn;
import java.util.*;
import java.sql.*;
import jdbc.Conn;
class Admin{

    public void adminLogIn(){
        String uname="admin", pwdhd="admin";
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the Username : ");
        String adminuname = sc.nextLine();
        System.out.println("Enter the Password : ");
        String pwd = sc.nextLine();
       
        if(adminuname.equals(uname)&&pwdhd.equals(pwd))
        {
            System.out.println("Admin logged in Successfully...");
            adminmenu();
        }
        else
        {
            System.out.println("Please enter the valid credentials...");
        }

    }
    
    // add data into database    
    public void addCustomer(){
        Conn con1=new Conn();
        String name, address,type , mobile;
        String meterno;
        Scanner sc= new Scanner(System.in);
        
        System.out.print("Customer Name : ");
        name=sc.nextLine();

        System.out.print("Meter No. : ");
        meterno=sc.next();

        System.out.print("Address : ");
        address=sc.next();

        System.out.print("Contact : ");
        mobile=sc.next();

        System.out.print("Connection Type : (C-Commertial/D-Domestic)");
        type=sc.next();
        float unit=0.0f;
//        Random random = new Random();
//        if(type.equals("c")){
//        	int randomNumber = random.nextInt(2001);
//            unit=randomNumber;
//        }
//        else{
//        	int randomNumber = random.nextInt(501);
//            unit=randomNumber;
//        }
        try{
        	String query =  "INSERT INTO Customer (meterNo,name, address, contact,password,usertype,unit)"+" VALUES (?,?,?,?,?,?,?)";
        	//Connection con;
        	PreparedStatement ps=con1.con.prepareStatement(query);
        	ps.setString(1, meterno);
        	ps.setString(2, name);
        	ps.setString(3, address);
        	ps.setString(4, mobile);
        	ps.setString(5, mobile);
        	ps.setString(6, type);
        	ps.setFloat(7, unit);
        	ps.execute();
        	System.out.println("Customer Register Sucessfully...!");
        	//Statement smtm=con.createStatement();
        	//smtm.executeUpdate(query);
        	con1.con.close();
        }catch(Exception e){
        	System.out.println("Connection "+e.getMessage());
        }
    }


    // generating bill    
    public void generateBill(){
        System.out.println("enter meter id");
        String id;
        Scanner sc=new Scanner(System.in);
        id = sc.next();
        Conn con1=new Conn();
        String query="select * from Customer where meterNo="+id;
        float unitConsumed=0.0f;
        String userType="";

        try{
        	Statement stmt = con1.con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	if(rs.next()){
        		userType= rs.getString(6);
        		//unitConsumed= rs.getFloat(7);
        	     Random random = new Random();
        	      if(userType.equals("c")){
        	      	int randomNumber = random.nextInt(2001);
        	          unitConsumed=randomNumber;
        	      }
        	      else{
        	      	int randomNumber = random.nextInt(501);
        	          unitConsumed=randomNumber;
        	      }
              	query="update Customer set Unit="+unitConsumed+"where meterNo="+id;
            	int res=stmt.executeUpdate(query);
        	}
        	float totalBill = 0.0f;
        	if(userType.equals("c")){
        		if(unitConsumed >=0 && unitConsumed <= 300){
        			totalBill = unitConsumed * 15;
        		}
        		else if(unitConsumed >300 && unitConsumed <= 1000){
        			totalBill = unitConsumed * 13;
        		}
        		else{
        			totalBill = unitConsumed * 11;
        		}
        	}
        	else{
        		if(unitConsumed >=0 && unitConsumed <= 100){
        			totalBill = unitConsumed * 10;
        		}
        		else if(unitConsumed >100 && unitConsumed <= 300){
        			totalBill = unitConsumed * 9;
        		}
        		else{
        			totalBill = unitConsumed * 8;
        		}
        	}
        	//System.out.println("total bill "+totalBill);
        	query="update Customer set Amount="+totalBill+"where meterNo="+id;
        	int res=stmt.executeUpdate(query);
        	System.out.println(res+" row updated");
        	con1.con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    
    // remove customer
    public void removeCustomer(){

        System.out.println("enter meter id");
        String id;
        Scanner sc = new Scanner(System.in);
        id = sc.next();
        Conn con1=new Conn();
        try{
            Statement stmt = con1.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MeterNo FROM Customer WHERE MeterNo = " + id);
            if(rs.next()){    
            	String query = "delete from Customer WHERE MeterNo = " + id;
                int q=stmt.executeUpdate(query);
                //String query1 = "delete from bill WHERE MeterNo = " + id;
                //int q1=stmt.executeUpdate(query1);

                if(q>0){
                    System.out.print("Record of that user has be delete successfully");
                }
                else{
                    System.out.print("Some error  while deleting the user");
                }
            }
            else{
            	System.out.print("User is not present");
            }
        }
        catch(Exception e){
            System.out.print(e);
        }


    }

    
    // show customer
    public void showCustomer(){
          try{
               Conn con1= new Conn();

                Statement stmt =con1.con.createStatement();

                ResultSet rs = stmt.executeQuery("Select * from Customer");
                System.out.println("MeterNo | Name | Address | Contact | Password | UserType | UnitConsumed | TotalBill | BillStatus");
                while(rs.next()){
                	System.out.println(rs.getString(1)+"\t|"+
                					   rs.getString(2)+"\t|"+
                					   rs.getString(3)+"\t|"+
                					   rs.getString(4)+"\t|"+
                					   rs.getString(5)+"\t|"+
                					   rs.getString(6)+"\t|"+
                					   rs.getInt(7)+"\t|"+
                					   rs.getFloat(8)+"\t|"+
                					   rs.getInt(9));
                }
                con1.con.close();
            }
            catch(Exception e){
                System.out.println(e);
            }

    }


    // menu for admin
    public void adminmenu()
    {
        System.out.println("*************************");
        System.out.println("*1.Add customer         *");
        System.out.println("*2.remove customer      *");
        System.out.println("*3.Generate bill        *");
        System.out.println("*4.show customers       *");
        System.out.println("*5.logout               *");
        System.out.println("*************************");
        System.out.println("please enter your choice");
        Scanner sc= new Scanner(System.in);
        int choice;
        choice = sc.nextInt();
        switch(choice){
        	case 1:
        		addCustomer();
        		break;
        	case 2:
        		removeCustomer();
        		break;
        	case 3:
        		generateBill();
        		break;
        	case 4:
        		showCustomer();
        		break;
        	case 5:
        		MainMenu mm1 = new MainMenu();
        		mm1.mainmenu();
        }
        
    }
}



class MainMenu{
    public void mainmenu(){
        int choice;
        do {
        	System.out.println("**********************************************************************************");
        	System.out.println("*                                                                                *");
        	System.out.println("*                       Amdocs ELECTRICITY BILL MANAGEMENT                       *");
        	System.out.println("*                                                                                *");
        	System.out.println("**********************************************************************************");
        	System.out.println("\nPlease select accordingly....\n");
        	System.out.println("1.Customer Login.");
        	System.out.println("2.Admin Login.");
        	System.out.println("3.Exit ");
        	Scanner sc=new Scanner(System.in);
        	choice = sc.nextInt();
        	switch(choice){
        		case 1:System.out.println("Customer Login/Signup");
        			Customer c = new Customer();
        			c.customerLogin();
        			break;
        		case 2:System.out.println("Admin Login");
                	Admin admin = new Admin();
                	admin.adminLogIn();
                	break;
        		case 3:System.out.println(" Exit");
            		System.exit(choice);
            		break;
        	}
        }while(choice!=3);
    }
}

 

public class EbillSystem{

    public static void main(String[] args){
        // TODO Auto-generated method stub
    	MainMenu mm = new MainMenu();
        mm.mainmenu();
    }
}