package dawan.projet.tileee.controller;

import java.io.IOException;
import java.sql.Connection;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.dao.CardDao;
import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.dao.TagsDAO;

/**
 * Servlet implementation class Entrainement
 */
@Controller
@RequestMapping("/Entrainement")
public class Entrainement {

	@GetMapping("/{userid}")
	@ResponseBody
	public List<Tag> chargementTags(@PathVariable("userid") long id) {
		TagsDAO tagdao = new TagsDAO("tileee");
		return (List<Tag>) tagdao.findTags(id, true);
	}

	@GetMapping("/{userid}/{tag}/{type}")
	public ArrayList<String> chargementMotCartesDeTag(@PathVariable("userid") long id, 
			@PathVariable("tag") String tag, @PathVariable("type") String type) {
		CardDao carddao = new CardDao("tileee");
		if (type == "mot") {
			List<Card> listCard = carddao.findByTag(id, tag, true);
			ArrayList<String> mot = new ArrayList<String>();
			System.out.println(listCard.get(0).getWord());
			for (Card c : listCard) {
				mot.add(c.getWord());
			}
			return mot;
		} else if (type == "traduction"){
			List<Card> listCard = carddao.findByTag(id, tag, true);
			ArrayList<String> traduction = new ArrayList<String>();
			System.out.println(listCard.get(0).getWord());
			for (Card c : listCard) {
				traduction.add(c.getTranslation());
			}
			return traduction;
		}
		return null;
	}
}