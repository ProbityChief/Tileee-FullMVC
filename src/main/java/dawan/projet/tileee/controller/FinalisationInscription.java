package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.services.UsersServices;

/**
 * Servlet implementation class FinalisationInscription
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/finalisationinscription")
public class FinalisationInscription {
	
	@Autowired
	private UsersServices usersServices;
	
	@GetMapping("/{rand}")
	public Message chargementTags(@PathVariable("rand") String rand) {
		dawan.projet.tileee.bean.User user = new dawan.projet.tileee.bean.User();
		Message msg = new Message();
    	try {
    		System.out.println(rand);
    	user = usersServices.findByRand(rand);

        if(user.isValidation() == false){
                user.setValidation(true);
    			usersServices.save(user);
    			msg.setMessage("Your registration has been validated. You can log in now");
                return msg;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	
    	msg.setMessage("An error occurred during the validation of your account, please try later.");
        return msg;
	}
}