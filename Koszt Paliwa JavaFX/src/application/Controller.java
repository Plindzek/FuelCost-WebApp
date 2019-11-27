package application;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller implements Initializable{
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane root;
    @FXML
    private Button solveButton, loadButton, saveButton, exitButton;
    @FXML
    private Label title, title1, lpgOn100kmDesc, lpgPriceDesc, kmOnLPGDesc, pb95On100kmDesc, pb95PriceDesc,
	kmOnPB95Desc, solutionDesc;
    @FXML
   private TextField lpgOn100km, lpgPrice, kmOnLPG, pb95On100km, pb95Price, kmOnPB95, solution;
   @FXML 
   private double dlpgOn100km, dlpgPrice, dkmOnLPG, dpb95On100km, dpb95Price, dkmOnPB95;
   
 
public
   void initialize(URL location, ResourceBundle resources) {
       assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'MainView.fxml'.";
       assert title1 != null : "fx:id=\"title1\" was not injected: check your FXML file 'MainView.fxml'.";
       assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'MainView.fxml'.";
       assert solveButton != null : "fx:id=\"solveButton\" was not injected: check your FXML file 'MainView.fxml'.";
       assert loadButton != null : "fx:id=\"loadButton\" was not injected: check your FXML file 'MainView.fxml'.";
       assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'MainView.fxml'.";
       assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'MainView.fxml'.";
       assert lpgOn100km != null : "fx:id=\"lpgOn100km\" was not injected: check your FXML file 'MainView.fxml'.";
       assert lpgPrice != null : "fx:id=\"lpgPrice\" was not injected: check your FXML file 'MainView.fxml'.";
       assert kmOnLPG != null : "fx:id=\"kmOnLPG\" was not injected: check your FXML file 'MainView.fxml'.";
       assert pb95On100km != null : "fx:id=\"pb95On100km\" was not injected: check your FXML file 'MainView.fxml'.";
       assert pb95Price != null : "fx:id=\"pb95Price\" was not injected: check your FXML file 'MainView.fxml'.";
       assert kmOnPB95 != null : "fx:id=\"kmOnPB95\" was not injected: check your FXML file 'MainView.fxml'.";
       assert lpgOn100kmDesc != null : "fx:id=\"lpgOn100kmDesc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert lpgPriceDesc != null : "fx:id=\"lpgPriceDesc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert kmOnLPGDesc != null : "fx:id=\"kmOnLPGDesc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert pb95On100kmDesc != null : "fx:id=\"pb95On100kmDesc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert pb95PriceDesc != null : "fx:id=\"pb95PriceDesc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert kmOnPB95Desc != null : "fx:id=\"kmOnPB95Desc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert solutionDesc != null : "fx:id=\"solutionDesc\" was not injected: check your FXML file 'MainView.fxml'.";
       assert solution != null : "fx:id=\"solution\" was not injected: check your FXML file 'MainView.fxml'.";
   }
   
   @FXML
   void solveButtonAction() {
       parseInput();
       fuelCost();
   }
   
   @FXML
   void loadButtonAction() {
	try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
		"Adam", "1234"); PreparedStatement upTable = conn.prepareStatement("SELECT*FROM ADAM.CARDATA")) {
	ResultSet result = upTable.executeQuery();

	while (result.next()) {
		lpgOn100km.setText("" + result.getDouble("DLPGON100KM"));
		lpgPrice.setText("" + result.getDouble("DLPGPRICE"));
		kmOnLPG.setText("" + result.getDouble("DKMONLPG"));
		pb95On100km.setText("" + result.getDouble("DPB95ON100KM"));
		pb95Price.setText("" + result.getDouble("DPB95PRICE"));
		kmOnPB95.setText("" + result.getDouble("DKMONPB95"));

	}
	title.setText("Wczytano ostatnio zapisane dane");
}

catch (SQLException ole) {
	System.out.println(ole.getMessage());
	title.setText("Brak rekord�w, najpierw wykonaj zapis danych!");
}
   }
   @FXML
   void saveButtonAction() {
       saveToDB();
       saveToTxt();
   }
   @FXML
   void exitButtonAction() {System.exit(0);}
   @FXML
   private void parseInput() {// zamiana danych wprowadzonych przez usera na warto�ci double + obs�uga formatu
       	title1.setText("");
	title.setText(" ");
       try {
	   dlpgOn100km = Double.parseDouble(lpgOn100km.getText());
	} catch (NumberFormatException ww) {
		title.setText("B��dny format danych! Wprowad� ponownie:");
		lpgOn100km.setText("0.00");
	}
	try {
		dlpgPrice = Double.parseDouble(lpgPrice.getText());
	} catch (NumberFormatException ww) {
		title.setText("B��dny format danych! Wprowad� ponownie:");
		lpgPrice.setText("0.00");
	}
	try {
		dkmOnLPG = Double.parseDouble(kmOnLPG.getText());
	} catch (NumberFormatException ww) {
		title.setText("B��dny format danych! Wprowad� ponownie:");
		kmOnLPG.setText("0.00");
	}
	try {
		dpb95On100km = Double.parseDouble(pb95On100km.getText());
	} catch (NumberFormatException ww) {
		title.setText("B��dny format danych! Wprowad� ponownie:");
		pb95On100km.setText("0.00");
	}
	try {
		dpb95Price = Double.parseDouble(pb95Price.getText());
	} catch (NumberFormatException ww) {
		title.setText("B��dny format danych! Wprowad� ponownie:");
		pb95Price.setText("0.00");
	}
	try {
		dkmOnPB95 = Double.parseDouble(kmOnPB95.getText());
	} catch (NumberFormatException ww) {
		title.setText("B��dny format danych! Wprowad� ponownie:");
		kmOnPB95.setText("0.00");
	}

}

void fuelCost() {// obliczanie wyniku
	Double result = (Math.round(
			(dlpgOn100km * dlpgPrice * dkmOnLPG / 100 + dpb95On100km * dpb95Price * dkmOnPB95 / 100) * 1000.0))
			/ 1000.0;
	solution.setText("" + result);
	title.setText("Wykonano obliczenia. Wprowad� nowe dane:");
}

private void saveToTxt() {// zapis do pliku txt
	Path plik = Paths.get("Dane.txt");
	try (BufferedWriter pw1 = Files.newBufferedWriter(plik, StandardCharsets.UTF_16);
	// PrintWriter pw = new PrintWriter(new BufferedWriter(new
	// FileWriter("Dane.txt")));
	) {
		pw1.write("Spalanie LPG na 100km: " + this.lpgOn100km.getText() + "\n" + "Cena LPG:"
				+ this.lpgPrice.getText() + "\n" + "Ilo�� kilometr�w na LPG: " + this.kmOnLPG.getText() + "\n"
				+ "Spalanie pb95 na 100km: " + this.pb95On100km.getText() + "\n" + "Cena PB95: "
				+ this.pb95Price.getText() + "\n" + "Ilo�� kilometr�w na pb95: " + this.kmOnPB95.getText() + "\n"
				+ "Koszt trasy wyniesie: " + this.solution.getText());

	} catch (IOException e) {
		System.err.println("B��d we/wy");
		e.printStackTrace();
	}

}

private void saveToDB() {// zapis do bazy danych
	try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
			"Adam", "1234")) {
		Statement mkTable = conn.createStatement();
		mkTable.executeUpdate(
				"CREATE TABLE cardata(dlpgOn100km double, dlpgPrice double, dkmOnLPG double, dpb95On100km double, dpb95Price double, dkmOnPB95 double)");
	} catch (SQLException ole) {
		System.out.println(ole.getMessage());
	}
	try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
			"Adam", "1234");
			PreparedStatement upTable = conn.prepareStatement("INSERT INTO "
					+ "ADAM.CARDATA(dlpgOn100km, dlpgPrice, dkmOnLPG, dpb95On100km, dpb95Price, dkmOnPB95)\r\n"
					+ "VALUES(?,?,?,?,?,?)"))
	// "UPDATE ADAM.CARDATA SET DLPGON100KM=?" gdy chcemy nadpisa� wszystkie wiersze
	// lub wybieramy wg klucza z WHERE. Nie dzia�a, gdy brak wierszy
	{
		upTable.setDouble(1, dlpgOn100km);
		upTable.setDouble(2, dlpgPrice);
		upTable.setDouble(3, dkmOnLPG);
		upTable.setDouble(4, dpb95On100km);
		upTable.setDouble(5, dpb95Price);
		upTable.setDouble(6, dkmOnPB95);
		upTable.executeUpdate();
		title.setText("Warto�ci zosta�y zapisane w bazie danych");
	} catch (SQLException ole) {
	    JOptionPane.showMessageDialog(null, "B��d zapisu danych");	
	    System.out.println(ole.getMessage());
	}
}
}

