package dawan.projet.tileee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Servlet implementation class Identification
 */

@RestController
@RequestMapping("/Identification")
public class Identification {

	@GetMapping()
	public boolean identification(@RequestBody dawan.projet.tileee.bean.User user) {
		dawan.projet.tileee.dao.UserDao userdao = new dawan.projet.tileee.dao.UserDao("tileee");
		dawan.projet.tileee.validator.UserValidator uservalidator = new dawan.projet.tileee.validator.UserValidator("tileee");
		dawan.projet.tileee.bean.User usr = userdao.findByName(user.getLogin(), true);
		if (uservalidator.hashPassword(user.getPassword()) == usr.getPassword()) {
			return true;
		}
		return false;
	}

	
	@PostMapping()
	public boolean inscription(@RequestBody dawan.projet.tileee.bean.User user) {
		dawan.projet.tileee.dao.UserDao userdao = new dawan.projet.tileee.dao.UserDao("tileee");
		dawan.projet.tileee.validator.UserValidator uservalidator = new dawan.projet.tileee.validator.UserValidator("tileee");
		try {

			String rand = uservalidator.hash(user.getLogin() + "_" + user.getMail());
			user.setRand(rand);

			uservalidator.sendEmail("Tileee <dawan-test@gmail.com>", user.getMail(),
					"Votre compte sur Tileee",
					"<h1>Bienvenu sur Tileee</h1><p><br /><br />" + user.getLogin()
							+ ", <br /></p><br />Bienvenu sur Tileee, veuillez cliquer <a href=http://localhost:8181/tileee/FinalisationInscription?rand="
							+ rand
							+ ">ici</a> pour activer votre compte.<p><p>Cordialement,</p><p>L'&eacute;quipe Tileee</p>",
					null, null, null);
			userdao.insert(user, true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
