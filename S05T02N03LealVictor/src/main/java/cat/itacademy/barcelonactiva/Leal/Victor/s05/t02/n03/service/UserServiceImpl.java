package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.domain.User;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.UserDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.repository.UserRepository;

import org.modelmapper.ModelMapper;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public List<UserDTO> getAllUsers() {
		
		List <User> users=new ArrayList<User>();
		userRepository.findAll().forEach(p->users.add(p));
		List<UserDTO> usersDTO=new ArrayList<UserDTO>();
		
		if (!users.isEmpty()) {

			users.forEach(p->usersDTO.add(modelMapper.map(p, UserDTO.class)));
		}
		
		return usersDTO;
	}
	
	@Override
	public UserDTO getUserById(int id) {
		
		User user=userRepository.findById(id).get();
		UserDTO userDTO=modelMapper.map(user,UserDTO.class);
		
		return userDTO;
	}
	
	@Override
	public void save(UserDTO userDTO) {
		
		User user=modelMapper.map(userDTO, User.class);

		userRepository.save(user);
	}
	
	@Override
	public void update(UserDTO userDTO) {
		
		String username=userDTO.getUsername();
		userDTO=getUserById(userDTO.getId());
		userDTO.setUsername(username);	
		User user=modelMapper.map(userDTO, User.class);

		userRepository.save(user);
	}
	
	@Override
	public void update(UserDTO userDTO, DiceRollDTO diceRollDTO) {
		
		List<DiceRollDTO> diceRolls=userDTO.getDiceRolls();
		diceRolls.add(diceRollDTO);
		userDTO.setDiceRolls(diceRolls);
		updateAverageService(userDTO);
		User user=modelMapper.map(userDTO, User.class);
		userRepository.save(user);
	}
	
	@Override
	public void delete(int id) {
		
		userRepository.deleteById(id);
	}
	
	public void updateAverageService(UserDTO userDTO) {
		
		float average=0;
		int count = 0;
		
		if (userDTO.getDiceRolls().size() != 0) {
			for (DiceRollDTO d : userDTO.getDiceRolls()) {

				if (d.getWinningRoll()) {

					count++;
				}
			}
			average = (float) count * 100 / userDTO.getDiceRolls().size();
		}

		average=Math.round(average*100);
		userDTO.setAveragePlays(average/100);
	}
	
	@Override
	public UserDTO getWinner() {
		
		User user=userRepository.findById(userRepository.selectBestPlayer()).get();
		UserDTO userDTO=modelMapper.map(user,UserDTO.class);
		
		return userDTO;
	}
	
	@Override
	public UserDTO getLoser() {
		
		User user=userRepository.findById(userRepository.selectWorstPlayer()).get();
		UserDTO userDTO=modelMapper.map(user,UserDTO.class);
		
		return userDTO;
	}
	
	@Override
	public float getTotalAverage() {
		
		float totalAverage=Math.round(userRepository.selectTotalAverage()*100);

		return totalAverage/100;
	}
	
	@Override
	public boolean findUserByName(String name) {
		
		boolean result=false;
		
		if (userRepository.getName(name)!=null) {

			result=true;
		}

		return result;
	}

	@Override
	public void restartAverage(UserDTO userDTO) {
		
		userDTO.setAveragePlays(0);
		User user=modelMapper.map(userDTO, User.class);
		userRepository.save(user);
	}
	
	@Override
	public void deleteAllPlaysByUser(UserDTO userDTO) {
		
		List<DiceRollDTO> diceRolls=new ArrayList <DiceRollDTO>();
		userDTO.setDiceRolls(diceRolls);
		updateAverageService(userDTO);
		User user=modelMapper.map(userDTO, User.class);
		userRepository.save(user);
	}
	
	public boolean isUser(int id) {
		
		User user=userRepository.findById(id).get();
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equalsIgnoreCase(user.getUsername());
	}
}
