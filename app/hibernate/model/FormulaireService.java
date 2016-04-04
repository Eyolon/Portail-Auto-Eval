package hibernate.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="formulaire_service")
public class FormulaireService implements Serializable {
	
	private static final long serialVersionUID = 6919408675899250239L;
	
	@EmbeddedId
	private FormulaireServiceID formulaireServiceID;
	
	public FormulaireService(){}
	
	public FormulaireService(FormulaireServiceID formulaireServiceID) {
		this.formulaireServiceID = formulaireServiceID;
	}

	public FormulaireServiceID getFormulaireServiceID() {
		return formulaireServiceID;
	}

	public void setFormulaireServiceID(FormulaireServiceID formulaireServiceID) {
		this.formulaireServiceID = formulaireServiceID;
	}
	
	@Transient
	@Override
	public String toString(){
		return"FormulaireService:{FormulaireServiceID:"+formulaireServiceID+"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formulaireServiceID == null) ? 0 : formulaireServiceID.hashCode());
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
		FormulaireService other = (FormulaireService) obj;
		if (formulaireServiceID == null) {
			if (other.formulaireServiceID != null)
				return false;
		} else if (!formulaireServiceID.equals(other.formulaireServiceID))
			return false;
		return true;
	}
	
}
