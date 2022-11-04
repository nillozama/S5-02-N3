package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query(value = "SELECT id_jugador FROM jugadores ORDER BY average DESC LIMIT 1", nativeQuery = true)
	public int selectBestPlayer();
	
	@Query(value = "SELECT id_jugador FROM jugadores ORDER BY average ASC LIMIT 1", nativeQuery = true)
	public int selectWorstPlayer();
	
	@Query(value = "SELECT AVG(average) FROM jugadores", nativeQuery = true)
	public float selectTotalAverage();
	
	@Query(value = "SELECT nombre_de_usuario FROM jugadores WHERE nombre_de_usuario = :name", nativeQuery = true)
	public String getName(String name);
}