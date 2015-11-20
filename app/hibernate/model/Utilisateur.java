package hibernate.model;

import java.io.Serializable;
import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import Tools.Tools;

@Entity
@Table(name="utilisateur")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = -7351729135012380019L;

	private Long id = null;
	private String nom = null;
	private String prenom = null;
	private Calendar datenaissance = null;
	private String adresseRue = null;
	private Ville ville = null;
	private String adresseCodePostal = null;
	private String adresseMail = null;
	private String telephone = null;
	private TypeUtilisateur typeUtilisateur = null;
	private Connexion connexion = null;
	private Genre genre = null;
	
	public Utilisateur(){}
	
	public Utilisateur(Long id, String nom, String prenom, Calendar datenaissance, String adresseRue, Ville ville, String adresseMail, String telephone, TypeUtilisateur typeUtilisateur, Connexion connexion, Genre genre) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.datenaissance = datenaissance;
		this.adresseRue = adresseRue;
		this.ville = ville;
		this.adresseMail = adresseMail;
		this.telephone = telephone;
		this.typeUtilisateur = typeUtilisateur;
		this.connexion = connexion;
		this.genre = genre;
	}
	
	public Utilisateur(String nom, String prenom, Calendar datenaissance, String adresseRue, Ville ville, String adresseMail, String telephone, TypeUtilisateur typeUtilisateur, Connexion connexion, Genre genre) {
		this.nom = nom;
		this.prenom = prenom;
		this.datenaissance = datenaissance;
		this.adresseRue = adresseRue;
		this.ville = ville;
		this.adresseMail = adresseMail;
		this.telephone = telephone;
		this.typeUtilisateur = typeUtilisateur;
		this.connexion = connexion;
		this.genre = genre;
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
	
	@Column(name="nom")
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Column(name="prenom")
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name="datenaissance")
	@Temporal(TemporalType.DATE)
	public Calendar getDateNaissance() {
		return datenaissance;
	}

	public void setDateNaissance(Calendar datenaissance) {
		this.datenaissance = datenaissance;
	}
	
	@Column(name="adresse_rue")
	public String getAdresse() {
		return adresseRue;
	}

	public void setAdresse(String adresse) {
		this.adresseRue = adresse;
	}

	@Column(name="adresse_mail")
	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_d_utilisateur", nullable = false)
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
	@JoinColumn(name = "id_genre", nullable = false)
	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ville", nullable = false)
	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	@Column(name="adresse_code_postal")
	public String getAdresseCodePostal() {
		return adresseCodePostal;
	}

	public void setAdresseCodePostal(String adresseCodePostal) {
		this.adresseCodePostal = adresseCodePostal;
	}

	@Transient
	@Override
	public String toString() {
		return "User:{ id:"+id+", prenom: \'"+prenom+"\' , nom: \'"+nom+"\', dateanniversaire: \'"+Tools.formatDateToDisplay(datenaissance)+"\'"+
				"\', adresse: \'"+adresseRue+ " " + adresseCodePostal + " " + ville +"\', adresseMail: \'"+adresseMail+"\', telephone: \'"+telephone+"\', genre: \'"+genre.toString()+"\', typeUtilisateur: \'"+typeUtilisateur.toString()+"\', connexion: \'"+connexion.toString()+"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresseRue == null) ? 0 : adresseRue.hashCode());
		result = prime * result
				+ ((adresseMail == null) ? 0 : adresseMail.hashCode());
		result = prime * result
				+ ((connexion == null) ? 0 : connexion.hashCode());
		result = prime * result
				+ ((datenaissance == null) ? 0 : datenaissance.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result
				+ ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result
				+ ((typeUtilisateur == null) ? 0 : typeUtilisateur.hashCode());
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
		if (adresseRue == null) {
			if (other.adresseRue != null)
				return false;
		} else if (!adresseRue.equals(other.adresseRue))
			return false;
		if (adresseMail == null) {
			if (other.adresseMail != null)
				return false;
		} else if (!adresseMail.equals(other.adresseMail))
			return false;
		if (connexion == null) {
			if (other.connexion != null)
				return false;
		} else if (!connexion.equals(other.connexion))
			return false;
		if (datenaissance == null) {
			if (other.datenaissance != null)
				return false;
		} else if (!datenaissance.equals(other.datenaissance))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (typeUtilisateur == null) {
			if (other.typeUtilisateur != null)
				return false;
		} else if (!typeUtilisateur.equals(other.typeUtilisateur))
			return false;
		return true;
	}
}
