package deliverymgnt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.sql.ordering.antlr.Factory;

import deliverymgnt.domainclasses.Box;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryOption;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Drone;
import deliverymgnt.domainclasses.Locker;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.PackageLockerPlacement;
import deliverymgnt.domainclasses.PackageLockerPlacementStatus;
import deliverymgnt.domainclasses.PackageSize;
import deliverymgnt.domainclasses.Product;
import deliverymgnt.domainclasses.ReservedDrone;
import deliverymgnt.domainclasses.ReservedDroneStatus;
import javassist.bytecode.LineNumberAttribute.Pc;

public class TestDatabaseOperation {
	
	public static void main(String[] args) {
		
		String jdbcurl = "jdbc:postgresql://localhost:5432/DeliveryDB";
		String user = "postgres";
		String password = "123456";
		
//		try {
//		
//			Connection myConn = 
//					DriverManager.getConnection(jdbcurl, user, password);
//			
//			System.out.print("Connection successful!!!");
//		}
//		catch(Exception exc)
//		{
//			System.out.print(exc.getMessage());
//		}
		
		SessionFactory sessionFactory = new Configuration()
										.configure()
										.addAnnotatedClass(Box.class)
										.addAnnotatedClass(Locker.class)
										.addAnnotatedClass(Package.class)
										.addAnnotatedClass(Customer.class)
										.addAnnotatedClass(Order.class)
										.addAnnotatedClass(OrderItem.class)
										.addAnnotatedClass(Product.class)
										.addAnnotatedClass(Package.class)
										.addAnnotatedClass(Delivery.class)
										.addAnnotatedClass(Drone.class)
										.addAnnotatedClass(PackageLockerPlacement.class)
										.addAnnotatedClass(ReservedDrone.class)
										.buildSessionFactory(); 
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			
			Locker locker = new Locker("123 John Kenneth, Drive street", 3, 4, 2);
			Box box = new Box(PackageSize.Medium, true, locker); 
			Customer cust = new Customer("Manh Dung", "Tran", "1000 N 4th street, 52557");
			Order ord = new Order(new Date(), DeliveryOption.HomeDelivery, new Date(), DeliveryType.LockerPickupDelivery, "Some address" , OrderStatus.Entered, cust);
			deliverymgnt.domainclasses.Package pkg1  = new deliverymgnt.domainclasses.Package(PackageSize.Large, ord);
			deliverymgnt.domainclasses.Package pkg2  = new deliverymgnt.domainclasses.Package(PackageSize.Small, ord);
			HashSet<Package> packages = new HashSet<>();
			packages.add(pkg1);
			packages.add(pkg2);
			Delivery delivery = new Delivery(DeliveryStatus.Delivering, "562 Evergreen street", 43, DeliveryType.HomeDelivery, ord, packages);
			Drone drone = new Drone("DRO001", "Sky Viper v2450FPV", true);
			Product prod = new Product("Striped shirt", 2, 15, 3, 1.5, 7.3);
			OrderItem oi = new OrderItem(prod, 3, prod.getPrice() * 1.05, ord);
			PackageLockerPlacement pkgPlacement = new PackageLockerPlacement(new Date(), new Date(), "C73FD", PackageLockerPlacementStatus.OnTheWay, box, pkg2);
			ReservedDrone resDrone = new ReservedDrone(drone, delivery, new Date(), new Date(), ReservedDroneStatus.InUse); 
			
			session.beginTransaction();
			session.save(locker);
			session.save(box);
			session.save(cust);
			session.save(ord);
			session.save(pkg1);
			session.save(pkg2);
			session.save(delivery);
			session.save(drone);
			session.save(prod);
			session.save(oi);
			session.save(resDrone);
			session.save(pkgPlacement);
			
			session.getTransaction().commit();
			
			System.out.println("Saved successfully!");
		}
		catch(Exception exc)
		{
			System.out.print(exc.getMessage());
		}
		finally {
			sessionFactory.close();
		}
		
	}
}
