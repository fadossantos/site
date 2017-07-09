package br.com.moradas291.site.business.entities.repositories;

import br.com.moradas291.site.business.entities.Permissoes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissoesRepository extends CrudRepository<Permissoes, Long> {

    public List<Permissoes> findByDescPermissoes(String descPermissoes);

}
