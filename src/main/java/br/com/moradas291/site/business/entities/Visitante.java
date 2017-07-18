package br.com.moradas291.site.business.entities;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Fernando on 02/07/2017.
 */

@Entity
@NamedQuery(name = "Visitante.findAll", query = "SELECT i FROM Visitante i")
@PrimaryKeyJoinColumn(name="idPessoa")
public class Visitante extends Pessoa{

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    List<HistoricoVisita> historicoVisitas;

    public Visitante(long idPessoa, String nome, String documento, byte[] foto, MultipartFile multipartFile, List<HistoricoVisita> historicoVisitas) {
        super(idPessoa, nome, documento, foto, multipartFile);
        this.historicoVisitas = historicoVisitas;
    }

    public Visitante() {
        super();
    }

    public List<HistoricoVisita> getHistoricoVisitas() {
        return historicoVisitas;
    }

    public void setHistoricoVisitas(List<HistoricoVisita> historicoVisitas) {
        this.historicoVisitas = historicoVisitas;
    }
}
