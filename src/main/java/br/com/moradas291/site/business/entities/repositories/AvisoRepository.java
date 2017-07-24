package br.com.moradas291.site.business.entities.repositories;

import br.com.moradas291.site.business.entities.Aviso;
import br.com.moradas291.site.business.entities.Pessoa;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisoRepository extends DataTablesRepository<Aviso, Long> {

}
