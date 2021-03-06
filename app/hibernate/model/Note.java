package hibernate.model;

import java.io.Serializable;
import java.time.Instant;

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
@Table(name="note")
public class Note implements Serializable {
	private static final long serialVersionUID = -3302569169408641130L;
	
	private Long id = null;
	private Integer valeur = null;
	private String remarque = null;
	private Question question = null;
	private Utilisateur utilisateur = null;
	private Instant dateSaisie = null;
	private Integer gravite = null;
	private String axeAmelioration1 = null;
	private String axeAmelioration2 = null;
	private Integer amelioration = null;
	
	
	public Note(){}
	
	public Note(Long id, Integer valeur, String remarque, Question question, Utilisateur utilisateur, Integer gravite ,String axeAmelioration1, String axeAmelioration2, Integer amelioration) {
		this.id = id;
		this.valeur = valeur;
		this.remarque = remarque;
		this.setQuestion(question);
		this.setUtilisateur(utilisateur);
		this.gravite = gravite;
		this.axeAmelioration1 = axeAmelioration1;
		this.axeAmelioration2 = axeAmelioration2;
		this.amelioration = amelioration;
	}
	
	public Note(Integer valeur, String remarque, Question question, Utilisateur utilisateur, Integer gravite,String axeAmelioration1, String axeAmelioration2, Integer amelioration) {
		this.valeur = valeur;
		this.remarque = remarque;
		this.setQuestion(question);
		this.setUtilisateur(utilisateur);
		this.gravite = gravite;
		this.axeAmelioration1 = axeAmelioration1;
		this.axeAmelioration2 = axeAmelioration2;
		this.amelioration = amelioration;
	}
	
	@Id 
	@SequenceGenerator(name="note_id_seq", sequenceName="note_id_seq", allocationSize=1)
	@GeneratedValue(generator = "note_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="valeur")
	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}

	@Column(name="remarque")
	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	
	@Column(name="axeAmelioration1")
	public String getAxeAmelioration1() {
		return axeAmelioration1;
	}

	public void setAxeAmelioration1(String axeAmelioration1) {
		this.axeAmelioration1 = axeAmelioration1;
	}
	
	@Column(name="axeAmelioration2")
	public String getAxeAmelioration2() {
		return axeAmelioration2;
	}

	public void setAxeAmelioration2(String axeAmelioration2) {
		this.axeAmelioration2 = axeAmelioration2;
	}
	
	@Column(name="gravite")
	public Integer getGravite() {
		return gravite;
	}

	public void setGravite(Integer gravite) {
		this.gravite = gravite;
	}
	
	@Column(name="amelioration")
	public Integer getAmelioration() {
		return amelioration;
	}

	public void setAmelioration(Integer amelioration) {
		this.amelioration = amelioration;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_question", nullable = false)
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur", nullable = false)
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Column(name="date_saisie")
	public Instant getDateSaisie() {
		return dateSaisie;
	}
	 
	public void setDateSaisie(Instant dateSaisie) {
		this.dateSaisie = dateSaisie;
	}
	 
	@Transient
	@Override
	public String toString() {
		return "Note:{ id:"+id+"\', valeur: \'"+valeur+"\', remarque: \'"+remarque+"\'}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((remarque == null) ? 0 : remarque.hashCode());
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
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
		Note other = (Note) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (remarque == null) {
			if (other.remarque != null)
				return false;
		} else if (!remarque.equals(other.remarque))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		return true;
	}
}
