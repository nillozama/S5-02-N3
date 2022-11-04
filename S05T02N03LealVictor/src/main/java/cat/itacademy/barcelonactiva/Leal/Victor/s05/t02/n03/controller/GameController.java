package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.UserDTO;

public interface GameController {
	
	//public ResponseEntity<String> addPlayer(String name);
	public ResponseEntity<String> updatePlayer( UserDTO playerDTO);
	public ResponseEntity<String> playGame(int idPlayerDTO);
	public ResponseEntity<HttpStatus> deleteDiceRolls(int id);
	public ResponseEntity<List<UserDTO>> getAllPlayers();
	public ResponseEntity<List<DiceRollDTO>> getAllDiceRolls(int idPlayerDTO);
	public ResponseEntity<Float> getAverageRanking();
	public ResponseEntity<UserDTO> getWorstPlayer();
	public ResponseEntity<UserDTO> getBestPlayer();
}
