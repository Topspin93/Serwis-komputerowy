package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HistoryController {
	private MainController mainController;
	
	@FXML public VBox historyViewVBox;
	
	public void init(MainController mainController) {
		this.mainController = mainController;
	}
	
}
