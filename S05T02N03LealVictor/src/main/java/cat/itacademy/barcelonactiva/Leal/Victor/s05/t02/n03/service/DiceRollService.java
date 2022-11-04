package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.service;

import java.util.List;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.UserDTO;

public interface DiceRollService {
	
	public List <DiceRollDTO> getAllDiceRolls();
	public List <DiceRollDTO> findByPlayer(int idPlayer);
	public void saveOrUpdate(DiceRollDTO diceRollDTO);
	public void deleteAllPlaysByPlayer(int idPlayer);
	public DiceRollDTO playGame(UserDTO playerDTO);
}
