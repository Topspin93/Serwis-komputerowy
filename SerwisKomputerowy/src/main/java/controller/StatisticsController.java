package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class StatisticsController {
	private MainController mainController;
	
	@FXML public VBox statisticsViewVBox;
	
	public void init(MainController mainController) {
		this.mainController = mainController;
	}

}
