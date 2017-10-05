package br.com.moradas291.site.business.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

/**
 * Created by Fernando on 02/07/2017.
 */

@Entity
@NamedQuery(name = "Aviso.findAll", query = "SELECT i FROM Aviso i")
public class Aviso implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idAviso;

    @NotEmpty
    @NotNull
    private String descricao;

    private Date dataExpiracao;

    private Date dataPostagem;

    private Date dataExibicao;

    @Column(length=16777215)
    private byte[] imagem;

    @Transient
    private MultipartFile multipartFile;

    public Aviso(long idAviso, String descricao, Date dataExpiracao, Date dataPostagem, Date dataExibicao, byte[] imagem, MultipartFile multipartFile) {
        this.idAviso = idAviso;
        this.descricao = descricao;
        this.dataExpiracao = dataExpiracao;
        this.dataPostagem = dataPostagem;
        this.dataExibicao = dataExibicao;
        this.imagem = imagem;
        this.multipartFile = multipartFile;
    }

    public Aviso() {
    }

    public long getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(long idAviso) {
        this.idAviso = idAviso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public Date getDataExibicao() {
        return dataExibicao;
    }

    public void setDataExibicao(Date dataExibicao) {
        this.dataExibicao = dataExibicao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public String getImagemBase64() {
        return Base64.getEncoder().encodeToString(imagem);
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
