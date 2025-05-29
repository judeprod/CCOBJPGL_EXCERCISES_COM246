package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable {
    ObservableList<User> mylist = FXCollections.observableArrayList();

    @FXML
    Label usernamelabel;

    @FXML
    private TableColumn<User, String> accolumn;

    @FXML
    private Button createbtn;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<User, String> passwordcolumn;

    @FXML
    private TableColumn<User, String> statuscolumn;

    @FXML
    private Button update;

    @FXML
    private ChoiceBox<String> statuscb;

    @FXML
    private TableColumn<User, String> usernamecolumn;

    @FXML
    private TableView<User> viewtable;

    @FXML
    private Label welcome;
    @FXML
    TextField usernametextfield;

    @FXML
    TextField passwordtextfield;

    @FXML
    TextField statustextfield;

  

    String filename = "accounts.txt";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
// TODO Auto-generated method stub
        String username = LoginController.user.getUsername();
        usernamelabel.setText(username);

        initializeCol();
        loadData();
        statuscb.getItems().addAll("Active", "Inactive");

        viewtable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null){
                usernametextfield.setText(newSelection.getUsername());
                passwordtextfield.setText(newSelection.getPassword());
                statuscb.setValue(newSelection.getAccountstatus());
            }
        });    
        
    } 

    private void initializeCol(){
        usernamecolumn.setCellValueFactory( new PropertyValueFactory<>("username"));
        passwordcolumn.setCellValueFactory( new PropertyValueFactory<>("password"));
        accolumn.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("accountstatus"));
    }

    private void loadData(){

        mylist.clear();

        try {
            // Create object from File class
            File myFile = new File("accounts.txt");

            // .exists() method checks if a file exists in the pathname
            if (myFile.exists()) {

                Scanner filescanner = new Scanner(myFile);

                while (filescanner.hasNextLine()) {

                    String data = filescanner.nextLine();
        
                    String username = data.split(",")[0];
                    String password = data.split(",")[1];
                    String dcreated = data.split(",")[2];
                    String status = data.split(",")[3];

                    mylist.add(new User(username, password, dcreated, status));
                } 
                viewtable.setItems(mylist);

                filescanner.close();
            }
            else {
                System.out.println(myFile.getName() + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("There is an error");
        } 

    }
    @FXML
    private boolean createuser(ActionEvent event) {

        String username = usernametextfield.getText();

        String password = passwordtextfield.getText();

        String status = statuscb.getValue();

        System.out.println(status);
        username = username.trim();
        password = password.trim();
        status= status.trim();

        if(username.length()==0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no username provided");

        }

        if(password.length()==0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no password provided");
        }

        LocalDate today =LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        String formattedDate = today.format(formatter);

        User user= new User(username, password, formattedDate, status);

        try{

            BufferedWriter myWriter = new BufferedWriter(new FileWriter("accounts.txt", true));
        myWriter.newLine();
        myWriter.write(user.getUsername() + "," + user.getPassword() + "," + user.getAccountcreated()+ ","+ user.getAccountstatus());
        
        myWriter.close();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Account Created");
        alert.setContentText("You have created a new account!");
        alert.showAndWait();

        loadData();

        } catch (IOException e ){
            System.out.println("An error occured.");
        }
        return true;
    }
    @FXML

    public boolean deleteuser(ActionEvent event) {
        User user = viewtable.getSelectionModel().getSelectedItem();

        String username = (user.getUsername());

        System.out.println(username);
        
        String filename = "accounts.txt";
        String usertoDelete = username;

        List<String> updatedLines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String[] parts = line.split(",");
                    if(!parts[0].equalsIgnoreCase(usertoDelete)) {
                        updatedLines.add(line);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for(int i = 0; i < updatedLines.size(); i++) {
                writer.write(updatedLines.get(i));
                if(i < updatedLines.size() - 1);{
                writer.newLine();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText("This is the header");
    alert.setContentText("User '"+ usertoDelete + "' has been deleted (if existed).");
    alert.showAndWait();
    loadData();

    return true;
}
@FXML
public boolean updateuser(ActionEvent event) {

    User user = viewtable.getSelectionModel().getSelectedItem();

    String username = usernametextfield.getText();

    String password = passwordtextfield.getText();

    String status = statuscb.getValue();

    username = username.trim();
    password = password.trim();
    status = status.trim();

    if(username.length() == 0)
    {
        System.out.println("No username!");
        return false;
    }

    if(password.length() == 0)
    {
        System.out.println("No password!");
        return false;
    }

    String targetUsername = user.getUsername();
    String newPassword = password;
    String newStatus = status;

    List<String> updatedLines = new ArrayList<>();

    // Step 1: Read and update
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(",");

                if (parts.length == 4 && parts[0].equalsIgnoreCase(targetUsername)) {
                    updatedLines.add(usernametextfield.getText() + "," + newPassword + "," + user.getAccountcreated() + "," + newStatus);
                } else {
                    updatedLines.add(line);
                }

                // if (parts.length == 2 && parts[0].equalsIgnoreCase(targetUsername)) {
                //     // Update password
                //     updatedLines.add(parts[0] + "," + newPassword + "," + newStatus);
                // } else {
                //     updatedLines.add(line);
                }
            }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    // Step 2: Write updated lines back
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (int i = 0; i < updatedLines.size(); i++) {
            writer.write(updatedLines.get(i));
            if (i < updatedLines.size() - 1) {
                writer.newLine(); // no extra blank line
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText("This is the header");
    alert.setContentText("User '" + targetUsername + "' has been updated.");
    alert.showAndWait();
    loadData();
    return true;
}

}


