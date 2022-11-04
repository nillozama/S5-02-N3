package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.security.entity.ERole;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByName(ERole name);

}
