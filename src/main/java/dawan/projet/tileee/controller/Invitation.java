package dawan.projet.tileee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.services.TagsServices;
import dawan.projet.tileee.services.UsersServices;


@RestController
@RequestMapping("/invitation")
public class Invitation {

	@Autowired
	private UsersServices usersServices;
	
	@Autowired
	private TagsServices tagsServices;
	
	@GetMapping("/{userid}")
	@ResponseBody
	public List<String> listTag(@PathVariable("userid") int id) {
		User user = (User)usersServices.findById(id);
			
		Set<Tag> tagsList = user.getTags();
		List<String> nomTags = new ArrayList<String>();
		for(Tag T : tagsList) {
			nomTags.add(T.getTagName());
		}
		return nomTags;
	}
	
	@PostMapping()
	@ResponseBody
	public void copieTag(@RequestBody String rand, @RequestBody User user) {
		tagsServices.CloneTags(rand, user);
	}
}
