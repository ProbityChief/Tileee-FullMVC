package dawan.projet.tileee.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.dao.TagsDAO;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.dao.UserDao;
import dawan.projet.tileee.validator.UserValidator;

/**
 * Servlet implementation class Identification
 */

@Controller
@RequestMapping("/Identification")
public class Identification {

	@GetMapping()
	@ResponseBody
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
