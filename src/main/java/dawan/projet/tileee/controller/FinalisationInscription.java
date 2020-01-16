package dawan.projet.tileee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Servlet implementation class FinalisationInscription
 */
@RestController
@RequestMapping("/FinalisationInscription")
public class FinalisationInscription {

	@GetMapping("/{rand}")
	@ResponseBody
	public String chargementTags(@PathVariable("rand") String rand) {
		dawan.projet.tileee.dao.UserDao userdao = new dawan.projet.tileee.dao.UserDao("tileee");
		dawan.projet.tileee.bean.User utilisateur = new dawan.projet.tileee.bean.User();
		String userMessage = new String();
    	try {
    	utilisateur = userdao.findByRand(rand, false);

        if(utilisateur.isValidation() == false){
                utilisateur.setValidation(true);
    			userdao.update(utilisateur, true);
                return "Votre compte a été validé! Merci.";
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	
		return "Votre compte a déja été validé! Merci.";
	}
}