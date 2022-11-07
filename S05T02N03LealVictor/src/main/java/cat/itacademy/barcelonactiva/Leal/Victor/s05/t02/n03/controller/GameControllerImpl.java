package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.DiceRollDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.dto.UserDTO;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.service.DiceRollServiceImpl;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.service.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
//Notación para indicar que es un controlador de tipo Rest
@RestController
//Notación para indicar el contexto de nuestros endpoint
@RequestMapping("/players")
public class GameControllerImpl implements GameController {

	// Inyección de dependencias
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private DiceRollServiceImpl diceRollService;

	@Override
	@PutMapping()
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	public ResponseEntity<String> updatePlayer(@RequestBody UserDTO userDTO) { // modifica el nom del jugador/a

		ResponseEntity<String> responseEntity;
		String name = userDTO.getUsername();

		if (userService.isUser(userDTO.getId())) {

			try {
				userService.getUserById(userDTO.getId());

				if (name.equalsIgnoreCase("ANÒNIM") || !userService.findUserByName(name)) {

					userService.update(userDTO);

					responseEntity = new ResponseEntity<>("S'ha modificat el nom a " + name, HttpStatus.OK);
				} else {

					responseEntity = new ResponseEntity<>("Aquest nom d'usuari ja existeix.", HttpStatus.IM_USED);
				}

			} catch (Exception e) {

				responseEntity = new ResponseEntity<>("No hi ha un usuari amb aquest id.", HttpStatus.NOT_FOUND);
			}
		} else {

			responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return responseEntity;
	}

	@Override
	@PostMapping("/{id}")
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	@ResponseBody
	public ResponseEntity<String> playGame(@PathVariable("id") int idUser) { // un jugador/a específic realitza una
																				// tirada dels daus
		ResponseEntity<String> responseEntity;

		if (userService.isUser(idUser)) {

			try {
				userService.getUserById(idUser);

				UserDTO userDTO = userService.getUserById(idUser);
				DiceRollDTO diceRollDTO = diceRollService.playGame(userDTO);

				try {

					diceRollService.saveOrUpdate(diceRollDTO);
					userService.update(userDTO, diceRollDTO);

					responseEntity = new ResponseEntity<>(diceRollDTO.generateMessage(), HttpStatus.CREATED);
				} catch (Exception e) {
					responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

			catch (Exception e) {

				responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		else {

			responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return responseEntity;
	}

	@Override
	@DeleteMapping("/{id}/games")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteDiceRolls(@PathVariable("id") int idUserDTO) {// elimina les tirades del
																							// jugador
		ResponseEntity<HttpStatus> responseEntity;

		try {
			UserDTO userDTO = userService.getUserById(idUserDTO);

			try {
				diceRollService.deleteAllPlaysByPlayer(idUserDTO);
				userService.deleteAllPlaysByUser(userDTO);

				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		catch (Exception e) {

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/")
	// @PreAuthorize("hasRole('USER')")
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	public ResponseEntity<List<UserDTO>> getAllPlayers() { // retorna el llistat de tots els jugadors/es del sistema
															// amb el seu percentatge mitjà d’èxits
		ResponseEntity<List<UserDTO>> responseEntity;

		try {
			List<UserDTO> usersDTO = new ArrayList<UserDTO>();

			usersDTO = userService.getAllUsers();

			if (usersDTO.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			responseEntity = new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/{id}/games")
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	public ResponseEntity<List<DiceRollDTO>> getAllDiceRolls(@PathVariable("id") int idUser) { // retorna el llistat
																								// dejugades per
																								// unjugador/a
		ResponseEntity<List<DiceRollDTO>> responseEntity;

		if (userService.isUser(idUser)) {

			try {

				List<DiceRollDTO> usersPlaysDTO = new ArrayList<DiceRollDTO>();

				usersPlaysDTO = diceRollService.findByPlayer(idUser);

				responseEntity = new ResponseEntity<List<DiceRollDTO>>(usersPlaysDTO, HttpStatus.OK);

			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {

			responseEntity = new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking")
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	public ResponseEntity<Float> getAverageRanking() { // retorna el ranking mig de tots els jugadors/es del

		ResponseEntity<Float> responseEntity = null;
		float totalAverage = 0;

		totalAverage = userService.getTotalAverage();

		try {

			responseEntity = new ResponseEntity<Float>(totalAverage, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking/loser")
	// @PreAuthorize("hasRole('USER')")
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	public ResponseEntity<UserDTO> getWorstPlayer() { // retorna el jugador/a amb pitjor percentatge d’èxit

		ResponseEntity<UserDTO> responseEntity;

		try {

			UserDTO userDTO = userService.getLoser();

			responseEntity = new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking/winner")
	@PreAuthorize(("hasRole('USER') or hasRole('ADMIN')"))
	public ResponseEntity<UserDTO> getBestPlayer() { // retorna el jugador/a amb mitjor percentatge d’èxit

		ResponseEntity<UserDTO> responseEntity;

		try {

			UserDTO userDTO = userService.getWinner();

			responseEntity = new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
}
