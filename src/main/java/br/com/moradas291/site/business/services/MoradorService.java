package br.com.moradas291.site.business.services;

import br.com.moradas291.site.business.entities.Morador;
import br.com.moradas291.site.business.entities.repositories.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;


    public MoradorService() {
        super();
    }  

    public Morador findOne(Long idMorador){
        return this.moradorRepository.findOne(idMorador);
    }

    public Iterable<Morador> findAll() {
        return this.moradorRepository.findAll();
    }    
      
    public Morador addOrUpdate(final Morador morador) {
        return this.moradorRepository.save(morador);
    }
    
    public void remove(final Long id){
    	this.moradorRepository.delete(id);
    }

}
