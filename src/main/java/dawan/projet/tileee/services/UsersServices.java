package dawan.projet.tileee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.repositories.UsersRepository;

@Service
public class UsersServices {

	@Autowired
	private UsersRepository usersRepository;
	
	public User save(User user) {
		return usersRepository.save(user);
	}

	public User findByRand(String rand) {
		return usersRepository.findByRand(rand);
	}

	public Object findById(int id) {
		return usersRepository.findById(id);
	}

	public User findByLogin(String login) {
		return usersRepository.findByLogin(login);
	}

	public void delete(User user) {
		usersRepository.delete(user);		
	}
}
