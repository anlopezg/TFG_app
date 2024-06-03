package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;


@Entity
public class User {
	
	public enum RoleType {USER, SELLER}
	private Long id;
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String language;
	private String country;
	private String region;
	private int crochetLevel;
	private int knitLevel;
	private String bio;
	private RoleType role;

	private ShoppingCart shoppingCart;

	private StripeAccount stripeAccount;



	public User(){}

	public User(String username, String email, String password, String firstName, String language, String country,
				String region, int crochetLevel, int knitLevel, String bio) {

		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.language = language;
		this.country = country;
		this.region = region;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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



	@OneToOne(mappedBy="user", optional=false, fetch=FetchType.LAZY)
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}


	@OneToOne(mappedBy="user", optional=false, fetch=FetchType.LAZY)
	public StripeAccount getStripeAccount() {
		return stripeAccount;
	}

	public void setStripeAccount(StripeAccount stripeAccount) {
		this.stripeAccount = stripeAccount;
	}
}
