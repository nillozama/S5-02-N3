package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.payload.request;

import javax.validation.constraints.NotBlank;

//Clase que hace de DTO para el login de usuario.

public class LoginRequest {
	@NotBlank
  private String username;

	@NotBlank
	private String password;

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
}
