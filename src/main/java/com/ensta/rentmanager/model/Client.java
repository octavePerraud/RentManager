package com.ensta.rentmanager.model;

import java.sql.Date;

public class Client {
	private int id;
	String nom;
	String Prenom;
	String email;
	Date naissance;
	
	public Client() {
		
	}
	
	public Client(int id,String nom,String Prenom,String email,Date naissance){
		this.id=id;
		this.nom=nom;
		this.Prenom=Prenom;
		this.email=email;
		this.naissance=naissance;
		
	}

	public String getPrenom() {
		return Prenom;
	}

	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getNaissance() {
		return naissance;
	}

	public void setNaissance(Date naissance) {
		this.naissance = naissance;
	}

	@Override // surcharge pour choisir ce que retourne toString
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", Prenom=" + Prenom + ", email=" + email + ", naissance="
				+ naissance + "]";
	}

}
