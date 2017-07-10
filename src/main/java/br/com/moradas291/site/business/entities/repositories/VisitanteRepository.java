package br.com.moradas291.site.business.entities.repositories;

import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.entities.Visitante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitanteRepository extends CrudRepository<Visitante, Long> {

}
