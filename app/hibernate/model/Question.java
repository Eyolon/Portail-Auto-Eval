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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="question")
public class Question implements Serializable {
	private static final long serialVersionUID = -5053421785400930573L;
	
	private Long id = null;
	private String valeur = null;
	private Formulaire formulaire = null;
	private Critere critere = null;
	
	public Question(){}
	
	public Question(Long id, String libelle, Formulaire formulaire, Critere critere) {
		this.id = id;
		this.valeur = libelle;
		this.formulaire = formulaire;
		this.critere = critere;
	}
	
	public Question(String libelle, Formulaire formulaire, Critere critere) {
		this.valeur = libelle;
		this.formulaire = formulaire;
		this.critere = critere;
	}
	
	@Id 
	@SequenceGenerator(name="question_id_seq", sequenceName="question_id_seq", allocationSize=1)
	@GeneratedValue(generator = "question_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="valeur")
	public String getValeur() {
		return valeur;
	}
	
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_formulaire", nullable = false)
	public Formulaire getFormulaire() {
		return formulaire;
	}

	public void setFormulaire(Formulaire formulaire) {
		this.formulaire = formulaire;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_critere", nullable = false)
	public Critere getCritere() {
		return critere;
	}

	public void setCritere(Critere critere) {
		this.critere = critere;
	}

	@Transient
	@Override
	public String toString() {
		return "Question:{ id:"+id+"\', valeur: \'"+valeur+"\'}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((critere == null) ? 0 : critere.hashCode());
		result = prime * result + ((formulaire == null) ? 0 : formulaire.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
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
		Question other = (Question) obj;
		if (critere == null) {
			if (other.critere != null)
				return false;
		} else if (!critere.equals(other.critere))
			return false;
		if (formulaire == null) {
			if (other.formulaire != null)
				return false;
		} else if (!formulaire.equals(other.formulaire))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		return true;
	}
}
