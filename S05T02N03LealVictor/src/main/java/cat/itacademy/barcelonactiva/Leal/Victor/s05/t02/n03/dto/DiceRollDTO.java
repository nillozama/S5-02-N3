package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DiceRollDTO {
	
	private long id;
	private byte dice1;
	private byte dice2;
	private byte score;
	@JsonIgnore
	private boolean winningRoll;
	private String message;
	@JsonIgnore
	private UserDTO user;
	
	public DiceRollDTO() {
		
	}
	
	public DiceRollDTO(UserDTO user) {
		super();
		
		this.user=user;
		dice1=calculateDiceScore();
		dice2=calculateDiceScore();
		score=(byte) (dice1+dice2);
		winningRoll=score==7;
		message=generateMessage();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public int getDice1() {
		return dice1;
	}

	public void setDice1(byte dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(byte dice2) {
		this.dice2 = dice2;
	}

	public byte getScore() {
		return score;
	}

	public void setScore(byte score) {
		this.score = score;
	}

	public boolean getWinningRoll() {
		return winningRoll;
	}

	public void setWinningRoll(boolean winningRoll) {
		this.winningRoll = winningRoll;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte calculateDiceScore(){
		
		return  (byte) (6*Math.random()+1);
	}
	
	public String generateMessage() {
		
		return winningRoll?"YOU WIN!!":"YOU LOST!!";
	}
}
