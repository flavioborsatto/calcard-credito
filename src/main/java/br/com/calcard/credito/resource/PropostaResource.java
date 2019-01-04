package br.com.calcard.credito.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.calcard.credito.domain.Proposta;
import br.com.calcard.credito.service.PropostaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/")
@Api(value = "Proposta", description = "Operações de proposta")
public class PropostaResource {
	
	@Autowired
	private PropostaService propostaService;

	@RequestMapping(value = "/consulta/{cpf}", method = RequestMethod.GET)
	@ApiOperation(value = "Lista as propostas de um cpf")
	public ResponseEntity<List<Proposta>> listar(@PathVariable("cpf") String cpf) {
		return new ResponseEntity<List<Proposta>>(new ArrayList<Proposta>(propostaService.findByCpf(cpf)), HttpStatus.OK);
	}
	
    @PostMapping(value = "/novaProposta")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cadastra uma nova proposta")
    public ResponseEntity<Proposta> save(@RequestBody Proposta proposta){
    	propostaService.save(proposta);
        return new ResponseEntity<Proposta>(proposta, HttpStatus.OK);
    }	
	
}
