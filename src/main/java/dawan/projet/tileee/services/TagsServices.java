package dawan.projet.tileee.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.repositories.TagsRepository;
import dawan.projet.tileee.validator.UserValidator;

@Service
public class TagsServices {

	@Autowired
	private TagsRepository tagsRepository;
	
	public void CloneTags(String rand, User user)
	{   
		Tag tag =  tagsRepository.findByRand(rand);
		Set<Card> cards = tag.getCards();
		
		
		Tag cloneTag = new Tag();
		cloneTag.setUser(user);
		cloneTag.setTagName(tag.getTagName());
		cloneTag.setRand(UserValidator.hash(user.getLogin() + "_" + cloneTag.getTagName()));
		tagsRepository.save(cloneTag);
		
		for(Card c : cards) {
			cloneTag.addCard(c);
		}	
	}

	public Tag findByRand(String rand) {
		return tagsRepository.findByRand(rand);
	}
}
