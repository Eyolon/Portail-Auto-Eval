package hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="utilisateur")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = -7351729135012380019L;

	private Long id = null;
	private String email = null;
	private String login = null;
	private TypeUtilisateur typeUtilisateur = null;
	private Connexion connexion = null;
	private Service service = null;
	
	public Utilisateur(){}
	
	public Utilisateur(Long id, String email, String login, TypeUtilisateur typeUtilisateur, Connexion connexion, Service service) {
		this.id = id;
		this.email = email;
		this.login = login;
		this.typeUtilisateur = typeUtilisateur;
		this.connexion = connexion;
		this.service = service;
	}
	
	public Utilisateur(String email, String login, TypeUtilisateur typeUtilisateur, Connexion connexion, Service service) {
		this.email = email;
		this.login = login;
		this.typeUtilisateur = typeUtilisateur;
		this.connexion = connexion;
		this.service = service;
	}
	
	@Id
	@SequenceGenerator(name="utilisateur_id_seq", sequenceName="utilisateur_id_seq", allocationSize=1)
	@GeneratedValue(generator = "utilisateur_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_utilisateur", nullable = false)
	public TypeUtilisateur getTypeUtilisateur() {
		return typeUtilisateur;
	}

	public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
		this.typeUtilisateur = typeUtilisateur;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_connexion", nullable = false)
	public Connexion getConnexion() {
		return connexion;
	}

	public void setConnexion(Connexion connexion) {
		this.connexion = connexion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_service", nullable = true)
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Transient
	@Override
	public String toString() {
		return "User:{ id:"+id+", adresseMail: \'"+email+"\', typeUtilisateur: \'"+typeUtilisateur.toString()+"\', connexion: \'"+connexion.toString()+"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connexion == null) ? 0 : connexion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((typeUtilisateur == null) ? 0 : typeUtilisateur.hashCode());
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
		Utilisateur other = (Utilisateur) obj;
		if (connexion == null) {
			if (other.connexion != null)
				return false;
		} else if (!connexion.equals(other.connexion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (typeUtilisateur == null) {
			if (other.typeUtilisateur != null)
				return false;
		} else if (!typeUtilisateur.equals(other.typeUtilisateur))
			return false;
		return true;
	}
}
