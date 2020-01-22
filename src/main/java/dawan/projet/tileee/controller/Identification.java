package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.bean.User;
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
	public Boolean identification(@RequestBody User user) {
		User usr = usersServices.findByLogin(user.getLogin());
		if (usr != null && user.getPassword().equals(usr.getPassword())) {
			return true;
		}
		return false;
	}

	@PostMapping("/inscription")
	public Message inscription(@RequestBody User user) {
		Message msg = new Message();
		try {
			String rand = UserValidator.hash(user.getLogin() + "_" + user.getMail());
			user.setRand(rand);

			UserValidator.sendEmail("Tileee <dawan-test@gmail.com>", user.getMail(),
					"Your account on Tileee",
					"<h1>Welcome to Tileee</h1><p><br /><br />" + user.getLogin()
							+ ", <br /></p><br />Hello, please follow this link <a href=" + urlFront + "subscription/"
							+ rand
							+ ">here</a> to activate your account.<p><p>Glad to have you,</p><p>Team Tileee</p>",
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
