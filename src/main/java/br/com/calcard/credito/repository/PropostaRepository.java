package br.com.calcard.credito.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.calcard.credito.domain.Proposta;

@Transactional
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
	
	List<Proposta> findByCpf(String cpf);

}
