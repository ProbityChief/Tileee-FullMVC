package dawan.projet.tileee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.repositories.TagsRepository;
import dawan.projet.tileee.validator.UserValidator;

@Service
public class CardsServices {

	@Autowired
	private TagsRepository tagsRepository;

	@Autowired
	private UsersServices usersServices;
	
	public void insert(Card card, String tagName, String login) {
		User user = usersServices.findByLogin(login);
		if (card != null && card.getId() == 0) {
				boolean tagExists = tagsRepository.findByTagName(tagName) != null;
				Tag tag = null;

				if (!tagExists) {
					tag = new Tag();
					tag.setUser(user);
					tag.setTagName(tagName);
					tag.setRand(UserValidator.hash(user.getLogin() + "_" + tagName));
					user.addTag(tag);
				} else {
					tag = tagsRepository.findByTagName(tagName);
				}

				tag.addCard(card);

				//cardsRepository.save(card);
		}
	}
	
	public void remove(Card card, String tagName, String login) {
		User user = usersServices.findByLogin(login);
		if (card != null && card.getId() == 0) {
				Tag tag = tagsRepository.findByTagName(tagName);

				tag.removeCard(card);
				
				if (tag.getCards().size() == 0) {		
					user.removeTag(tag);
				} else {
					tag = tagsRepository.findByTagName(tagName);
				}

				tag.addCard(card);

				//cardsRepository.save(card);
		}
	}
}
