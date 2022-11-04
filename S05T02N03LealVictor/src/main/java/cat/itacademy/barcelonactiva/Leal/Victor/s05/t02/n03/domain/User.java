package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.security.entity.Role;

@Entity
@Table(name = "jugadores")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_jugador")
	private int id;
	@NotBlank
	@Size(max = 20)
	@Column(name = "nombre_de_usuario", unique = true)
	private String username;
	@NotBlank
	@Size(max = 120)
	private String password;
	@NotBlank
	@Size(max = 50)
	@Column(name = "email", unique = true)
	@Email
	private String email;
	@CreationTimestamp
	@Column(name = "fecha_registro", updatable = false)
	private Date registrationDate;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "player_roles", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	@OneToMany(mappedBy = "user")
	private List<DiceRoll> diceRolls;
	@Column(name = "average")
	private float averagePlays;

	public User() {
	}

	public User(String username, String email, String password) {
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public float getAveragePlays() {
		return averagePlays;
	}

	public void setAveragePlays(float averagePlays) {
		this.averagePlays = averagePlays;
	}

	public List<DiceRoll> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<DiceRoll> diceRolls) {
		this.diceRolls = diceRolls;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
