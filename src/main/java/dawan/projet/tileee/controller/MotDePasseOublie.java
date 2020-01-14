package dawan.projet.tileee.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.dao.TagsDAO;

/**
 * Servlet implementation class MotDePassOublie
 */
@Controller
@RequestMapping("/MotDePasseOublie")
public class MotDePasseOublie {

	@PostMapping()
	@ResponseBody
	public String chargementTags(@RequestBody dawan.projet.tileee.bean.User user) {
		dawan.projet.tileee.dao.UserDao userdao = new dawan.projet.tileee.dao.UserDao("tileee");
		dawan.projet.tileee.bean.User usr = userdao.findByMail(user.getMail(), true);
		dawan.projet.tileee.validator.UserValidator uservalidator = new dawan.projet.tileee.validator.UserValidator("tileee");

		try {
			uservalidator.sendEmail("Tileee <dawan-test@gmail.com>", user.getMail(),
					"Votre compte sur Tileee",
					"<h1>Demande de nouveau mot de passe sur Tileee</h1><p><br /><br />"
							+ ", <br /></p><br />Bonjour " + user.getLogin() + ", clique <a href=http://localhost:8181/tileee/ReinitialisationMDP?rand="
							+ user.getRand()
							+ ">ici</a> pour red&eacute;finir un nouveau mot de passe sur Tileee.<p><p>Cordialement,</p><p>L'&eacute;quipe Tileee</p>",
					null, null, null);
			return "Un mail de redéfinition de mot de passe vous a été envoyé";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Aucun compte ne correspondant à cette adresse e-mail n'a été trouvé";
	}
}
