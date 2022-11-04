package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.service;

import java.util.List;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.UserDTO;


public interface UserService {
	
	public List <UserDTO> getAllUsers();
	public UserDTO getUserById(int id);
	public void save(UserDTO userDTO);
	public void update(UserDTO userDTO);
	public void update(UserDTO userDTO, DiceRollDTO diceRollDTO);
	public void delete(int id);
	public UserDTO getWinner();
	public UserDTO getLoser();
	public float getTotalAverage();
	public boolean findUserByName(String name);
	public void restartAverage(UserDTO userDTO);
	public void deleteAllPlaysByUser(UserDTO userDTO);
}
