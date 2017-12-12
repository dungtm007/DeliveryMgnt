package deliverymgnt.config;

import deliverymgnt.controllers.ViewOrderDetailController;
import deliverymgnt.views.FxmlView;

public class ControllerFactory {

	public static Object createController(FxmlView view, Object[] parameters) {
		
		if (view == FxmlView.VIEW_ORDER_DETAILS) {
			ViewOrderDetailController controller = new ViewOrderDetailController();
			controller.setParams(parameters);
			return controller;
		}
		
		return null;
	}
}
