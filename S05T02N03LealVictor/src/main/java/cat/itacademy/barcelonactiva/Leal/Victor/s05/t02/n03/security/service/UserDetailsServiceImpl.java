package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.domain.User;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n03.repository.UserRepository;


//Clase que convierte la clase usuario en un UsuarioMain. UserDetailsService es propia de Spring Security.
//instancia de UserDetails que contiene la información del usuario autenticado que debe contener los authorities asociados
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
}
