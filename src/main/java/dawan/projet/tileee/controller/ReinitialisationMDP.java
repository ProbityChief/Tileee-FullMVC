package dawan.projet.tileee.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.dao.UserDao;
import dawan.projet.tileee.validator.UserValidator;


@Controller
@RequestMapping("/ReinitialisationMDP")
public class ReinitialisationMDP {

	@PostMapping()
	@ResponseBody
	public String modifierMDP(@RequestBody dawan.projet.tileee.bean.User user) {
		UserValidator uservalidator = new UserValidator("tileee");
		try {
			user.setPassword(uservalidator.hashPassword(user.getPassword()));
			UserDao userdao = new UserDao("tileee");
			userdao.update(user, true);
			return "Votre mot de passe à bien été réinitialisé. Vous pouvez vous connecter";
		} catch(Exception e) {
			e.printStackTrace();
		}
	return "Erreur pendant la mise à jour de votre mot de passe";
	}
}
