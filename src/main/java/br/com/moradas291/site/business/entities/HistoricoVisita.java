package br.com.moradas291.site.business.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NamedQuery(name = "HistoricoVisita.findAll", query = "SELECT i FROM HistoricoVisita i")
public class HistoricoVisita implements Serializable {

    @Id
    Long idHistoricoVisita;

    @Temporal(TemporalType.TIMESTAMP)
    Date dateTime;

    String observacao;

    @ManyToOne
    @JoinColumn(name = "idUnidade")
    Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    Pessoa pessoa;

    public HistoricoVisita() {
    }

    public HistoricoVisita(Long idHistoricoVisita, Date dateTime, String observacao, Unidade unidade, Pessoa pessoa) {
        this.idHistoricoVisita = idHistoricoVisita;
        this.dateTime = dateTime;
        this.observacao = observacao;
        this.unidade = unidade;
        this.pessoa = pessoa;
    }

    public Long getIdHistoricoVisita() {
        return idHistoricoVisita;
    }

    public void setIdHistoricoVisita(Long idHistoricoVisita) {
        this.idHistoricoVisita = idHistoricoVisita;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}