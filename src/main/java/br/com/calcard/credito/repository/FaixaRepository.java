package br.com.calcard.credito.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.calcard.credito.domain.Faixa;

@Transactional
public interface FaixaRepository extends JpaRepository<Faixa, Long> {

	@Query("select f from FAIXA f where valorInicial <= ?1 and valorFinal >= ?1")
	Faixa pesquisarFaixa(BigDecimal valor);

}
