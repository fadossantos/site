package br.com.moradas291.site.business.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@NamedQuery(name = "Unidade.findAll", query = "SELECT i FROM Unidade i")
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "Unidade não pode ser nula.")
    @NotEmpty(message = "Unidade não pode ficar em branco")
    private String unidade;

    private String senha;

    @Transient
    private boolean resetSenha;

    @Email
    @NotNull(message = "Email não pode ser nulo")
    @NotEmpty(message = "Email não pode ficar em branco.")
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Permissoes> permissoes;

    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL)
    private List<Morador> moradores;

    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL)
    List<HistoricoVisita> historicoVisitas;

    public Unidade() {
    }

    public boolean isResetSenha() {
        return resetSenha;
    }

    public void setResetSenha(boolean resetSenha) {
        this.resetSenha = resetSenha;
    }

    public List<Morador> getMoradores() {
        return moradores;
    }

    public void setMoradores(List<Morador> moradores) {
        this.moradores = moradores;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Permissoes> getPermissoes() {
        if (permissoes != null) return permissoes;
        else return new ArrayList<Permissoes>();
    }

    public void setPermissoes(List<Permissoes> permissoes) {
        this.permissoes = permissoes;
    }

    public List<HistoricoVisita> getHistoricoVisitas() {
        return historicoVisitas;
    }

    public void setHistoricoVisitas(List<HistoricoVisita> historicoVisitas) {
        this.historicoVisitas = historicoVisitas;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}