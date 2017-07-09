package br.com.moradas291.site.business.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@NamedQuery(name="Permissoes.findAll", query="SELECT s FROM Permissoes s")
public class Permissoes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPermissoes;

	@Column(unique = true)
	private String descPermissoes;

	public long getIdPermissoes() {
		return idPermissoes;
	}

	public void setIdPermissoes(long idPermissoes) {
		this.idPermissoes = idPermissoes;
	}

	public String getDescPermissoes() {
		return descPermissoes;
	}

	public void setDescPermissoes(String descPermissoes) {
		this.descPermissoes = descPermissoes;
	}

	public Permissoes(long idPermissoes, String descPermissoes) {
		super();
		this.idPermissoes = idPermissoes;
		this.descPermissoes = descPermissoes;
	}

	public Permissoes() {
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);

	}

	
}
