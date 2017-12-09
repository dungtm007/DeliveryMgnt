package deliverymgnt.schedulers;

import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;
import deliverymgnt.services.PackagingBusiness;

@Component
public class ProcessOrderScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(ProcessOrderScheduler.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DeliveryService deliveryService;
    
    @Scheduled(fixedRate = 4000) 
    public void processOrders() {
    	
    	List<Order> orders = orderService.findByOrderStatus(OrderStatus.Entered);
    	for(Order o : orders) {
    		try {
				PackagingBusiness.ProcessOrder(o, deliveryService, orderService);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		log.info("Order {} status: {}", o.getId(), o.getOrderStatus());
    	}
    }
}
