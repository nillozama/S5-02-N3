package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.security.entity.Role;

public class UserDTO {

	/**
	 * 
	 */
	// private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	@JsonIgnore
	private String password;
	private String email;
	@JsonIgnore
	private Set<Role> roles;
	@JsonIgnore
	private List<DiceRollDTO> diceRolls;
	private float averagePlays;
	@JsonIgnore
	private Date registrationDate;


	public UserDTO() {

	}

	public UserDTO(String username) {
		super();
		this.username = username;
		diceRolls = new ArrayList<DiceRollDTO>();
		averagePlays = 0;
		roles = new HashSet<>();
	}

	public UserDTO(int id, String username) {
		super();
		this.id = id;
		this.username = username;
		averagePlays = 0;
	}
	
	public UserDTO(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public List<DiceRollDTO> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<DiceRollDTO> diceRolls) {
		this.diceRolls = diceRolls;
	}

	public float getAveragePlays() {
		return averagePlays;
	}

	public void setAveragePlays(float averagePlays) {
		this.averagePlays = averagePlays;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
