package hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="connexion")
public class Connexion implements Serializable {
	private static final long serialVersionUID = -7601578587341446093L;
	
	private Long id;
	private String motdepasse = null;
	
	public Connexion(){}
	
	public Connexion(Long id, String motdepasse) {
		this.id = id;
		this.motdepasse = motdepasse;
	}
	
	public Connexion(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	
	@Id 
	@SequenceGenerator(name="connexion_id_seq", sequenceName="connexion_id_seq", allocationSize=1)
	@GeneratedValue(generator = "connexion_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="motdepasse")
	public String getPassword() {
		return motdepasse;
	}
	
	public void setPassword(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	
	@Transient
	@Override
	public String toString() {
		return "Connexion:{ id:"+id+"\', password: \'"+motdepasse+"\'}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((motdepasse == null) ? 0 : motdepasse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connexion other = (Connexion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motdepasse == null) {
			if (other.motdepasse != null)
				return false;
		} else if (!motdepasse.equals(other.motdepasse))
			return false;
		return true;
	}
}
