package br.com.moradas291.site.business.entities;

import javax.persistence.*;

/**
 * Created by Fernando on 02/07/2017.
 */

@Entity
@NamedQuery(name = "Morador.findAll", query = "SELECT i FROM Morador i")
@PrimaryKeyJoinColumn(name="idPessoa")
public class Morador extends Pessoa{

    @ManyToOne
    @JoinColumn(name = "unidade")
    private Unidade unidade;

    private boolean responsavel;

    public Morador(long idPessoa, String nome, String documento, byte[] foto, Unidade unidade, boolean responsavel) {
        super(idPessoa, nome, documento, foto);
        this.unidade = unidade;
        this.responsavel = responsavel;
    }

    public Morador() {
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public boolean isResponsavel() {
        return responsavel;
    }

    public void setResponsavel(boolean responsavel) {
        this.responsavel = responsavel;
    }
}
