package br.com.moradas291.site.web.controller;

import br.com.moradas291.site.SiteUtils;
import br.com.moradas291.site.business.entities.Aviso;
import br.com.moradas291.site.business.entities.Unidade;
import br.com.moradas291.site.business.services.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;


@Controller
@RequestMapping(value = "/aviso")
public class QuadroDeAvisosController {

    @Autowired
    AvisoService avisoService;

    @RequestMapping(value = "/gestaoAvisos", method = RequestMethod.GET)
    public ModelAndView gestaoAvisos() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("aviso/gestaoAvisos");
        return mav;
    }

    @RequestMapping(value = "/dtAvisos", method = RequestMethod.POST)
    public ResponseEntity<DataTablesOutput<Aviso>> getAvisos(@Valid @RequestBody DataTablesInput input) {
        ResponseEntity<DataTablesOutput<Aviso>> resp = new ResponseEntity<DataTablesOutput<Aviso>>(
                avisoService.findAll(input), HttpStatus.OK);
        return resp;
    }

    @RequestMapping(value = "/adicionarAviso", method = RequestMethod.GET)
    public ModelAndView adicionarAviso() {
        ModelAndView mav = new ModelAndView("aviso/adicionarAviso");
        mav.addObject("aviso", new Aviso());
        return mav;
    }


    @RequestMapping(value = "/adicionarAviso", method = RequestMethod.POST)
    public String adicionarUnidadePost(@Valid Aviso aviso, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "aviso/adicionarAviso";
        } else {
            try {
                aviso.setImagem(aviso.getMultipartFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            aviso.setDataPostagem(new Date());
            this.avisoService.addOrUpdate(aviso);
            model.addAttribute("mensagem", "Aviso adicionado com sucesso");
            return "aviso/adicionarAvisoSucesso";
        }
    }

    @RequestMapping(value = "/imagem/{idAviso}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("idAviso") Long idAviso) throws IOException {
        byte[] imageContent = this.avisoService.findOne(idAviso).getImagem();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/removerAviso/{idAviso}", method = RequestMethod.GET)
    public ModelAndView removerAviso(@PathVariable Long idAviso) {
        String mensagem = "";
        try {
            this.avisoService.remove(idAviso);
            System.out.println("Aviso nº " + idAviso + " removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro na remoção do aviso nº " + idAviso + "\n\n" + e.getMessage());
        }
        ModelAndView mav = new ModelAndView("aviso/removerAvisoSucesso");
        mav.addObject("mensagem", "Aviso removido com sucesso");
        return mav;
    }

    @RequestMapping(value = "/visualizarAvisos", method = RequestMethod.GET)
    public ModelAndView visualizarAvisos() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("aviso/visualizarAvisos");
        mav.addObject("listaAvisos", this.avisoService.findAll());
        return mav;
    }

}
