package raizes_do_nordeste.api.dto;

public class AuthRequest {

	private String email;
	private String senha;
	
	//construtor
	public AuthRequest(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	//getters e setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
