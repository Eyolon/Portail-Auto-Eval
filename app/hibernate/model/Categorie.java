package hibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="categorie")
public class Categorie implements Serializable {
	private static final long serialVersionUID = 7106347193340059067L;
	
	private Long id = null;
	private String nom = null;
	private Set<Critere> criteres = null;
	
	public Categorie(){}
	
	public Categorie(Long id, String libelle) {
		this.id = id;
		this.nom = libelle;
	}
	
	public Categorie(String libelle) {
		this.nom = libelle;
	}
	
	@Id 
	@SequenceGenerator(name="categorie_id_seq", sequenceName="categorie_id_seq", allocationSize=1)
	@GeneratedValue(generator = "categorie_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="nom")
	public String getLibelle() {
		return nom;
	}
	
	public void setLibelle(String libelle) {
		this.nom = libelle;
	}
	
	@OneToMany(mappedBy="categorie")
	public Set<Critere> getCriteres() {
		return criteres;
	}

	public void setCriteres(Set<Critere> criteres) {
		this.criteres = criteres;
	}

	@Transient
	@Override
	public String toString() {
		return "Categorie:{ id:"+id+"\', libelle: \'"+nom+"\'}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		Categorie other = (Categorie) obj;
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
		return true;
	}
}
