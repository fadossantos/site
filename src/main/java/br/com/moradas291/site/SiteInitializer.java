package br.com.moradas291.site;

import br.com.moradas291.site.business.entities.Permissoes;
import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.services.PermissoesService;
import br.com.moradas291.site.business.services.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 02/07/2017.
 */
@Component
public class SiteInitializer {

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private PermissoesService permissoesService;


    private String resetPassword = "qwert";
    private String adminUsername = "admin";
    private String moradorUserName = "153A";
    private String senha = "153A";
    private String descPermissao = "Administrador";
    private String descPermissao1 = "Morador";

    @PostConstruct
    private void init() {
        buildAdmin();
    }

    private void buildAdmin() {
        //here I try to retrieve the Admin from my persistence layer
        Unidade admin = unidadeService.findOne(adminUsername);
        Unidade morador = unidadeService.findOne(moradorUserName);
        Permissoes permissao = permissoesService.findByDescPermissoes(descPermissao);
        Permissoes permissao1 = permissoesService.findByDescPermissoes(descPermissao1);

        try {
            if (permissao == null) {
                permissao = new Permissoes(0, descPermissao);
                permissao = permissoesService.addOrUpdate(permissao);

                permissao1 = new Permissoes(0, descPermissao1);
                permissao1 = permissoesService.addOrUpdate(permissao1);
            }
            //If the application is started for the first time (e.g., the admin is not in the DB)
            if (admin == null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                //create a user for the admin
                admin = new Unidade();
                //and fill her attributes accordingly
                admin.setUnidade(adminUsername);
                admin.setSenha(passwordEncoder.encode(resetPassword));
                List<Permissoes> permissoesList = new ArrayList<Permissoes>();
                permissoesList.add(permissao);
                permissoesList.add(permissao1);
                admin.setPermissoes(permissoesList);

            }
            if (morador == null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                //create a user for the admin
                morador = new Unidade();
                //and fill her attributes accordingly
                morador.setUnidade(moradorUserName);
                morador.setSenha(passwordEncoder.encode(senha));
                List<Permissoes> permissoesList = new ArrayList<Permissoes>();
                permissoesList.add(permissao1);
                morador.setPermissoes(permissoesList);

            }
            unidadeService.addOrUpdate(admin);
            unidadeService.addOrUpdate(morador);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro na inicializacao, gentileza verificar.");
        }
    }
}

