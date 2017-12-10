package deliverymgnt.schedulers;

import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;
import deliverymgnt.services.PackagingBusiness;

@Component
public class TrackOrderScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(TrackOrderScheduler.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DeliveryService deliveryService;
    
    @Scheduled(fixedRate = 12000) 
    public void trackOrders() {
    	
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
