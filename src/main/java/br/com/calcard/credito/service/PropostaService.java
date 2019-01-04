package br.com.calcard.credito.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.calcard.credito.domain.EstadoCivilEnum;
import br.com.calcard.credito.domain.Faixa;
import br.com.calcard.credito.domain.Proposta;
import br.com.calcard.credito.domain.SituacaoEnum;
import br.com.calcard.credito.repository.FaixaRepository;
import br.com.calcard.credito.repository.PropostaRepository;

@Service
public class PropostaService {

	@Autowired
	private final PropostaRepository propostaRepository;
	
	@Autowired
	private final FaixaRepository faixaRepository;
	
	public Proposta save(Proposta proposta) {
		//regra de negócio
		if (proposta.getRenda().compareTo(new BigDecimal(1000)) < 0) {
			proposta.setSituacao(SituacaoEnum.REPROVADA);
			proposta.setDescricaoSituacao("Proposta negada por renda baixa");
		} else {
			Integer pontuacaoDependente = proposta.getEstadoCivil().equals(EstadoCivilEnum.DIVORCIADO) ? 501 : 1001;
			BigDecimal pontuacao = proposta.getRenda().subtract(new BigDecimal(proposta.getDependentes() * pontuacaoDependente));
			Faixa faixa = faixaRepository.pesquisarFaixa(pontuacao);
			proposta.setFaixa(faixa);
			proposta.setDescricaoSituacao(faixa.getDescricao());
			proposta.setSituacao(faixa.getSituacao().equals("A") ? SituacaoEnum.APROVADA : SituacaoEnum.REPROVADA);
		}
	
		return propostaRepository.save(proposta);
	}
	
	
	
	PropostaService(PropostaRepository propostaRepository, FaixaRepository faixaRepository) {
		this.propostaRepository = propostaRepository;
		this.faixaRepository = faixaRepository;
	}



	public List<Proposta> findByCpf(String cpf) {
		return propostaRepository.findByCpf(cpf);
	}
}
