package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class CurrentController {
	private MainController mainController;
	
	@FXML public VBox currentViewVBox;

	public void init(MainController mainController) {
		this.mainController = mainController;
	}

}
