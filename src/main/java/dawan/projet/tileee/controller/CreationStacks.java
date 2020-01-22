package dawan.projet.tileee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Message;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.services.CardsServices;

@RestController
@RequestMapping("/creationstacks")
public class CreationStacks {

	@Autowired
	private CardsServices cardsServices;

	@PostMapping("")
	@ResponseBody
	public Message ajoutCarte(@RequestBody Card card, @RequestBody String tag, @RequestBody String tag1,
			@RequestBody String tag2, @RequestBody String tag3, @RequestBody String tag4, @RequestBody User user) {
		Message msg = new Message();
		try {
			if (tag != null)
				cardsServices.insert(card, tag, user);
			if (tag1 != null)
				cardsServices.insert(card, tag1, user);
			if (tag2 != null)
				cardsServices.insert(card, tag2, user);
			if (tag3 != null)
				cardsServices.insert(card, tag3, user);
			if (tag4 != null)
				cardsServices.insert(card, tag4, user);
			msg.setMessage("La carte a bien été ajouté au stacks spécifiées");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		msg.setMessage("Problème lors de l'ajout de la carte aux stacks spécifiées");
		return msg;
	}
}