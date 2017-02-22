package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AddOrderController {
private MainController mainController;
	
	@FXML public VBox addOrderViewVBox;

	public void init(MainController mainController) {
		this.mainController = mainController;
	}
}
