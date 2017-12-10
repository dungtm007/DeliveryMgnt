package deliverymgnt.config;

import deliverymgnt.controllers.ViewOrderDetailsController;
import deliverymgnt.views.FxmlView;

public class ControllerFactory {

	public static Object createController(FxmlView view, Object[] parameters) {
		
		
		if (view == FxmlView.VIEW_ORDER_DETAILS) {
			ViewOrderDetailsController controller = new ViewOrderDetailsController();
			controller.setParams(parameters);
			return controller;
		}
		
		return null;
	}
}
