import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {
	
	public static void main(String[] args) {
		
		//jdbc:postgresql://localhost:5432/DeliveryDB
		//postgres
		//123456
		
		String jdbcurl = "jdbc:postgresql://localhost:5432/DeliveryDB";
		String user = "postgres";
		String password = "123456";
		
		try {
		
			Connection myConn = 
					DriverManager.getConnection(jdbcurl, user, password);
			
			System.out.print("Connection successful!!!");
		}
		catch(Exception exc)
		{
			System.out.print(exc.getMessage());
		}
		
		
		
	}
}
