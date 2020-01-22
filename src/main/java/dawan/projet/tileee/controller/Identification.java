package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.services.UsersServices;
import dawan.projet.tileee.validator.UserValidator;

/**
 * Servlet implementation class Identification
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/identification")
public class Identification {
	
	@Autowired
	private UsersServices usersServices;
	
	@Value("${urlfront}")
	private String urlFront;

	@PostMapping("/login")
	public Message identification(@RequestBody dawan.projet.tileee.bean.User user) {
		dawan.projet.tileee.bean.User usr = usersServices.findByLogin(user.getLogin());
		Message msg = new Message();
		if (user.getPassword() == usr.getPassword()) {
			msg.setMessage("Connection au compte réussie");
			return msg;
		}
		msg.setMessage("Echec de la connexion au compte");
		return msg;
	}

	@PostMapping("/inscription")
	public Message inscription(@RequestBody dawan.projet.tileee.bean.User user) {
		Message msg = new Message();
		try {
			String rand = UserValidator.hash(user.getLogin() + "_" + user.getMail());
			user.setRand(rand);

			UserValidator.sendEmail("Tileee <dawan-test@gmail.com>", user.getMail(),
					"Votre compte sur Tileee",
					"<h1>Bienvenu sur Tileee</h1><p><br /><br />" + user.getLogin()
							+ ", <br /></p><br />Bienvenu sur Tileee, veuillez cliquer <a href=" + urlFront + "subscription/"
							+ rand
							+ ">ici</a> pour activer votre compte.<p><p>Cordialement,</p><p>L'&eacute;quipe Tileee</p>",
					null, null, null);
			usersServices.save(user);
			msg.setMessage("To complete your registration, please consult your mailbox and click on the registration completion link");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg.setMessage("Problem when creating the account");
		return msg;
	}
}
