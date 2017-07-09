package br.com.moradas291.site.business.entities;

import javax.persistence.*;

/**
 * Created by Fernando on 02/07/2017.
 */
@Entity
@NamedQuery(name = "PrestadorServico.findAll", query = "SELECT i FROM PrestadorServico i")
@PrimaryKeyJoinColumn(name="idPessoa")
public class PrestadorServico extends Pessoa{

    @ManyToOne
    @JoinColumn(name = "unidade")
    private Unidade unidade;

    public PrestadorServico() {
    }

    public PrestadorServico(long idPessoa, String nome, String documento, byte[] foto, Unidade unidade) {
        super(idPessoa, nome, documento, foto);
        this.unidade = unidade;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
}
