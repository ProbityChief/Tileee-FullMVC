package dawan.projet.tileee.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.LockMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dawan.projet.tileee.dao.UserDao;

@Controller
@RequestMapping("/SuppressionCompte")
public class SuppressionCompte {

	@PostMapping()
	@ResponseBody
	public Boolean supressionCompte(@RequestBody dawan.projet.tileee.bean.User user) {
		UserDao userdao = new UserDao("tileee");
		try {
    		userdao.delete(user, true);
    		return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
