package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.validator.UserValidator;

/**
 * Servlet implementation class MotDePassOublie
 */
@RestController
@RequestMapping("/motdepasseoublie")
public class MotDePasseOublie {
	
	@Value("${urlfront}")
	private String urlFront;
	
	@PostMapping()
	@ResponseBody
	public Message chargementTags(@RequestBody dawan.projet.tileee.bean.User user) {
		Message msg = new Message();
		try {
			UserValidator.sendEmail("Tileee <dawan-test@gmail.com>", user.getMail(),
					"Votre compte sur Tileee",
					"<h1>Demande de nouveau mot de passe sur Tileee</h1><p><br /><br />"
							+ ", <br /></p><br />Bonjour " + user.getLogin() + ", clique <a href="+ urlFront + "reinitialisationmdp?rand="
							+ user.getRand()
							+ ">ici</a> pour red&eacute;finir un nouveau mot de passe sur Tileee.<p><p>Cordialement,</p><p>L'&eacute;quipe Tileee</p>",
					null, null, null);
			msg.setMessage("Un mail de redéfinition de mot de passe vous a été envoyé");
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		msg.setMessage("Aucun compte ne correspondant à l'adresse e-mail n'a été trouvé");
		return msg;
	}
}
