package es.udc.paproject.backend.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class User {
	
	public enum RoleType {USER, SELLER};

	private Long id;
	private String userName;
	private String email;
	private String password;
	private String firstName;

	private String language;
	private String country;
	private String crochetLevel;
	private String knitLevel;
	private String bio;

	private RoleType role;

	public User(){}

	public User(String userName, String email, String password, String firstName){
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
	}

	public User(String userName, String email, String password, String firstName, String language, String country, String crochetLevel, String knitLevel, String bio) {

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


	public String getCrochetLevel() {
		return crochetLevel;
	}

	public void setCrochetLevel(String crochetLevel) {
		this.crochetLevel = crochetLevel;
	}

	public String getKnitLevel() {
		return knitLevel;
	}

	public void setKnitLevel(String knitLevel) {
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
