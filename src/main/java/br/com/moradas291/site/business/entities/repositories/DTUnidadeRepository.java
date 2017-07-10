package br.com.moradas291.site.business.entities.repositories;

import br.com.moradas291.site.business.entities.Unidade;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fernando on 09/07/2017.
 */

@Repository
public interface DTUnidadeRepository extends DataTablesRepository<Unidade, String> {
}
