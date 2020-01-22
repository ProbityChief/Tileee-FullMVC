package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.services.UsersServices;

@RestController
@RequestMapping("/suppressioncompte")
public class SuppressionCompte {

	@Autowired
	private UsersServices usersServices;
	@PostMapping()
	public Message supressionCompte(@RequestBody dawan.projet.tileee.bean.User user) {
		Message msg = new Message();
		try {
    		usersServices.delete(user);
    		msg.setMessage("Votre compte à bien été supprimé");
    		return msg;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		msg.setMessage("Erreur pendant la suppression de votre compte");
		return msg;
	}
}
