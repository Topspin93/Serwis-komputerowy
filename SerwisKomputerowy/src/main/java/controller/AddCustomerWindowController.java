package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

public class AddCustomerWindowController {
	@FXML final private Image imageOK = new Image(
			AddCustomerWindowController.class.getResourceAsStream("/icons/ok_icon.png"));
	@FXML final private Image imageDenied = new Image(
			AddCustomerWindowController.class.getResourceAsStream("/icons/denied_icon.png"));
	@FXML final private Image imageEmpty = new Image(
			AddCustomerWindowController.class.getResourceAsStream("/icons/empty_icon.png"));

	private BooleanProperty buttonDisableByName = new SimpleBooleanProperty(true);
	private BooleanProperty buttonDisableBySurname = new SimpleBooleanProperty(true);
	private BooleanProperty buttonDisableByTelephone = new SimpleBooleanProperty(true);
	private BooleanProperty buttonDisableByEmail = new SimpleBooleanProperty(false);

	@FXML private TextField tfName, tfSurname, tfTelephone, tfEmail;
	@FXML private ImageView ivName, ivSurname, ivTelephone, ivEmail;
	@FXML private Button buttonAdd;
	@FXML private VBox addCustomerWindowVBox;

	public void initialize() {
		tfName.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfName.getText().matches("")) {
				ivName.setImage(imageEmpty);
				buttonDisableByName.set(true);
			} else if (tfName.getText().matches("[A-Z¯£][a-z¿Ÿæœê¹ñ³ó]+")) {
				ivName.setImage(imageOK);
				buttonDisableByName.set(false);
			} else {
				ivName.setImage(imageDenied);
				buttonDisableByName.set(true);
			}
		});

		tfSurname.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfSurname.getText().matches("")) {
				ivSurname.setImage(imageEmpty);
				buttonDisableBySurname.set(true);
			} else if (tfSurname.getText().matches("[A-Z¯£][a-z¿Ÿæœê¹ñ³ó]+")) {
				ivSurname.setImage(imageOK);
				buttonDisableBySurname.set(false);
			} else {
				ivSurname.setImage(imageDenied);
				buttonDisableBySurname.set(true);
			}
		});

		tfTelephone.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfTelephone.getText().matches("")) {
				ivTelephone.setImage(imageEmpty);
				buttonDisableByTelephone.set(true);
			} else if (tfTelephone.getText().matches("[0-9]{9}")) {
				ivTelephone.setImage(imageOK);
				buttonDisableByTelephone.set(false);
			} else {
				ivTelephone.setImage(imageDenied);
				buttonDisableByTelephone.set(true);
			}
		});

		tfEmail.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfEmail.getText().matches("")) {
				ivEmail.setImage(imageEmpty);
				buttonDisableByEmail.set(false);
			} else if (tfEmail.getText().matches("[a-z0-9.]+@[a-z0-9]+\\.[a-z]{2,3}")) {
				ivEmail.setImage(imageOK);
				buttonDisableByEmail.set(false);
			} else {
				ivEmail.setImage(imageDenied);
				buttonDisableByEmail.set(true);
			}
		});

		// enabling addButton
		buttonAdd.disableProperty().bind(buttonDisableByName.booleanProperty(buttonDisableByName)
				.or(buttonDisableBySurname).or(buttonDisableByTelephone).or(buttonDisableByEmail));
	}

	public void add() {
		Customer customer = new Customer();
		customer.setName(tfName.getText());
		customer.setSurname(tfSurname.getText());
		customer.setTelephone(tfTelephone.getText());
		customer.setEmail(tfEmail.getText());

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("serwisKomputerowy");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(customer);
		entityManager.getTransaction().commit();

		entityManager.close();
		entityManagerFactory.close();

		Stage addCustomerWindow = (Stage) addCustomerWindowVBox.getScene().getWindow();
		addCustomerWindow.close();
		// try {
		// addOrderController.getTfSearch().setText(tfName.getText());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(addOrderController.getTfSearch().getText());
	}

}
