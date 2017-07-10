package br.com.moradas291.site.business.services;

import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.entities.repositories.DTUnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Fernando on 09/07/2017.
 */
@Service
@Transactional
public class DTUnidadeService {

    @Autowired
    private DTUnidadeRepository dtUnidadeRepository;

    public DataTablesOutput<Unidade> findAll(DataTablesInput input) {
        return this.dtUnidadeRepository.findAll(input);
    }
}
