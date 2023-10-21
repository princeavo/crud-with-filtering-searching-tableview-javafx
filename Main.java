package com;

import java.util.Comparator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		TableView<Article> liste = new TableView<>();
		liste.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		TableColumn<Article, String> nomColumn = new TableColumn<>("Nom");
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

		TableColumn<Article, Double> priceColumn = new TableColumn<>("Prix");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

		TableColumn<Article, String> desColumn = new TableColumn<>("Description");
		desColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		ObservableList<Article> articles = FXCollections.observableArrayList();
		articles.add(new Article("Cahier", 500, "Cahier scolaire de 300 pages"));
		articles.add(new Article("Bic", 150, "Outil scolaire"));
		articles.add(new Article("Crayon", 100, "Permet de dessiner"));
		articles.add(new Article("Gomme", 50, "Aide dans la suppression des dessins"));
		articles.add(new Article("Traceurs", 500, "Ils permettent de tracer de manière droite"));
		articles.add(new Article("ordinateur", 428156, "Ce sont des machines automatiques"));
		articles.add(new Article("Chargeur", 8500, "Redonne une autre durée de vie auw bactéries"));
		articles.add(new Article("Ecran", 30000, "Affichage"));
		articles.add(new Article("UE", 500, "Unité d'enseignement"));

		liste.setItems(articles);
		liste.getColumns().addAll(nomColumn, priceColumn, desColumn);
		liste.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		ComboBox<String> motif = new ComboBox<>();
		motif.setPromptText("Sélectionnez ici le motif du tri");
		motif.getItems().addAll("Nom", "Prix", "Description");

		ComboBox<String> ordre = new ComboBox<>();
		ordre.setPromptText("Séléctionnez ici l'ordre");
		ordre.getItems().addAll("ordre croissant", "ordre décroissant");

		Button addButton = new Button("Ajouter un article");
		addButton.setOnAction(e -> {
			new AddArticle(articles);
		});

		Button deleteAllButton = new Button("Tout supprimer");

		deleteAllButton.setOnAction(e -> {
			if (!articles.isEmpty()) {
				AlertSuppression alert = new AlertSuppression(0, 2);
				int choix = alert.choice();
				if (choix == AlertSuppression.SUPPRIMER)
					articles.clear();
			}

		});

		Button deleteButton = new Button("Supprimer les articles sélectionnés");

		deleteButton.disableProperty().bind(liste.getSelectionModel().selectedItemProperty().isNull());
		deleteButton.setOnAction(e -> {
			AlertSuppression alert = new AlertSuppression(liste.getSelectionModel().getSelectedItems().size(), 1);
			int choix = alert.choice();
			if (choix == AlertSuppression.SUPPRIMER)
				articles.removeAll(liste.getSelectionModel().getSelectedItems());
		});

		Button updateButton = new Button("Modifier");
		updateButton.disableProperty().bind(liste.getSelectionModel().selectedItemProperty().isNull());
		updateButton.setOnAction(e -> {
			new Update(articles, liste.getSelectionModel().getSelectedIndex());
		});

		HBox buttonBox = new HBox(20);
		buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, deleteAllButton);
		buttonBox.setAlignment(Pos.CENTER_RIGHT);

		HBox box = new HBox(10);
		box.getChildren().addAll(motif, ordre);

		ComboBox<String> searchButton = new ComboBox<>();
		searchButton.getItems().addAll("Nom", "Prix", "Description");
		searchButton.setPromptText("Recherchez par");
		TextField searchField = new TextField();
		searchField.setPromptText("Entrez ce que vous connaissez sur l'article");

		FlowPane flow = new FlowPane(searchButton, searchField);
		flow.setHgap(20);
		flow.setVgap(10);
		flow.setAlignment(Pos.CENTER_RIGHT);
		searchField.setPrefWidth(200);
		box.getChildren().add(flow);
		HBox.setHgrow(flow, Priority.SOMETIMES);

		BorderPane root = new BorderPane();
		root.setCenter(liste);
		root.setTop(box);
		BorderPane.setMargin(box, new Insets(10));
		root.setPadding(new Insets(20));
		BorderPane.setMargin(buttonBox, new Insets(10));
		root.setBottom(buttonBox);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toString());

		primaryStage.setScene(scene);
		primaryStage.show();
//		primaryStage.setMinHeight(400);
//		primaryStage.setMinWidth(700);

		motif.valueProperty().addListener((v, o, n) -> {
			if (ordre.getValue() == null) {
				ordre.setValue("ordre croissant");
			}
			String motifValue = motif.getValue();
			String orderValue = ordre.getValue();
			if (motifValue.equals("Nom")) {
				if (orderValue.equals("ordre croissant")) {
					articles.sort(Comparator.comparing(Article::getNom));
				} else {
					articles.sort(Comparator.comparing(Article::getNom).reversed());
				}

			} else if (motifValue.equals("Prix")) {
				if (orderValue.equals("ordre croissant")) {
					articles.sort(Comparator.comparing(Article::getPrix));
				} else {
					articles.sort(Comparator.comparing(Article::getPrix).reversed());
				}
			} else {
				if (orderValue.equals("ordre croissant")) {
					articles.sort(Comparator.comparing(Article::getDescription));
				} else {
					articles.sort(Comparator.comparing(Article::getDescription).reversed());
				}
			}
		});

		ordre.valueProperty().addListener((v, o, n) -> {
			if (motif.getValue() == null) {
				motif.setValue("Nom");
			}
			String motifValue = motif.getValue();
			String orderValue = ordre.getValue();
			if (motifValue.equals("Nom")) {
				if (orderValue.equals("ordre croissant")) {
					articles.sort(Comparator.comparing(Article::getNom));
				} else {
					articles.sort(Comparator.comparing(Article::getNom).reversed());
				}

			} else if (motifValue.equals("Prix")) {
				if (orderValue.equals("ordre croissant")) {
					articles.sort(Comparator.comparing(Article::getPrix));
				} else {
					articles.sort(Comparator.comparing(Article::getPrix).reversed());
				}
			} else {
				if (orderValue.equals("ordre croissant")) {
					articles.sort(Comparator.comparing(Article::getDescription));
				} else {
					articles.sort(Comparator.comparing(Article::getDescription).reversed());
				}
			}
		});

		FilteredList<Article> data = new FilteredList<>(articles, p -> true);

		searchField.textProperty().addListener((v, o, n) -> {

			if (searchButton.getValue() == null)
				searchButton.setValue("Nom");
			String recherche = n.trim().toLowerCase();
			String mot = searchButton.getValue();
			if (recherche.isEmpty()) {
				data.setPredicate(p -> {
					return true;
				});
			} else {
				data.setPredicate(article -> {
					if (mot.equals("Nom")) {
						return article.getNom().toLowerCase().contains(recherche);
					} else if (mot.equals("Prix")) {
						return String.valueOf(article.getPrix()).toLowerCase().contains(recherche);
					} else {
						return article.getDescription().toLowerCase().contains(recherche);
					}
				});
			}
			liste.setItems(data);
		});

		searchButton.valueProperty().addListener((v, o, n) -> {
			String recherche = searchField.getText().trim().toLowerCase();
			String mot = n;
			if (recherche.isEmpty()) {
				data.setPredicate(p -> {
					return true;
				});
			} else {
				data.setPredicate(article -> {
					if (mot.equals("Nom")) {
						return article.getNom().toLowerCase().contains(recherche);
					} else if (mot.equals("Prix")) {
						return String.valueOf(article.getPrix()).toLowerCase().contains(recherche);
					} else {
						return article.getDescription().toLowerCase().contains(recherche);
					}
				});
			}
			liste.setItems(data);
		});

	}

}
