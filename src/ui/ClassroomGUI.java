package ui;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Classroom;
import model.UserAccount;

public class ClassroomGUI {
	
	@FXML
    private Pane mainPane;
	
	@FXML
	private TextField logInName;

	@FXML
	private PasswordField LogInPass;

	@FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoto;
    
    @FXML
    private ToggleGroup genderOption;

    @FXML
    private RadioButton genderM;
    
    @FXML
    private RadioButton genderF;

    @FXML
    private RadioButton genderO;

    @FXML
    private CheckBox careerSE;

    @FXML
    private CheckBox careerTE;

    @FXML
    private CheckBox careerIE;

    @FXML
    private DatePicker txtBirthday;

    @FXML
    private PasswordField txtPassword;
	
	@FXML
    private ImageView photo;

    @FXML
    private Label userName;
	
	@FXML
    private TableView<UserAccount> tvUsers;

    @FXML
    private TableColumn<UserAccount, String> tcUserName;

    @FXML
    private TableColumn<UserAccount, String> tcGender;

    @FXML
    private TableColumn<UserAccount, String> tcCarrer;

    @FXML
    private TableColumn<UserAccount, String> tcBirthday;

    @FXML
    private TableColumn<UserAccount, String> tcBrowser;
    
    private ObservableList<UserAccount> observableList;
    private Classroom classroom;
    
    @FXML
    public void firstWindow() throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
    	fxmlLoader.setController(this);
    	Parent form1 = fxmlLoader.load();
    	mainPane.getChildren().setAll(form1);
    	
    }
    
    public ClassroomGUI() {
    	classroom = new Classroom();
    }
    
    private void itializeTableView() {
    	observableList = FXCollections.observableArrayList(classroom.getUsers());
    	
    	tvUsers.setItems(observableList);
    	tcUserName.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("username"));
    	tcGender.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("gender"));
    	tcCarrer.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("carrer"));
    	tcBirthday.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("birthday"));
    	tcBrowser.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("browser"));
    }

    @FXML
    void logIn(ActionEvent event) throws IOException {
    	
    	if(logInName.getText().equals("") && LogInPass.getText().equals("")) {
    		notifications("Por favor llene todos los campos");
    	} else {
    		if(classroom.findUser(logInName.getText(), LogInPass.getText())!= -1) {
        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("account-list.fxml"));
            	fxmlLoader.setController(this);
            	Parent window1 = fxmlLoader.load();
            	mainPane.getChildren().setAll(window1);
            	
            	itializeTableView();
        	} else {
        		notifications("Usuario o contraseña incorrectos, intente de nuevo");
        	}
    		
    	}
    	
    	
    	
    }

    @FXML
    void singUp(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
    	fxmlLoader.setController(this);
    	Parent window2 = fxmlLoader.load();
    	mainPane.getChildren().setAll(window2);
    }
    
    @FXML
    void logOut(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
    	fxmlLoader.setController(this);
    	Parent window3 = fxmlLoader.load();
    	mainPane.getChildren().setAll(window3);
    }
    
    @FXML
    public void addUser(ActionEvent event) throws IOException {
    	String name = txtName.getText();
    	String password = txtPassword.getText();
    	String photo = txtPhoto.getText();
    	String gender = "";
    	if(genderM.isSelected() && genderM != null) {
    		gender = "MALE";
    	} else if (genderF.isSelected()) {
    		gender = "FEMALE";
    	} else if (genderO.isSelected()) {
    		gender = "OTHER";
    	} else {
    		gender = "";
    	}
    	String career = "";
    	if (careerSE.isSelected()) {
    		career+= "Software Engineering\n";
    	}if (careerTE.isSelected()) {
    		career+= "Telematic Engineering\n";
    	}if (careerIE.isSelected()) {
    		career+= "Industrial Engineering";
    	}
    	String birthday = "";
    	
    	if(txtBirthday.getValue()!=null) {
    		LocalDate date = txtBirthday.getValue();
    		birthday+=date;
    	}
    	
    	if(name.equals("") || password.equals("") || photo.equals("") || gender.equals("") || career.equals("") || birthday.equals("")) {
    		
        	notifications("Por favor rellene todos los campos");
        	
    	} else {
    		
    		classroom.add(name, password, photo, gender, career, birthday, "Chrome");
    		notifications("Se ha creado la cuenta");
        	logOut(event);
        	
    	}
    	
    	
    	
    }
    
    public void notifications(String a) {
    	JOptionPane.showMessageDialog(null, a);
    }

}

