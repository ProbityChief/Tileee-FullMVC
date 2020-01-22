package dawan.projet.tileee.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag extends DbObject{
	@ManyToOne
	private User user;
	private String tagName;
	private String rand; 
	@ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Set<Card> cards = new HashSet<>();
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public Set<Card> getCards() {
		return new HashSet<Card>(cards);
	}
	public void addCard(Card card) {
		this.cards.add(card);
	}
	public void removeCard(Card card) {
		this.cards.remove(card);
	}
	
	@Override
	public String toString() {
		return "Tag [user=" + user + ", tagName=" + tagName + ", rand=" + rand + ", cards=" + cards + "]";
	}
}