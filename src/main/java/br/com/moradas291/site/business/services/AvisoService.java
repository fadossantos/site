package br.com.moradas291.site.business.services;

import br.com.moradas291.site.business.entities.Aviso;
import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.entities.repositories.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AvisoService {

    @Autowired
    private AvisoRepository avisoRepository;

    public AvisoService() {
    }

    public DataTablesOutput<Aviso> findAll(DataTablesInput input) {
        return this.avisoRepository.findAll(input);
    }

    public Aviso findOne(Long idAviso){
        return this.avisoRepository.findOne(idAviso);
    }

    public Iterable<Aviso> findAll() {
        return this.avisoRepository.findAll();
    }    
      
    public Aviso addOrUpdate(final Aviso aviso) {
        return this.avisoRepository.save(aviso);
    }
    
    public void remove(final Long id){
    	this.avisoRepository.delete(id);
    }

}
