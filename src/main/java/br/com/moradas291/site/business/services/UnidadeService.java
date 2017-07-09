package br.com.moradas291.site.business.services;

import javax.transaction.Transactional;

import br.com.moradas291.site.business.entities.Permissoes;
import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.entities.repositories.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UnidadeService implements UserDetailsService {
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    public UnidadeService() {
        super();
    }  
        
    public Iterable<Unidade> findAll() {
        return this.unidadeRepository.findAll();
    }

    public Unidade findOne(String unidade){
    	return this.unidadeRepository.findOne(unidade);
    }

    public void addOrUpdate(final Unidade unidade) {
        this.unidadeRepository.save(unidade);
    }
    
    public void remove(final String unidade){
    	this.unidadeRepository.delete(unidade);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Unidade unidade = this.findOne(username);
        if (unidade != null){
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<Permissoes> listaPermissoes = unidade.getPermissoes();
                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                for(Permissoes perm : listaPermissoes)
                {
                    grantedAuths.add(new SimpleGrantedAuthority(perm.getDescPermissoes()));
                }
                return grantedAuths;
            }

            @Override
            public String getPassword() {
                return unidade.getSenha();
            }

            @Override
            public String getUsername() {
                return unidade.getUnidade();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        return userDetails;
        }
        else throw new UsernameNotFoundException(username);
    }
}
