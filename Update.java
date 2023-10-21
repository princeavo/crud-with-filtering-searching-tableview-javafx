package com;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Update {
	private Stage stage;
	private	Article article;
	private	ObservableList<Article> liste;

	public Update(ObservableList<Article> liste,int index) {
		this.liste = liste;
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		// Le première colonne
		grid.add(new Label("Nom"), 0, 0);
		grid.add(new Label("Prix"), 0, 1);
		grid.add(new Label("Description"), 0, 2);
		// La deuxième colonne
		TextField nomLabel = new TextField(this.liste.get(index).getNom());
		TextField prixLabel = new TextField(this.liste.get(index).getPrix()+"");
		TextField obsLabel = new TextField(this.liste.get(index).getDescription());
		grid.add(nomLabel, 1, 0);
		grid.add(prixLabel, 1, 1);
		grid.add(obsLabel, 1, 2);
		
		
		
		
		prixLabel.textProperty().addListener((v,o,n)->{
			try {
				Double.parseDouble(n);
				if(o.equals("0"))
					prixLabel.setText(n.substring(1));
			}catch (NumberFormatException e) {
				if(!n.isEmpty()) {
					if(!o.isEmpty())
						prixLabel.setText(o);
					else
						prixLabel.setText("0");
				}else {
					prixLabel.setText("0");
				}
				
			}
		});
		
		

		Label info = new Label("Veuillez renseigner les champs suivant relatifs à l'article");
		
		Button addButton = new Button("Modifier l'article");
		addButton.disableProperty().bind(nomLabel.textProperty().isEmpty().or(prixLabel.textProperty().isEmpty().or(obsLabel.textProperty().isEmpty())));
		addButton.setOnAction(e->{
			article = new Article(nomLabel.getText(),Double.parseDouble(prixLabel.getText()), obsLabel.getText());
			
			this.liste.set(index, article);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Votre article a été modifié avec succés");
			alert.showAndWait();
			nomLabel.clear();
			prixLabel.clear();
			obsLabel.clear();
			stage.close();
		});
		
		Button cancelButton = new Button("Annuler");
		cancelButton.setOnAction(e->{
			stage.close();
		});

		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(addButton, cancelButton);
		hbox.setAlignment(Pos.CENTER_RIGHT);

		VBox root = new VBox(30);
		root.getChildren().addAll(info, grid, hbox);
		root.setPadding(new Insets(30));
		root.setAlignment(Pos.CENTER);

		stage = new Stage();
		stage.setTitle("Ajout d'un article");
		stage.setResizable(false);
		stage.setScene(new Scene(root));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
}
