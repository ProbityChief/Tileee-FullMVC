package dawan.projet.tileee.repositories;

import org.springframework.data.repository.CrudRepository;

import dawan.projet.tileee.bean.User;

public interface UsersRepository extends CrudRepository<User, Integer>{
	User findByLogin(String login);
	
	User findByRand(String rand);
	
	User findByMail(String mail);
	
	User findByLoginAndPassword(String login, String typedPassword);
}
