package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.services.CardsServices;

@RestController
@RequestMapping("/creationstacks")
public class CreationStacks {

	@Autowired
	private CardsServices cardsServices;

	@PostMapping("/ajoutcarte")
	public Message ajoutCarte(@RequestBody Card card, @RequestBody String tag, @RequestBody String tag1, @RequestBody String tag2,
			@RequestBody String tag3, @RequestBody String tag4, @RequestBody String login) {
		Message msg = new Message();
		try {
			if (tag != null)
				cardsServices.insert(card, tag, login);
			if (tag1 != null)
				cardsServices.insert(card, tag, login);
			if (tag2 != null)
				cardsServices.insert(card, tag, login);
			if (tag3 != null)
				cardsServices.insert(card, tag, login);
			if (tag4 != null)
				cardsServices.insert(card, tag, login);
			msg.setMessage("card(s) has been saved");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		msg.setMessage("card(s) could not be saved");
		return msg;
	}
	
	@PostMapping("/suppressioncarte")
	public Message suppressionCarte(@RequestBody Card card, @RequestBody String tag, @RequestBody String login) {
		Message msg = new Message();
		try {
			if (tag != null)
				cardsServices.remove(card, tag, login);

			msg.setMessage("La carte a bien été supprimé de la stack");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		msg.setMessage("Problème lors de la suppression de la carte");
		return msg;
	}
}