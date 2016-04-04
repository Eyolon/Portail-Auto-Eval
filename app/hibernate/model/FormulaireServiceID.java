package hibernate.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class FormulaireServiceID implements Serializable {
	
	private static final long serialVersionUID = -5338242583228183174L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_formulaire", nullable=false)
	private Formulaire formulaire;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_service", nullable=false)
	private Service service;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_type_utilisateur", nullable=false)
	private TypeUtilisateur typeUtilisateur;
	
	public FormulaireServiceID(){}
	
	public FormulaireServiceID(Formulaire formulaire, Service service) {
		this.formulaire = formulaire;
		this.service = service;
	}
	
	public FormulaireServiceID(Formulaire formulaire, Service service, TypeUtilisateur typeUtilisateur) {
		this.formulaire = formulaire;
		this.service = service;
		this.typeUtilisateur = typeUtilisateur;
	}
	
	public Formulaire getFormulaire() {
		return formulaire;
	}

	public void setFormulaire(Formulaire formulaire) {
		this.formulaire = formulaire;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public TypeUtilisateur getTypeUtilisateur() {
		return typeUtilisateur;
	}

	public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
		this.typeUtilisateur = typeUtilisateur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formulaire == null) ? 0 : formulaire.hashCode());
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
		FormulaireServiceID other = (FormulaireServiceID) obj;
		if (formulaire == null) {
			if (other.formulaire != null)
				return false;
		} else if (!formulaire.equals(other.formulaire))
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

	@Transient
	@Override
	public String toString(){
		return"FormulaireServiceID:{formulaire:"+formulaire+", service: \'"+service+"\'}";
	}
}
