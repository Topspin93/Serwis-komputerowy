package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			mainWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
			AnchorPane pane = loader.load();

			// MainWindowController mainWindowController =
			// loader.getController();
			// mainWindowController.setMain(this, primaryStage);

			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(Main.class.getResource("/view/application.css").toExternalForm());
			primaryStage.setTitle("Serwis komputerowy v1.0");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return Main.primaryStage;
	}
}
