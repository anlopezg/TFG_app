package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserDto {
	
	public interface AllValidations {}
	
	public interface UpdateValidations {}

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

	private String role;

	public UserDto() {}

	public UserDto(Long id, String userName, String email, String firstName, String language, String country, String crochetLevel, String knitLevel, String bio, String role) {

		this.id = id;
		this.userName = userName != null ? userName.trim() : null;
		this.email = email.trim();
		this.firstName = firstName.trim();
		this.language = language.trim();
		this.country = country.trim();
		this.crochetLevel = crochetLevel.trim();
		this.knitLevel = knitLevel.trim();
		this.bio = bio.trim();

		this.role = role;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	@Email(groups={AllValidations.class, UpdateValidations.class})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getCrochetLevel() {
		return crochetLevel;
	}

	public void setCrochetLevel(String crochetLevel) {
		this.crochetLevel = crochetLevel;
	}

	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getKnitLevel() {
		return knitLevel;
	}

	public void setKnitLevel(String knitLevel) {
		this.knitLevel = knitLevel;
	}

	@Size(min=1, max=200, groups={AllValidations.class, UpdateValidations.class})
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
