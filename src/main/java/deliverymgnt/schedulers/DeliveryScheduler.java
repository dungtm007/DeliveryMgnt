package deliverymgnt.schedulers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;

@Component
public class DeliveryScheduler {

private static final Logger log = LoggerFactory.getLogger(ProcessOrderScheduler.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DeliveryService deliveryService;
    
    @Scheduled(fixedRate = 7000) 
    public void processDeliveries() {
    	
    	List<Delivery> deliveries = deliveryService.findByDeliveryStatus(DeliveryStatus.Entered);
    	for(Delivery d : deliveries) {
    		try {
				// Call delivery handler to process
    			
			} catch (Exception e) {
				e.printStackTrace();
			}
    		log.info("Delivery {} status: {}", d.getId(), d.getDeliveryStatus());
    	}
    }
}
