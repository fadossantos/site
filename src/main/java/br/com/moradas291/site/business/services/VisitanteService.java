package br.com.moradas291.site.business.services;

import br.com.moradas291.site.business.entities.Visitante;
import br.com.moradas291.site.business.entities.repositories.VisitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VisitanteService {

    @Autowired
    private VisitanteRepository visitanteRepository;


    public VisitanteService() {
        super();
    }  

    public Visitante findOne(Long idVisitante){
        return this.visitanteRepository.findOne(idVisitante);
    }

    public Iterable<Visitante> findAll() {
        return this.visitanteRepository.findAll();
    }    
      
    public Visitante addOrUpdate(final Visitante visitante) {
        return this.visitanteRepository.save(visitante);
    }
    
    public void remove(final Long id){
    	this.visitanteRepository.delete(id);
    }
    
}
