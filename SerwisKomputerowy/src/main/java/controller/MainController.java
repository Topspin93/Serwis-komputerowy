package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {
	@FXML CurrentController currentViewController;
	@FXML HistoryController historyViewController;
	@FXML AddOrderController addOrderViewController;
	@FXML StatisticsController statisticsViewController;

	private VBox current;

	public void initialize(){
		current = currentViewController.currentViewVBox;
		currentViewController.init(this);
		addOrderViewController.init(this);
	}
	
	public void showCurrent(){
		System.out.println("showCurrent");
		showVBox(currentViewController.currentViewVBox);
	}
	
	public void showAddOrder(){
		System.out.println("showAddOrder");
		showVBox(addOrderViewController.addOrderViewVBox);
	}
	
	public void showHistory(){
		System.out.println("showHistory");
		showVBox(historyViewController.historyViewVBox);
	}
	
	public void showStatistics(){
		System.out.println("showStatistic");
		showVBox(statisticsViewController.statisticsViewVBox);
	}
	
	public void close(){
		System.exit(0);
	}

	private void showVBox(VBox vbox) {
		current.setVisible(false);
		current = vbox;
		current.setVisible(true);
	}
	
//	public void load(){
//		//currentViewController.label.setText(addOrderViewController.getTfOrderNumber().getText());
//		
//	}
}
