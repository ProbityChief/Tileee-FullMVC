package dawan.projet.tileee.bean;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cards")
public class Card extends DbObject{	
	private String word;
	private String translation;
	private String starting_language;
	private String ending_language;
	private double value;
	@ManyToOne
	private User user;
	@ManyToMany(mappedBy="cards", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Set<Tag> tags = new HashSet<>();

	public Set<Tag> getTags() {
		return (new HashSet<Tag>(tags));
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public String getStarting_language() {
		return starting_language;
	}
	public void setStarting_language(String starting_language) {
		this.starting_language = starting_language;
	}
	public String getEnding_language() {
		return ending_language;
	}
	public void setEnding_language(String ending_language) {
		this.ending_language = ending_language;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Card [word=" + word + ", translation=" + translation + ", starting_language=" + starting_language
				+ ", ending_language=" + ending_language + ", value=" + value + "]";
	}
}
