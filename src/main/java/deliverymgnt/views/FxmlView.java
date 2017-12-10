package deliverymgnt.views;

import java.util.ResourceBundle;

public enum FxmlView {
	
	USER {

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
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
    ROOT_LAYOUT_VIEW {
    	@Override
		public String getTitle() {
			return "Root Layout";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RootLayout.fxml";
		}
    },
    VIEW_ORDER_DETAILS {
    	
    	@Override
		public String getTitle() {
			return getStringFromResourceBundle("view_order_details.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ViewOrderDetails.fxml";
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
    }
	;
	
	public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
