package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.repositories.UsersRepository;


@RestController
@RequestMapping("/reinitialisationmdp")
public class ReinitialisationMDP {

	@Autowired
	private UsersRepository usersServices;
	
	@PostMapping()
	@ResponseBody
	public Message modifierMDP(@RequestBody dawan.projet.tileee.bean.User user) {
		Message msg = new Message();
		try {
			usersServices.save(user);
			msg.setMessage("Votre mot de passe à bien été réinitialisé. Vous pouvez vous connecter.");
			return msg;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	msg.setMessage("Erreur pendant la mise à jour de votre mot de passe");
	return msg;
	}
}
