package br.com.moradas291.site.business.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idPessoa;

    @NotEmpty
    @NotNull
    private String nome;

    @NotEmpty
    @NotNull
    private String documento;

    @Column(length=16777215)
    private byte[] foto;

    @Transient
    private MultipartFile multipartFile;



    public Pessoa() {
    }

    public Pessoa(long idPessoa, String nome, String documento, byte[] foto, MultipartFile multipartFile) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.documento = documento;
        this.foto = foto;
        this.multipartFile = multipartFile;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
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
