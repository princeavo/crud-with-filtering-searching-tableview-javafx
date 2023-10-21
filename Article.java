package com;

public class Article {
	private String nom;
	private double prix;
	private String description;
	
	public Article(String nom,double prix,String description) {
		this.nom = nom;
		this.prix = prix;
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
