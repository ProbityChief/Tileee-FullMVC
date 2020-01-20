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

	public void insert(Card card, String tagName, User user) {
		if (card != null && card.getId() == 0) {
				boolean tagExists = tagsRepository.findByTagName(tagName) != null;
				Tag tag = null;

				if (!tagExists) {
					tag = new Tag();
					tag.setUser(user);
					tag.setTagName(tagName);
					tag.setRand(UserValidator.hash(user.getLogin() + "_" + tagName));
					tagsRepository.save(tag);
				} else {
					tag = tagsRepository.findByTagName(tagName);
				}

				tag.addCard(card);

				//cardsRepository.save(card);
		}
	}
}
