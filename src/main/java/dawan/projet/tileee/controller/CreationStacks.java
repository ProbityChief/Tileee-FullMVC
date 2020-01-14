package dawan.projet.tileee.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.dao.CardDao;
import dawan.projet.tileee.dao.TagsDAO;
import dawan.projet.tileee.dao.UserDao;
import dawan.projet.tileee.validator.UserValidator;


@Controller
@RequestMapping("/CreationStacks")
public class CreationStacks {

	@PostMapping
	@ResponseBody
	public void ajoutCarte(@RequestBody Card card, User user) {
		TagsDAO tagdao = new TagsDAO("tileee");
		CardDao carddao = new CardDao("tileee");
		UserValidator uservalidator = new UserValidator("tileee");
		Tag tag = null;
		if (((List<Tag>) card.getTags()).get(0) != null 
				&& !tagdao.tagExist((((List<Tag>) card.getTags()).get(0)).getTag_name(), true)) {
			tag = new Tag();
			tag.setTag_name((((List<Tag>) card.getTags()).get(0)).getTag_name());
			String rand = uservalidator.hash(user.getLogin() + "_" + (((List<Tag>) card.getTags()).get(0)).getTag_name());
			tag.setRand(rand);
			tagdao.insert(tag, false);
			tag.addCard(card);
			tagdao.update(tag, false);
		} else if ((((List<Tag>) card.getTags()).get(0)).getTag_name() != null 
				&& tagdao.tagExist((((List<Tag>) card.getTags()).get(0)).getTag_name(), true)) {
			tag = tagdao.findTagByTagname((((List<Tag>) card.getTags()).get(0)).getTag_name(), true);
		}

		card.addTag(tag);
		tag.addCard(card);
		carddao.insert(card, true);
		tagdao.update(tag, true);
	}
}