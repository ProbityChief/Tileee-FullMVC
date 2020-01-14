package dawan.projet.tileee.controller;

import java.io.IOException;
import java.sql.Connection;
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

import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.dao.TagsDAO;


@Controller
@RequestMapping("/Invitation")
public class Invitation {

	@GetMapping("/{userid}")
	@ResponseBody
	public List<String> listTag(@PathVariable("userid") long id) {
		TagsDAO tagsdao = new TagsDAO("tileee");
		Set<Tag> tagsList= tagsdao.findTags(id, true);
		List<String> tags = new ArrayList<String>();
		for(Tag T : tagsList) {
			tags.add(T.getTag_name());
		}
		return tags;
	}
	
	@PostMapping()
	@ResponseBody
	public void copieTag(@RequestBody String rand, @RequestBody User user) {
		TagsDAO tagsdao = new TagsDAO("tileee");
		tagsdao.CloneTags(rand, user, false);
	}
}
