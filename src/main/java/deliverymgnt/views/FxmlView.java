package deliverymgnt.views;

import java.util.ResourceBundle;

public enum FxmlView {
	
	CUSTOMER {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("customer.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/CustomerLayout.fxml";
		}
	},
	MANAGER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("manager.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ManagerLayout2.fxml";
		}
	},
	LOGIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
	CREATE_ORDER {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("create_order.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/CreateOrderLayout.fxml";
		}
    		
    },
	VIEW_ORDER {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("view_order.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/OrderLayoutCustomer.fxml";
		}
    		
    },
	MANAGE_ORDER {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("manage_order.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/OrderLayoutManager.fxml";
		}
    },
	ORDERS_LIST_MNGR {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("manage_order.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/OrdersList2.fxml";
		}
    		
    },
    VIEW_ORDER_DETAILS {
    	
    	@Override
		public String getTitle() {
			return getStringFromResourceBundle("view_order_details.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ViewOrderDetail2.fxml";
		}
    },
    DELIVERY_COST_REPORT {
    	
    	@Override
		public String getTitle() {
			return getStringFromResourceBundle("delivery_cost_report.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/DeliveryCostReport.fxml";
		}
    },
    MANAGE_DRONE {
    	
    	@Override
		public String getTitle() {
			return getStringFromResourceBundle("manage_drone.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/DroneLayoutManager.fxml";
		}
    },
    MANAGE_LOCKER {
    	
    	@Override
		public String getTitle() {
			return getStringFromResourceBundle("manage_locker.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/LockerLayoutManager.fxml";
		}
    },
    ROOT {
    	
    	@Override
		public String getTitle() {
			return getStringFromResourceBundle("root.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RootLayout.fxml";
		}
    }
	;
	
	public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
