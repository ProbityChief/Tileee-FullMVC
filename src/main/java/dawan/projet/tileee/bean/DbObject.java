package dawan.projet.tileee.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DbObject {
	//S�quence: ligne de dif�rente table incrmente un champ en commun
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//Nom de attribut = nom des champs
	private long id;
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
}
