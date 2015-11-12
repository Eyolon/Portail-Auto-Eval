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
@Table(name="type_d_utilisateur")
public class TypeUtilisateur implements Serializable {
	private static final long serialVersionUID = -1118303401403366726L;
	
	private Long id;
	private String libelle = null;
	
	public TypeUtilisateur(){}
	
	public TypeUtilisateur(Long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public TypeUtilisateur(String libelle) {
		this.libelle = libelle;
	}
	
	@Id 
	@SequenceGenerator(name="type_d_utilisateur_id_seq", sequenceName="type_d_utilisateur_id_seq", allocationSize=1)
	@GeneratedValue(generator = "type_d_utilisateur_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="libelle")
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@Transient
	@Override
	public String toString() {
		return "TypeUtilisateur:{ id:"+id+"\', libelle: \'"+libelle+"\'}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
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
		TypeUtilisateur other = (TypeUtilisateur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}
}
