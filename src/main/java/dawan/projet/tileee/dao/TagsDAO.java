package dawan.projet.tileee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import dawan.projet.tileee.bean.Card;
import dawan.projet.tileee.bean.Tag;
import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.validator.UserValidator;

//public class TagsDAO extends GenericDao<Tag>{
//	
//	public TagsDAO(EntityManager em) {
//		super(em);
//		// TODO Auto-generated constructor stub
//	}

//	public static ArrayList<String> findTags(Connection cnx, boolean CloseConnection) {
//		ArrayList<String> tagsList = new ArrayList<>();
//		PreparedStatement st;
//		try {
//			st = cnx.prepareStatement("SELECT tag1, tag2, tag3, tag4, tag5 from cards;");
//			ResultSet res = st.executeQuery();
//			while (res.next()) {
//				if ("null".equals(res.getString("tag1")) || tagsList.contains(res.getString("tag1"))) {
//					continue;
//				}
//				else {
//					tagsList.add(res.getString("tag1"));
//					System.out.println("ajout d'un tag");
//				}
//				if ("null".equals(res.getString("tag2")) || !tagsList.contains(res.getString("tag2"))) {
//					continue;
//				}
//				else {
//					tagsList.add(res.getString("tag2"));
//					System.out.println("ajout d'un tag");
//				}
//				if ("null".equals(res.getString("tag3")) || !tagsList.contains(res.getString("tag3"))) {
//					continue;
//				}
//				else {
//					tagsList.add(res.getString("tag3"));
//					System.out.println("ajout d'un tag");
//				}
//				if ("null".equals(res.getString("tag4")) || !tagsList.contains(res.getString("tag4"))) {
//					continue;
//				}
//				else {
//					tagsList.add(res.getString("tag4"));
//					System.out.println("ajout d'un tag");
//				}
//				if ("null".equals(res.getString("tag5")) || !tagsList.contains(res.getString("tag5"))) {
//					continue;
//				}
//				else {
//					tagsList.add(res.getString("tag5"));
//					System.out.println("ajout d'un tag");
//				}
//			}
//			if (CloseConnection) {
//				cnx.close();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return tagsList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dawan.projet.tileee.bean.Tag;

public class TagsDAO extends GenericDao<Tag>{

	private String bdd;
	// Fait comprendre à spring que j'utilise la persistence de JPA
	// Objet EntityManager avec les infos de connexion à la base


	// recherche des tags par mot clé
//	public Set<Tag> findTags(User user, boolean close)
//	{   
//		em = createEntityManager();
//		transaction = em.getTransaction();
//		
//        List<Tag> resultat = null;
//
//		String requete = String.format("SELECT f.tag_name FROM %s f INNER JOIN WHERE f.id = ("
//				+ "SELECT t.id FROM %s t WHERE t.user = %b" + ")", Tag.class.getName(),  Card.class.getName(), user);
//		
//		TypedQuery<Tag> query = em.createQuery(requete, Tag.class);
//		resultat = query.getResultList();
//		Set<Tag> result = new HashSet<>(resultat);
//		
//		if (close)
//			em.close();
//
//		return result;
//	}
//	
//	
	
	public TagsDAO(String bdd) {
		super(bdd);
		this.bdd = bdd;
		// TODO Auto-generated constructor stub
	}

	public Set<Tag> findTags(long userid, boolean close)
	{   
		
        List<Tag> resultat = null;

		String requete = "SELECT * "
				+ "FROM tags "
				+ "INNER JOIN tags_cards ON tags_cards.tags_id = tags.id "
				+ "INNER JOIN cards ON tags_cards.cards_id = cards.id "
				+ "WHERE cards.user_id = " + userid;
		resultat = em.createNativeQuery(requete, Tag.class).getResultList();
		Set<Tag> result = new HashSet<>(resultat);

		
		if (close)
			em.close();

		return result;
	}
	
	public void CloneTags(String rand, User user, boolean close)
	{   
		CardDao carddao = new CardDao("tileee");
		List<Card> listCard = carddao.findByRand(rand, false);
		
		
		Tag tag = new Tag();
		TagsDAO tagsdao = new TagsDAO("tileee");
		Tag tag2 = tagsdao.findTagNameByRand(rand, false);
		String tagaclone = tag2.getTag_name();
		tag.setTag_name(tagaclone);
		String rand2 = UserValidator.hash(user.getLogin() + "_" + tagaclone);
		tag.setRand(rand2);
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(tag);
		tagsdao.insert(tag, false);
		Card card = listCard.get(0);
		for(Card c : listCard) {
			c.setId(0);
			c.setUser(user);
			c.setTags(tags);
			carddao.insert(c, false);
			tag.addCard(c);
		}	
		
		tagsdao.update(tag, true);
		carddao.update(card, true);
		if (close)
			em.close();

	}

	public Tag findTagNameByRand(String rand, boolean b) {
		//A revoir
		List<Tag> listTag = (List<Tag>) em.createNativeQuery("SELECT * FROM tags WHERE rand = \"" + rand +"\"", Tag.class).getResultList();
		Tag tag = listTag.get(0);
		if (b)
			em.close();

		return tag;
	}
	
	public Tag findTagByTagname(String tagName, boolean b) {
		
		Tag tag = (Tag) em.createNativeQuery("SELECT * FROM tags WHERE tag_name = \"" + tagName +"\"", Tag.class).getSingleResult();
		
		if (b)
			em.close();

		return tag;
	}

	public boolean tagExist(String tagName, boolean b) {
		// TODO Auto-generated method stub
		try {
		Tag tag = (Tag) em.createNativeQuery("SELECT * FROM tags WHERE tag_name = \"" + tagName +"\"", Tag.class).getSingleResult();
		return true;
		} catch (Exception e){
			e.printStackTrace();
		} finally {
		if (b)
			em.close();
		return false;
		}
	}
	

}
