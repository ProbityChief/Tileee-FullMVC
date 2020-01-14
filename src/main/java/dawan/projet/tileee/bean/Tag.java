package dawan.projet.tileee.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

@Entity
@Table(name = "tags")
public class Tag extends DbObject{
	private String tag_name;
	@ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Set<Card> cards = new HashSet<>();
	private String rand; 

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public void addCard(Card card) {
		this.cards.add(card);
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	
	public String toString() {
		return tag_name;
	}

	public void setCard(Set<Card> cards2) {
		this.cards = cards2;
		
	}
}
