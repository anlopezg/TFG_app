package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.*;


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
	private int crochetLevel;
	private int knitLevel;
	private String bio;

	private String role;

	public UserDto() {}

	public UserDto(Long id, String userName, String email, String firstName, String language, String country, int crochetLevel, int knitLevel, String bio, String role) {

		this.id = id;
		this.userName = userName != null ? userName.trim() : null;
		this.email = email.trim();
		this.firstName = firstName.trim();
		this.language = language.trim();
		this.country = country.trim();
		this.crochetLevel = crochetLevel;
		this.knitLevel = knitLevel;
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

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Min(value=0, groups={AllValidations.class, UpdateValidations.class})
	@Max(value=3, groups={AllValidations.class, UpdateValidations.class})
	public int getCrochetLevel() {
		return crochetLevel;
	}

	public void setCrochetLevel(int crochetLevel) {
		this.crochetLevel = crochetLevel;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Min(value=0, groups={AllValidations.class, UpdateValidations.class})
	@Max(value=3, groups={AllValidations.class, UpdateValidations.class})
	public int getKnitLevel() {
		return knitLevel;
	}

	public void setKnitLevel(int knitLevel) {
		this.knitLevel = knitLevel;
	}

	@Size( max=200, groups={AllValidations.class, UpdateValidations.class})
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
