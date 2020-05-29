package coe528.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author mbcan
 */
public class Bank extends Application {

    /**
     *
     */
    protected ArrayList<Customer> accounts = new ArrayList<>();

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        accounts.add(new Customer("Test", "Test",0));
        Stage window;
        Scene login, account, manager;
        Button loginbutton;
        CheckBox isManager = new CheckBox("Manager");
        
        //Login screen setup
        window = stage;
        window.setTitle("Bank Login");
        
        TextField username = new TextField();
        Label userlabel = new Label("Username:");
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        Label passwordlabel = new Label("Password:");
        password.setPromptText("Password");
        
        loginbutton = new Button("Login");
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets (100,100,100,100));
        layout.getChildren().addAll(userlabel,username,passwordlabel,password,loginbutton, isManager);
        
        //Account screen set up
        Label accountlabel = new Label ("Account");
        Label balancelabel = new Label("Balance: ");
        balancelabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(balancelabel,0.0);
        AnchorPane.setRightAnchor(balancelabel,0.0);
        balancelabel.setAlignment(Pos.CENTER);
        balancelabel.setFont(new Font("Arial", 24));
        accountlabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(accountlabel, 0.0);
        AnchorPane.setRightAnchor(accountlabel, 0.0);
        accountlabel.setAlignment(Pos.CENTER);
        accountlabel.setFont(new Font("Arial", 24));
        VBox accountbox = new VBox(10);
        accountbox.setPadding(new Insets (100,100,100,100));
        Label amount = new Label("Amount:");
        Button deposit = new Button("Deposit");
        deposit.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(deposit,0.0);
        AnchorPane.setRightAnchor(deposit,0.0);
        deposit.setAlignment(Pos.CENTER);
        Button withdraw = new Button("Withdraw");
        withdraw.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(withdraw, 0.0);
        AnchorPane.setRightAnchor(withdraw, 0.0);
        withdraw.setAlignment(Pos.CENTER);
        Button online = new Button("Online Purchase");
        online.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(online, 0.0);
        AnchorPane.setRightAnchor(online, 0.0);
        online.setAlignment(Pos.CENTER);
        TextField amountfield = new TextField();
        amountfield.setPromptText("Amount");
        Label alert = new Label("");
        alert.setFont(new Font("Arial",12));
        alert.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(deposit, 0.0);
        AnchorPane.setRightAnchor(deposit, 0.0);
        alert.setAlignment(Pos.CENTER);
        Button logout = new Button("Logout");
        logout.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(logout, 0.0);
        AnchorPane.setRightAnchor(logout, 0.0);
        logout.setAlignment(Pos.CENTER);
        accountbox.getChildren().addAll(accountlabel,balancelabel, amount, amountfield, deposit, withdraw, online, alert, logout);
        
        login = new Scene(layout, 700, 700);
        window.setScene(login);
        account = new Scene(accountbox, 700, 700);
        VBox managerbox = new VBox(10);
        TextField manuser = new TextField();
        Label manuserlabel = new Label("Username:");
        manuser.setPromptText("Username");
        PasswordField manpassword = new PasswordField();
        Label manpasswordlabel = new Label("Password:");
        manpassword.setPromptText("Password");
        Button addUser = new Button("Add user");
        addUser.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(addUser,0.0);
        AnchorPane.setRightAnchor(addUser,0.0);
        addUser.setAlignment(Pos.CENTER);
        Button deleteUser = new Button("Delete user");
        deleteUser.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(deleteUser, 0.0);
        AnchorPane.setRightAnchor(deleteUser, 0.0);
        deleteUser.setAlignment(Pos.CENTER);
        Button manlogout = new Button("Logout");
        manlogout.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(manlogout, 0.0);
        AnchorPane.setRightAnchor(manlogout, 0.0);
        manlogout.setAlignment(Pos.CENTER);
        Label manactiontarget = new Label();
        manactiontarget.setText("");
        manager = new Scene(managerbox, 700, 700);
        managerbox.setPadding(new Insets (100,100,100,100));
        managerbox.getChildren().addAll(manuserlabel, manuser, manpasswordlabel, manpassword, addUser, deleteUser, manactiontarget, manlogout);
        window.show();
        
        //Warning messages
        Label actiontarget = new Label();
        actiontarget.setText("");
        layout.getChildren().addAll(actiontarget);
        
        //Force positive and double inputs to amountfield only
        amountfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                amountfield.setText(oldValue);
            }
        });
        
        //Saving changes when force closed
        window.setOnCloseRequest((WindowEvent we) -> {
            System.out.println("Force close detected, saving changes.");
            if (!accounts.isEmpty()){
                File users = new File("src/coe528/project/Users");
            File[] listOfUsers = users.listFiles();
            
            for (File listOfUser : listOfUsers){
                if (accounts.get(0).getName().equals(listOfUser.getName())){
                    try {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(listOfUser, false))) {
                            writer.write(accounts.get(0).getPassword()+"\n");
                            writer.write(""+accounts.get(0).getBalance());
                            writer.close();
                        }
                        accounts.removeAll(accounts);
                        System.out.println("Logout successful, flushed user data.");
                        username.setText("");
                        password.setText("");
                        alert.setText("");
                        window.setScene(login);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            }
        });
        
        //Login button handling
        loginbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> { 
            if (password.getText().equals("admin") && 
                username.getText().equals("admin") && isManager.isSelected()){
                isManager.setSelected(false);
                Manager m = Manager.getInstance("admin", "admin");
                window.setScene(manager);
            }
            else if (!isManager.isSelected()){
                File users = new File("src/coe528/project/Users");
                File[] listOfUsers = users.listFiles();
                if (listOfUsers.length == 0){
                    System.out.println("Invalid username/password.");
                    actiontarget.setText("Invalid username/password");
                }
                for (File listOfUser : listOfUsers) {
                    if (username.getText().equals(listOfUser.getName())) {
                        System.out.println("Username found, checking password.\n");
                        actiontarget.setText("");
                        try {
                            BufferedReader read = new BufferedReader(new FileReader(listOfUser));
                            if (password.getText().equals(read.readLine())){
                                System.out.println("Password authenticated, logging in.\n");
                                if (actiontarget.getText().equals("")){
                                    double balance = Double.parseDouble(read.readLine());
                                    System.out.println("Balance has been read.\n");
                                    accounts.removeAll(accounts);
                                    accounts.add(new Customer(username.getText(),password.getText(),balance));
                                    accounts.get(0).changeLevel();
                                    accountlabel.setText(""+accounts.get(0).levelName()+" Account");
                                    balancelabel.setText("Balance: CAD$ " + accounts.get(0).getBalance());
                                    window.setScene(account);
                                }
                            read.close();
                            }
                            else{
                                System.out.println("Invalid username/password.\n");
                                if (actiontarget.getText().equals("")){                                    
                                    actiontarget.setText("Invalid username/password");
                                }                           
                            }
                        }catch (FileNotFoundException ex) {
                            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                        }catch (IOException ex) {
                            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            else{
                System.out.println("Invalid username/password.\n");
                actiontarget.setText("Invalid username/password");
            }
        });
        
        //Account screen handling
        //Deposit
        deposit.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            alert.setText("");
            System.out.println("Registered deposit click, sending event to the account.");
            accounts.get(0).deposit(Double.parseDouble(amountfield.getText()));
            accountlabel.setText(""+accounts.get(0).levelName()+" Account");
            amountfield.setText("");
            balancelabel.setText("Balance: CAD$" + accounts.get(0).getBalance());
            System.out.println("Successful deposit.");
        }); 
        //Withdraw
        withdraw.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Registered withdraw click.");
            alert.setText("");
            boolean withdrawSuccess = accounts.get(0).withdraw(Double.parseDouble(amountfield.getText()));
            if (!withdrawSuccess){               
                alert.setText("Error, insufficient balance.");
            }
            else{
                accountlabel.setText(""+accounts.get(0).levelName()+" Account");
                amountfield.setText("");
                balancelabel.setText("Balance: CAD$" + accounts.get(0).getBalance());                
            }
            System.out.println("Withdrawal successfully handled.");
        }); 
        //Online Shop
        online.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Registered online shop click.");
            boolean onlineSuccess = accounts.get(0).onlineShop(Double.parseDouble(amountfield.getText()));
            if (!onlineSuccess){               
                alert.setText("Error, insufficient balance.");
            }
            else{
                alert.setText("Online purchase successful with applicable service charges.\n");
                accountlabel.setText(""+accounts.get(0).levelName()+" Account");
                amountfield.setText("");
                balancelabel.setText("Balance: CAD$" + accounts.get(0).getBalance());                
            }
            System.out.println("Online shop successfully handled.");
        }); 
        
        //Logout
        logout.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Registered logout click.");
            File users = new File("src/coe528/project/Users");
            File[] listOfUsers = users.listFiles();
            
            for (File listOfUser : listOfUsers){
                if (!accounts.isEmpty()){
                    if (accounts.get(0).getName().equals(listOfUser.getName())){
                        try {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(listOfUser, false))) {
                                writer.write(accounts.get(0).getPassword()+"\n");
                                writer.write(""+accounts.get(0).getBalance());
                                writer.close();
                                System.out.println("User file updated.");
                            }
                            accounts.removeAll(accounts);
                            System.out.println("Logout successful, flushed user data.");
                            username.setText("");
                            password.setText("");
                            alert.setText("");
                            window.setScene(login);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        
        //Manager sreen handling
        //Logout
        manlogout.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Registered logout click.");
            accounts.removeAll(accounts);
            username.setText("");
            password.setText("");
            System.out.println("Successfully logged out as admin.\n");
            window.setScene(login);
        });
        
        //Add user
        addUser.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Registered add user click.");
            if (!(manuser.getText() == null || manuser.getText().trim().isEmpty()) && !(manpassword.getText() == null || manpassword.getText().trim().isEmpty())){
                try {
                    File users = new File("src/coe528/project/Users/"+manuser.getText());
                    if (users.createNewFile()){
                        users.getParentFile().mkdirs();
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(users, false))) {
                            writer.append(manpassword.getText()+"\n");
                            writer.append(""+100.00);
                            writer.close();
                        }
                        manuser.setText("");
                        manpassword.setText("");
                        System.out.println("Created new user.");
                        manactiontarget.setText("");
                    }
                    else{
                        System.out.println("Error, user already exists.\n");
                        manactiontarget.setText("Error, user already exists.");
                        manuser.setText("");
                        manpassword.setText("");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                System.out.println("Error, one or more fields are blank.\n");
                manactiontarget.setText("Error, one or more fields are blank.");
            }
        });
        
        //Delete user
        deleteUser.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.out.println("Registered delete user click.");
            accounts.removeAll(accounts);
            File users = new File("src/coe528/project/Users");
            File[] listOfUsers = users.listFiles();
            boolean deleted = false;
            for (File listOfUser:listOfUsers){
                if (manuser.getText().equals(listOfUser.getName())){
                    listOfUser.delete();
                    deleted = true;
                    System.out.println("Deleted user.\n");
                    manactiontarget.setText("Successfully deleted user.");
                    manuser.setText("");
                    manpassword.setText("");
                    if (listOfUser.exists()){
                        System.out.println("Error, deleting file.");
                    }
                }
            }
            if (!deleted){
                System.out.println("Could not find requested user.\n");
                manactiontarget.setText("Could not delete requested user.\n");
                manuser.setText("");
                manpassword.setText("");
            }
        });
    }
}