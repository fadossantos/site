package br.com.moradas291.site.business.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@NamedQuery(name = "Unidade.findAll", query = "SELECT i FROM Unidade i")
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String unidade;

    private String senha;

    private String email;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Permissoes> permissoes;

    public Unidade() {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}