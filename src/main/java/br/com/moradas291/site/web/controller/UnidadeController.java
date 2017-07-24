package br.com.moradas291.site.web.controller;

import br.com.moradas291.site.SiteUtils;
import br.com.moradas291.site.business.entities.Morador;
import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.services.DTUnidadeService;
import br.com.moradas291.site.business.services.MoradorService;
import br.com.moradas291.site.business.services.PermissoesService;
import br.com.moradas291.site.business.services.UnidadeService;
import br.com.moradas291.site.web.mail.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;


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

    @Autowired
    MoradorService moradorService;

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
            String senha = SiteUtils.gerarSenhaAleatoria();
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
                String senha = SiteUtils.gerarSenhaAleatoria();
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

    @RequestMapping(value = "/morador/adicionarMorador", method = RequestMethod.GET)
    public ModelAndView adicionarMorador(@RequestParam(value="idUnidade", required=true) String idUnidade) {
        Morador morador = new Morador();
        morador.setUnidade(this.unidadeService.findOne(idUnidade));
        ModelAndView mav = new ModelAndView("unidade/adicionarMorador");
        mav.addObject("unidade", idUnidade);
        mav.addObject("morador", morador);
        return mav;
    }


    @RequestMapping(value = "/morador/adicionarMorador", method = RequestMethod.POST)
    public String adicionarMoradorPost(@Valid Morador morador, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("unidade", morador.getUnidade().getUnidade());
            return "unidade/adicionarMorador";
        } else {
            try {
                morador.setFoto(morador.getMultipartFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.moradorService.addOrUpdate(morador);
        }
        model.addAttribute("mensagem", "Morador adicionado com sucesso.");
        return "unidade/mensagemSucesso";
    }

    @RequestMapping(value = "/morador/editarMorador/{idMorador}", method = RequestMethod.GET)
    public ModelAndView editarMorador(@PathVariable Long idMorador) {
        Morador morador = this.moradorService.findOne(idMorador);
        ModelAndView mav = new ModelAndView("unidade/editarMorador");
        mav.addObject("morador", morador);
        return mav;
    }

    @RequestMapping(value = "/morador/editarMorador", method = RequestMethod.POST)
    public String editarMoradorPost(@Valid Morador morador, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "unidade/editarMorador";
        } else {
            this.moradorService.addOrUpdate(morador);
        }
        model.addAttribute("mensagem", "Morador " + morador.getNome() + " editado com sucesso.");
        return "unidade/mensagemSucesso";
    }

    @RequestMapping(value = "/morador/removerMorador/{idMorador}", method = RequestMethod.GET)
    public ModelAndView removerMorador(@PathVariable Long idMorador) {
        try {
            this.moradorService.remove(idMorador);
            System.out.println("Morador " + idMorador + " removida com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro na remoção do morador " + idMorador + "\n\n" + e.getMessage());
        }
        ModelAndView mav = new ModelAndView("unidade/mensagemSucesso");
        mav.addObject("mensagem", "Morador nº " + idMorador + " removido com sucesso");
        return mav;
    }

    @RequestMapping(value = "/morador/foto/{idMorador}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("idMorador") Long idMorador) throws IOException {
        byte[] imageContent = this.moradorService.findOne(idMorador).getFoto();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/trocarSenha", method = RequestMethod.GET)
    public ModelAndView trocarSenha() {
        String idUnidade = SecurityContextHolder.getContext().getAuthentication().getName();
        ModelAndView mav = new ModelAndView("unidade/trocarSenha");
        mav.addObject("mensagem", "");
        mav.addObject("unidade", idUnidade);
        return mav;
    }

    @RequestMapping(value = "/trocarSenha", method = RequestMethod.POST)
    public String trocarSenhaPost(@RequestParam("senhaAtual") String senhaAtual,
                                  @RequestParam("novaSenha") String novaSenha,
                                  @RequestParam("confirmacaoSenha") String confirmacaoSenha,
                                  @RequestParam("unidade") String unidade, Model model) {
        Unidade _unidade = this.unidadeService.findOne(unidade);
        if(!novaSenha.contentEquals(confirmacaoSenha) || !new BCryptPasswordEncoder().matches(senhaAtual, _unidade.getSenha())){
            String mensagem = "Senhas digitadas não conferem.";
            model.addAttribute("mensagem", mensagem);
            return "unidade/trocarSenha";
        }
        else {

            _unidade.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
            this.unidadeService.addOrUpdate(_unidade);
            try {
                this.smtpMailSender.send(_unidade.getEmail(), "Alteração de Senha", "Sua senha do site moradas291.com.br foi alterada para " + novaSenha);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            String mensagem = "Senha alterada com sucesso.";
            model.addAttribute("mensagem", mensagem);
            return "unidade/trocarSenhaSucesso";
        }
    }

    @RequestMapping(value = "/visualizarMoradores", method = RequestMethod.GET)
    public ModelAndView visualizarMoradores() {
        String idUnidade = SecurityContextHolder.getContext().getAuthentication().getName();
        Unidade unidade = this.unidadeService.findOne(idUnidade);
        ModelAndView mav = new ModelAndView("unidade/visualizarMoradores");
        mav.addObject("mensagem", "");
        mav.addObject("unidade", unidade);
        return mav;
    }

}






