package deliverymgnt.schedulers;

import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.deliveryhandlers.DeliveryHandler;
import deliverymgnt.factories.DeliveryHandlerFactory;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;
import deliverymgnt.services.DeliveryBusinessLogic;
import deliverymgnt.services.WarehouseService;

@Component
public class DeliveryManagementScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(DeliveryManagementScheduler.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DeliveryService deliveryService;
    
    @Autowired
    private WarehouseService warehouseService;
    
    @Scheduled(fixedRate = 7000) 
    public void runScheduledDeliveryManagement() {
    	processOrders();
    	processDeliveries();
    	updateDeliveryTracking();
    	updateDeliveryPickupStatus();
    	updateOrderTracking();
    }
    
    public void processOrders() {
    	
    	List<Order> orders = orderService.findByOrderStatus(OrderStatus.Entered);
    	for(Order o : orders) {
    		try {
				DeliveryBusinessLogic.processOrder(o, orderService, warehouseService, deliveryService);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		log.info("Order {} status: {}", o.getId(), o.getOrderStatus());
    	}
    }
    
    public void processDeliveries() {
    	
    	List<Delivery> deliveries = deliveryService.findByDeliveryStatus(DeliveryStatus.Entered);
    	
    	for(Delivery delivery : deliveries) {

			DeliveryMethod method = delivery.getDeliveryMethod();
			DeliveryHandler deliveryHdlr = DeliveryHandlerFactory.getDeliveryHandler(method);
			deliveryHdlr.deliver(delivery, deliveryService, orderService);
			
    		log.info("Delivery {} status: {}", delivery.getId(), delivery.getDeliveryStatus());
    	}
    }
    
    public void updateDeliveryTracking() {
    	
    	List<Delivery> deliveries = deliveryService.findByDeliveryStatus(DeliveryStatus.Delivering);
    	
    	for(Delivery delivery : deliveries) {
    		
			DeliveryMethod method = delivery.getDeliveryMethod();
			DeliveryHandler deliveryHdlr = DeliveryHandlerFactory.getDeliveryHandler(method);
			deliveryHdlr.track(delivery, deliveryService, orderService);
    			
    		log.info("Delivery {} status: {}", delivery.getId(), delivery.getDeliveryStatus());
    	}
    }
    
    public void updateDeliveryPickupStatus() { // specifically for Locker Pickup
       
    }
    
    public void updateOrderTracking() {
    	
    	List<Order> orders = orderService.findByOrderStatus(OrderStatus.Delivering);
    	
    	for(Order o : orders) {
    		
    		boolean orderDelivered = true;
    		boolean orderFinished = true;
    		
			List<Delivery> deliveries = deliveryService.findByOrderId(o.getId());
			for(Delivery d : deliveries) {
				DeliveryStatus status = d.getDeliveryStatus();
				if (status != DeliveryStatus.Delivered && status != DeliveryStatus.Finished) {
					orderDelivered = false;
					orderFinished = false;
					break;
				}
				if (status == DeliveryStatus.Delivered) {
					orderFinished = false;
				}
			}
			if (orderDelivered) {
				o.setOrderStatus(OrderStatus.Delivered);
			}
			if (orderFinished) {
				o.setOrderStatus(OrderStatus.Finished);
			}
			orderService.save(o);
    		log.info("Order {} status: {}", o.getId(), o.getOrderStatus());
    	}
    }

}
