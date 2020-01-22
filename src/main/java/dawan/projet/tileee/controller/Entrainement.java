package dawan.projet.tileee.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.services.TagsServices;
import dawan.projet.tileee.services.UsersServices;

/**
 * Servlet implementation class Entrainement
 */
@RestController
@RequestMapping("/entrainement")
public class Entrainement {

	@Autowired
	private UsersServices usersServices;
	
	@Autowired
	private TagsServices tagsServices;

	@GetMapping("/{login}")
	@ResponseBody
	public Set<Tag> chargementTags(@PathVariable("login") String login) {

		User user = (User) usersServices.findByLogin(login);

		if (user == null) {
			return new HashSet<Tag>();
		} else {
			return user.getTags();
		}
	}

	@GetMapping("/{rand}/{type}")
	public List<String> chargementMotCartesDeTag(@PathVariable("rand") String rand, @PathVariable("type") String type) {
			Tag tag = tagsServices.findByRand(rand);
			Set<Card> listCard = tag.getCards();
			
		if (type == "mot") {
			ArrayList<String> mot = new ArrayList<String>();
			for (Card c : listCard) {
				mot.add(c.getWord());
			}
			return mot;
		} else if (type == "traduction"){
			ArrayList<String> traduction = new ArrayList<String>();
			for (Card c : listCard) {
				traduction.add(c.getTranslation());
			}
			return traduction;
		}
		return new ArrayList<>();
	}
}