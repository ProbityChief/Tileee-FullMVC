package dawan.projet.tileee.bean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends DbObject {
	private String login;
	private String password;
	private String mail;
	private LocalDate registrationdate;
	private boolean validation;
	private String rand;
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<Tag> tags;
	
	public User() {
		super();
	}
    public User(String login, String mail, String password) {
    	this();
		this.login = login;
		this.mail = mail;
		this.password = password;
		this.registrationdate = LocalDate.now();
		this.validation = false;
	}
	public User(String login, String password) {
		this();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public LocalDate getRegistrationdate() {
		return registrationdate;
	}
	public void setRegistrationdate(LocalDate registrationdate) {
		this.registrationdate = registrationdate;
	}
	public boolean isValidation() {
		return validation;
	}
	public void setValidation(boolean validation) {
		this.validation = validation;
	}
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public Set<Tag> getTags() {
		return new HashSet<Tag>(tags);
	}
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	public void removeTag(Tag tag) {
		this.tags.remove(tag);
	}
	
}
