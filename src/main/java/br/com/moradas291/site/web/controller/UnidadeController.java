package br.com.moradas291.site.web.controller;

import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.services.DTUnidadeService;
import br.com.moradas291.site.business.services.PermissoesService;
import br.com.moradas291.site.business.services.UnidadeService;
import br.com.moradas291.site.web.mail.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/unidade")
public class UnidadeController {

    @Autowired
    UnidadeService unidadeService;

    @Autowired
    DTUnidadeService dtUnidadeService;

    @Autowired
    PermissoesService permissoesService;

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/gestaoUnidade", method = RequestMethod.GET)
    public ModelAndView gestaoUnidade() {
        ModelAndView mav = new ModelAndView("unidade/gestaoUnidade");
        mav.addObject("listaUnidades", this.unidadeService.findAll());
        return mav;
    }

    @RequestMapping(value = "/adicionarUnidade", method = RequestMethod.GET)
    public ModelAndView adicionarUnidade() {
        ModelAndView mav = new ModelAndView("unidade/adicionarUnidade");
        mav.addObject("unidade", new Unidade());
        return mav;
    }

    @RequestMapping(value = "/adicionarUnidade", method = RequestMethod.POST)
    public String adicionarUnidadePost(@Valid Unidade unidade, BindingResult result, Model model) {
        Unidade unidadeBanco = this.unidadeService.findOne(unidade.getUnidade());
        if (unidadeBanco != null) {
            result.reject("unidade", "Unidade já existente.");
        }
        if (result.hasErrors()) {
            return "unidade/adicionarUnidade";
        } else {
            String senha = gerarSenhaAleatoria();
            unidade.setSenha(new BCryptPasswordEncoder().encode(senha));
            this.unidadeService.addOrUpdate(unidade);
            try {
                smtpMailSender.send(unidade.getEmail(), "Criação de Usuário no site moradas291.com.br", "Bem vindo ao site <a href='http://moradas291.com.br'>Moradas291</a>. <br>Para acessar a área restrita utilize os seguintes dados: <br>Usuário: " + unidade.getUnidade() + "<br>Senha: " + senha);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            model.addAttribute("mensagem", "Unidade nº " + unidade.getUnidade() + " adicionada com sucesso");
            return "unidade/adicionarUnidadeSucesso";
        }
    }

    @RequestMapping(value = "/visualizarUnidade/{idUnidade}", method = RequestMethod.GET)
    public ModelAndView visualizarUnidade(@PathVariable String idUnidade) {
        ModelAndView mav = new ModelAndView("unidade/visualizarUnidade");
        mav.addObject("unidade", this.unidadeService.findOne(idUnidade));
        return mav;
    }

    @RequestMapping(value = "/removerUnidade/{idUnidade}", method = RequestMethod.GET)
    public ModelAndView removerUnidade(@PathVariable String idUnidade) {
        String mensagem = "";
        try {
            this.unidadeService.remove(idUnidade);
            System.out.println("Unidade nº " + idUnidade + " removida com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro na remoção da unidade nº " + idUnidade + "\n\n" + e.getMessage());
        }


        ModelAndView mav = new ModelAndView("unidade/removerUnidadeSucesso");
        mav.addObject("mensagem", "Unidade nº " + idUnidade + " removida com sucesso");
        return mav;
    }

    @RequestMapping(value = "/dtUnidades", method = RequestMethod.POST)
    public ResponseEntity<DataTablesOutput<Unidade>> getInspecoes(@Valid @RequestBody DataTablesInput input) {
        ResponseEntity<DataTablesOutput<Unidade>> resp = new ResponseEntity<DataTablesOutput<Unidade>>(
                dtUnidadeService.findAll(input), HttpStatus.OK);
        return resp;
    }


    @RequestMapping(value = "/editarUnidade/{idUnidade}", method = RequestMethod.GET)
    public ModelAndView editarUnidade(@PathVariable String idUnidade) {
        Unidade unidade = this.unidadeService.findOne(idUnidade);
        ModelAndView mav = new ModelAndView("unidade/editarUnidade");
        mav.addObject("unidade", unidade);
        mav.addObject("listaPermissoes", this.permissoesService.findAll());
        return mav;
    }

    @RequestMapping(value = "/editarUnidade", method = RequestMethod.POST)
    public String editarUnidadePost(@Valid Unidade unidade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaPermissoes", this.permissoesService.findAll());
            return "unidade/editarUnidade";
        } else {
            if (unidade.isResetSenha()) {
                String senha = gerarSenhaAleatoria();
                unidade.setSenha(new BCryptPasswordEncoder().encode(senha));
                this.unidadeService.addOrUpdate(unidade);
                try {
                    smtpMailSender.send(unidade.getEmail(), "Alteração de senha de Usuário no site moradas291.com.br", "Bem vindo ao site <a href='http://moradas291.com.br'>Moradas291</a>. Para acessar a áre restrita utilize os seguintes dados: \nUsuário: " + unidade.getUnidade() + "\nSenha: " + senha);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } else {
                this.unidadeService.addOrUpdate(unidade);
            }
            model.addAttribute("mensagem", "Unidade nº " + unidade.getUnidade() + " editada com sucesso");
            return "unidade/adicionarUnidadeSucesso";
        }
    }

    private static String gerarSenhaAleatoria() {
        int qtdeMaximaCaracteres = 8;
        String[] caracteres = {"a", "1", "b", "2", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z"};

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
    }

}
