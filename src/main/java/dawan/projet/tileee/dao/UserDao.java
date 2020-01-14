package dawan.projet.tileee.dao;

import dawan.projet.tileee.bean.DbObject;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.tool.StringFunctions;
import dawan.projet.tileee.validator.UserValidator;
import dawan.projet.tileee.bean.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserDao extends GenericDao<User> {
	
	private String bdd;
	
	public UserDao(String bdd) {
		super(bdd);
		this.bdd = bdd;
		// TODO Auto-generated constructor stub
	}

	public void insert(User user, boolean close) {
		UserValidator uservalidator = new UserValidator("tileee");
		if (user != null && user.getId() == 0) {

			user.setPassword(UserValidator.hashPassword(user.getPassword()));
	
			try {
				// début de la transaction
				transaction.begin();

				// On insère la formation dans la BDD
				em.persist(user);

				// on commit tout ce qui s'est fait dans la transaction
				transaction.commit();
			} catch (Exception ex) {
				// en cas d'erreur, on effectue un rollback
				transaction.rollback();
				ex.printStackTrace();
			} finally {
				if(close) {
				em.close();
				}
			}
		}
	}

	// /**
//	 * Teste la validit� du format de l'adresse email envoy�e par l'utilisateur
//	 * @param email
//	 * Une adresse email, de la forme utilisateur@domain.ext
//	 * @return
//	 * true si l'adresse email fournie respecte le format d�fini par l'expression r�guli�re
//	 */
//	public static List<User> findAll(Connection connection, boolean willBeClosed) throws Exception {
//		List<User> list = new ArrayList<>();
//		String mySql = "SELECT * FROM utilisateur";
//
//		PreparedStatement stmt = connection.prepareStatement(mySql);
//		ResultSet rs = stmt.executeQuery();
//		while (rs.next()) {
//			User u = new User();
//			u.setName(rs.getString("login"));
//			u.setMail(rs.getString("email"));
//			u.setPassword(rs.getString("mdpMD5"));
//			u.setValidation(rs.getBoolean("validation"));
//			list.add(u);
//
//		}
//		rs.close();
//		if (willBeClosed)
//			connection.close();
//
//		return list;
//	}
//	
	public User findByName(String login, boolean closeCnx) {
		
//		String requete = String.format("SELECT f FROM %s f WHERE f.login = %s", User.class.getName(), login);
		
//		TypedQuery<User> query = em.createQuery(requete, User.class);
//		User user = query.getSingleResult();
		User user = (User) em.createNativeQuery("SELECT * FROM users WHERE login = \"" + login +"\"", User.class).getSingleResult();
		
		if (closeCnx)
			em.close();

		return user;
	}
	
	public User findByRand(String rand, boolean closeCnx) {

//		String requete = String.format("SELECT f FROM %s f WHERE f.rand = %s", User.class.getName(), rand);
//		
//		TypedQuery<User> query = em.createQuery(requete, User.class);
		User user = (User) em.createNativeQuery("SELECT * FROM users WHERE rand =" + rand, User.class).getSingleResult();
//		User user = query.getSingleResult();
		
		if (closeCnx)
			em.close();

		return user;
	}
//
//	public static int insert(User user, Connection cnx, boolean willConnectionClosed) throws Exception {
//		String password = user.getPassword();
//		Date now = new java.util.Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date = sdf.format(now);
//		password = hashPassword(password);
//		PreparedStatement stmt = cnx.prepareStatement(
//				"INSERT INTO users (name, pass, mail, dateinscription, validation, hashcode) "
//				+ "VALUES(?,?,?,?,?,?)");
//		stmt.setString(1, user.getName().trim());
//		stmt.setString(2, password);
//		stmt.setString(3, user.getMail().trim());
//		stmt.setString(4, date);
//		stmt.setBoolean(5, false);
//		stmt.setString(6, user.getRand());
//		int resultSet = stmt.executeUpdate();
//		if (willConnectionClosed)
//			cnx.close();
//		return resultSet;
//	}
//
//	public static int delete(Integer id, Connection connection, Boolean willBeClosed) throws Exception {
//		PreparedStatement ps = connection.prepareStatement("DELETE FROM utilisateur WHERE id=?");
//		ps.setInt(1, id);
//		int resultSet = ps.executeUpdate();
//		if (willBeClosed)
//			connection.close();
//		return resultSet;
//	}
//
//	public static int update(Integer id, Integer role, Connection connection, Boolean willBeClosed) throws Exception {
//		PreparedStatement ps = connection.prepareStatement("UPDATE utilisateur SET role=? WHERE id=?");
//		ps.setInt(1, role);
//		ps.setInt(2, id);
//		int resultSet = ps.executeUpdate();
//
//		if (willBeClosed)
//			connection.close();
//		return resultSet;
//	}
//
	public void update(User user, Boolean close) {
		String requete = String.format("UPDATE %s f SET validation=1 WHERE f.id=%d", User.class.getName(), user.getId());
		
		TypedQuery<User> query = em.createQuery(requete, User.class);
		query.executeUpdate();
		
		if (close)
			em.close();
	}
	
	
	public Boolean doesEmailExist(String mail, boolean close) {

		Boolean result = false;
		String requete = String.format("SELECT f FROM %s f WHERE f.mail = %s", 
				User.class.getName(), mail);
		
		TypedQuery<User> query = em.createQuery(requete,
				User.class);
		List<User> resultat = query.getResultList();
		if (resultat!=null) {
			result = true;
		}

		//Le fait de faire un appel au set de formateurs de la formation
		//va charger les formatteur dans la formation
		//Hibernate se charge de r�cup�re les donn�es de la table t_formation_formation
		
		if (close)
			em.close();
		return result;
	}


	public Boolean pswAndLoginMatche(String login, String typedPassword, boolean close){
		
		String requete = String.format("SELECT f FROM %s f WHERE f.login = %s", 
				User.class.getName(), login);
		
		TypedQuery<User> query = em.createQuery(requete,
				User.class);
		User resultat = query.getSingleResult();
		
		if (close)
			em.close();
		if (UserValidator.hashPassword(typedPassword) != resultat.getPassword()) {
			return false;
		}

		//Le fait de faire un appel au set de formateurs de la formation
		//va charger les formatteur dans la formation
		//Hibernate se charge de r�cup�re les donn�es de la table t_formation_formation
		return true;
	}

	public User findByMail(String mail, Boolean closeCnx) {

//		String requete = String.format("SELECT f FROM %s f WHERE f.rand = %s", User.class.getName(), rand);
//		
//		TypedQuery<User> query = em.createQuery(requete, User.class);
		User user = (User) em.createNativeQuery("SELECT * FROM users WHERE mail =" + "\"" + mail +"\"", User.class).getSingleResult();
//		User user = query.getSingleResult();
		
		if (closeCnx)
			em.close();

		return user;
	}
}
