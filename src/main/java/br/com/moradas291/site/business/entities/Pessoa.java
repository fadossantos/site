package br.com.moradas291.site.business.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Fernando on 02/07/2017.
 */

@Entity
@NamedQuery(name = "Pessoa.findAll", query = "SELECT i FROM Pessoa i")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private long idPessoa;

    private String nome;

    private String documento;

    private byte[] foto;

    public Pessoa(long idPessoa, String nome, String documento, byte[] foto) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.documento = documento;
        this.foto = foto;
    }

    public Pessoa() {
    }

    public long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
