package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Customer;
import model.Employee;

public class AddOrderController {
	private MainController mainController;

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	private List<String> customersNameAndSurname = new ArrayList<String>();
	private long OrderNumber;

	final private Image imageOK = new Image(AddOrderController.class.getResourceAsStream("/icons/ok_icon.png"));
	final private Image imageDenied = new Image(AddOrderController.class.getResourceAsStream("/icons/denied_icon.png"));
	final private Image imageEmpty = new Image(AddOrderController.class.getResourceAsStream("/icons/empty_icon.png"));

	@FXML public VBox addOrderViewVBox;
	
	@FXML private DatePicker datepicker;
	@FXML private ComboBox combobox;
	@FXML private TextField tfOrderNumber, tfManufacturer, tfModel, tfSerialNumber, tfSearch, tfName, tfSurname,
			tfTelephone, tfEmail;
	@FXML private TextArea tfDescription;
	@FXML private ImageView ivOrderNumber, ivDatePicker, ivManufacturer, ivModel, ivSerialNumber, ivDescription,
			ivEmployee, ivName, ivSurname, ivTelephone, ivEmail;

	public void init(MainController mainController) {
		this.mainController = mainController;
		
		datepicker.setValue(LocalDate.now());

		entityManagerFactory = Persistence.createEntityManagerFactory("serwisKomputerowy");
		entityManager = entityManagerFactory.createEntityManager();

		loadEmployeesForComboBox();

		loadCustomersForSearch();

		createNewUniqueIDForRepairOrder();

		entityManager.close();
		entityManagerFactory.close();

		addListenersToIcons();
	}

	private void addListenersToIcons() {
		if (tfOrderNumber.getText().matches(String.valueOf(OrderNumber)))
			ivOrderNumber.setImage(imageOK);
		tfOrderNumber.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfOrderNumber.getText().matches(""))
				ivOrderNumber.setImage(imageEmpty);
			else if (tfOrderNumber.getText().matches(String.valueOf(OrderNumber)))
				ivOrderNumber.setImage(imageOK);
			else
				ivOrderNumber.setImage(imageDenied);
		});

		if (datepicker.getValue() != null)
			ivDatePicker.setImage(imageOK);
		datepicker.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (datepicker.getValue() != null)
				ivDatePicker.setImage(imageOK);
			else
				ivDatePicker.setImage(imageDenied);
		});

		tfManufacturer.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfManufacturer.getText().matches(""))
				ivManufacturer.setImage(imageEmpty);
			else if (tfManufacturer.getText().matches("[A-Z¯£][a-z¿Ÿæœê¹ñ³ó]+"))
				ivManufacturer.setImage(imageOK);
			else
				ivManufacturer.setImage(imageDenied);
		});

		tfModel.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfModel.getText().matches(""))
				ivModel.setImage(imageEmpty);
			else if (tfModel.getText() != null)
				ivModel.setImage(imageOK);
		});

		tfSerialNumber.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfSerialNumber.getText().matches(""))
				ivSerialNumber.setImage(imageEmpty);
			else if (tfSerialNumber.getText() != null)
				ivSerialNumber.setImage(imageOK);
			else
				ivSerialNumber.setImage(imageDenied);
		});

		tfDescription.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfDescription.getText().matches(""))
				ivDescription.setImage(imageEmpty);
			else if (tfDescription.getText() != null)
				ivDescription.setImage(imageOK);
			else
				ivDescription.setImage(imageDenied);
		});

		combobox.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (combobox.getValue() != null)
				ivEmployee.setImage(imageOK);
			else
				ivEmployee.setImage(imageEmpty);
		});
		
		tfName.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfName.getText().matches(""))
				ivName.setImage(imageEmpty);
			else if (tfName.getText().matches("[A-Z¯£][a-z¿Ÿæœê¹ñ³ó]+"))
				ivName.setImage(imageOK);
			else
				ivName.setImage(imageDenied);
		});
		
		tfSurname.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfSurname.getText().matches(""))
				ivSurname.setImage(imageEmpty);
			else if (tfSurname.getText().matches("[A-Z¯£][a-z¿Ÿæœê¹ñ³ó]+"))
				ivSurname.setImage(imageOK);
			else
				ivSurname.setImage(imageDenied);
		});
		
		tfTelephone.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfTelephone.getText().matches(""))
				ivTelephone.setImage(imageEmpty);
			else if (tfTelephone.getText().matches("[0-9]+"))
				ivTelephone.setImage(imageOK);
			else
				ivTelephone.setImage(imageDenied);
		});
		
		tfEmail.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfEmail.getText().matches(""))
				ivEmail.setImage(imageEmpty);
			else if (tfEmail.getText().matches("[a-z0-9.]+@[a-z0-9]+\\.[a-z]{2,3}"))
				ivEmail.setImage(imageOK);
			else
				ivEmail.setImage(imageDenied);
		});
	}

	private void createNewUniqueIDForRepairOrder() {
		try {
			TypedQuery<Long> maxRepairOrderId = entityManager.createQuery("SELECT max(r.id) FROM RepairOrder r",
					Long.class);
			OrderNumber = maxRepairOrderId.getSingleResult();
			OrderNumber++;
			tfOrderNumber.setText(String.valueOf(OrderNumber));
		} catch (NullPointerException e) {
			tfOrderNumber.setText("1");
		}
	}

	public void loadCustomersForSearch() {
		customersNameAndSurname.clear();
		TypedQuery<Customer> customersForSearch = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
		List<Customer> customers = customersForSearch.getResultList();

		for (Customer customer : customers) {
			customersNameAndSurname.add(customer.getName() + " " + customer.getSurname());
		}
		TextFields.bindAutoCompletion(tfSearch, customersNameAndSurname);
		tfSearch.textProperty().addListener((obs, oldVal, newVal) -> {
			if (tfSearch.getText().equals("")) {
				tfName.clear();
				tfSurname.clear();
				tfTelephone.clear();
				tfEmail.clear();
			} else {
				for (int i = 0; i < customersNameAndSurname.size(); i++) {
					if (tfSearch.getText().equals(customersNameAndSurname.get(i))) {
						tfName.setText(customers.get(i).getName());
						tfSurname.setText(customers.get(i).getSurname());
						tfTelephone.setText(customers.get(i).getTelephone());
						tfEmail.setText(customers.get(i).getEmail());
					}
				}
			}
		});
	}

	private void loadEmployeesForComboBox() {
		TypedQuery<Employee> employeesForComboBox = entityManager.createQuery("SELECT e FROM Employee e",
				Employee.class);
		List<Employee> employees = employeesForComboBox.getResultList();
		combobox.getItems().add(""); // for not-specified employee
		for (Employee employee : employees) {
			combobox.getItems().add(employee.getName() + " " + employee.getSurname());
		}
	}

	public void saveOrder() {
		System.out.println("button save clicked");

		// if ()
		// mainController.load();
		// EntityManagerFactory entityManagerFactory =
		// Persistence.createEntityManagerFactory("serwisKomputerowy");
		// EntityManager entityManager =
		// entityManagerFactory.createEntityManager();
		//
		// entityManager.close();
		// entityManagerFactory.close();
	}

	public void addNewCustomer(){
		Stage stage = new Stage();
		stage.setTitle("Dodawanie nowego klienta");
		try {
			FXMLLoader loader = new FXMLLoader(AddOrderController.class.getResource("/view/AddCustomerWindow.fxml"));
			VBox vbox = loader.load();
			stage.setScene(new Scene(vbox));
			Stage ownerStage = (Stage) addOrderViewVBox.getScene().getWindow();
			stage.initOwner(ownerStage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editCustomer(){
	}
	
	public void refresh(){
		entityManagerFactory = Persistence.createEntityManagerFactory("serwisKomputerowy");
		entityManager = entityManagerFactory.createEntityManager();

		loadCustomersForSearch();

		entityManager.close();
		entityManagerFactory.close();
	}

	public TextField getTfSearch() {
		return tfSearch;
	}

	public void setTfSearch(TextField tfSearch) {
		this.tfSearch = tfSearch;
	}

}
