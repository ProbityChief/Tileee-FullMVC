package dawan.projet.tileee.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.dao.TagsDAO;

/**
 * Servlet implementation class FinalisationInscription
 */
@Controller
@RequestMapping("/FinalisationInscription")
public class FinalisationInscription {

	@GetMapping("/{rand}")
	@ResponseBody
	public String chargementTags(@PathVariable("rand") String rand) {
		dawan.projet.tileee.dao.UserDao userdao = new dawan.projet.tileee.dao.UserDao("tileee");
		dawan.projet.tileee.bean.User utilisateur = new dawan.projet.tileee.bean.User();
		String userMessage = new String();
    	try {
    	utilisateur = userdao.findByRand(rand, false);

        if(utilisateur.isValidation() == false){
                utilisateur.setValidation(true);
    			userdao.update(utilisateur, true);
                return "Votre compte a été validé! Merci.";
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	
		return "Votre compte a déja été validé! Merci.";
	}
}