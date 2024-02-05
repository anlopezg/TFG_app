package es.udc.paproject.backend.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User {
	
	public enum RoleType {USER, SELLER}

	private Long id;

	private String userName;

	private String email;

	private String password;

	private String firstName;

	private String language;

	private String country;

	private int crochetLevel;

	private int knitLevel;

	private String bio;

	private RoleType role;

	public User(){}

	public User(String userName, String email, String password, String firstName, String language, String country, int crochetLevel, int knitLevel, String bio) {

		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.language = language;
		this.country = country;
		this.crochetLevel = crochetLevel;
		this.knitLevel = knitLevel;
		this.bio = bio;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public int getCrochetLevel() {
		return crochetLevel;
	}

	public void setCrochetLevel(int crochetLevel) {
		this.crochetLevel = crochetLevel;
	}

	public int getKnitLevel() {
		return knitLevel;
	}

	public void setKnitLevel(int knitLevel) {
		this.knitLevel = knitLevel;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}


}
