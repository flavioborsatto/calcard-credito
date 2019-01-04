package br.com.calcard.credito.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "PROPOSTA")
public class Proposta {
	
	@Id
     @GeneratedValue(strategy=GenerationType.AUTO) 
     private Long id; 
      
     @Column(name="nome", nullable=false)
     private String nome; 
     
     @Column(name="cpf", nullable=false)
     private String cpf; 
     
     @Column(name="idade", nullable=false)
     private Integer idade; 
     
     @Column(name="sexo", nullable=false)
     private String sexo; 
     
     @Column(name="estadoCivil", nullable=false)
     private EstadoCivilEnum estadoCivil; 
     
     @Column(name="estado", nullable=false)
     private String estado; 
     
     @Column(name="dependentes", nullable=false)
     private Integer dependentes; 
     
     @Column(name="renda", nullable=true)
     private BigDecimal renda;

     @Column(name="situacao", nullable=true)
     private SituacaoEnum situacao;
     
     @Column(name="descricaoSituacao")
     private String descricaoSituacao;

    @ManyToOne
    private Faixa faixa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getDependentes() {
		return dependentes;
	}

	public void setDependentes(Integer dependentes) {
		this.dependentes = dependentes;
	}

	public Faixa getFaixa() {
		return faixa;
	}

	public void setFaixa(Faixa faixa) {
		this.faixa = faixa;
	}

	public BigDecimal getRenda() {
		return renda;
	}

	public void setRenda(BigDecimal renda) {
		this.renda = renda;
	}

	public SituacaoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoEnum situacao) {
		this.situacao = situacao;
	}

	public String getDescricaoSituacao() {
		return descricaoSituacao;
	}

	public void setDescricaoSituacao(String descricaoSituacao) {
		this.descricaoSituacao = descricaoSituacao;
	}

	
 
	
}
