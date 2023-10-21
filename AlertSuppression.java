package com;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertSuppression {
	
	private Stage alert;
	public static final int SUPPRIMER = 1;
	public static final int ANNULER = 0;
	private int choix = ANNULER;
	Button cancelButton;
	Button okButton;
	
	public AlertSuppression(int nombre,int n ) {
		String info = (n == 1)? "Vous êtes sur le point de supprimer " + ((nombre>1)?"tous les éléments sélectionnés":"l'élément sélectionné"):
			"Vous êtes sur le point de supprimer tous les articles";
		Label texte = new Label(info);
		Label rouge = new Label("Cette action est irréversible");
		rouge.setStyle("-fx-font-size:15px");
		texte.setStyle("-fx-font-size:15px");
		
		HBox hbox = new HBox(10);
		okButton = new Button("Confirmer");
		okButton.setStyle("-fx-background-color:red;-fx-font-size:15");
		okButton.setPadding(new Insets(2));
		
		cancelButton = new Button("Annuler");
		cancelButton.setStyle("-fx-background-color:green;-fx-font-size:15");
		cancelButton.setPadding(new Insets(2));
		
		hbox.getChildren().addAll(okButton,cancelButton);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		
		VBox root = new VBox(10);
		root.getChildren().addAll(texte,rouge,hbox);
		root.setPadding(new Insets(30));
		VBox.setMargin(hbox, new Insets(10));
		
		alert = new Stage();
		alert.setScene(new Scene(root));
		alert.setTitle("Confirmation");
		alert.setResizable(false);
		alert.initModality(Modality.APPLICATION_MODAL);
		
	}
	public int choice() {
		okButton.setOnAction(e->{
			choix = SUPPRIMER;
			alert.close();
		});
		cancelButton.setOnAction(e->{
			alert.close();
		});
		alert.showAndWait();
		return choix;
	}
}
