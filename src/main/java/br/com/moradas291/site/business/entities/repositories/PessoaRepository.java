package br.com.moradas291.site.business.entities.repositories;

import br.com.moradas291.site.business.entities.Pessoa;
import br.com.moradas291.site.business.entities.Unidade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

}
