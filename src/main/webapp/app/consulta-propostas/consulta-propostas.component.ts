import { Component, OnInit } from '@angular/core';
 
import {Router, ActivatedRoute} from '@angular/router';
import { PropostaService } from '../services/proposta.service';
import { Proposta } from '../services/proposta';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
 
@Component({
    selector: 'app-consulta-proposta',
    templateUrl: './consulta-propostas.component.html',
    styleUrls:["./consulta-propostas.component.css"]
  })
  export class ConsultaPropostasComponent implements OnInit {
 
    private propostas: Proposta[] = new Array();
    private titulo:string;
    private cpf:string;
    formularioDeConsulta: FormGroup;
 
    constructor(private propostaService: PropostaService,
                private router: Router,
                private route: ActivatedRoute,
                private fb: FormBuilder){}
 
    ngOnInit() {
      this.route
      .queryParams
      .subscribe(params => {
        // Defaults to 0 if no query param provided.
        this.cpf = params['cpf'] || '';
      });

      this.formularioDeConsulta = this.fb.group({
        cpf:[this.cpf, 
          Validators.compose([
          Validators.required,   
        ])
      ]
      });
 

      /*SETA O TÍTULO */
      this.titulo = "Propostas Cadastradas";
 
      /*CHAMA O SERVIÇO E RETORNA TODAS AS PESSOAS CADASTRADAS */
      this.propostaService.getPropostas(this.cpf).subscribe(res => this.propostas = res);
    } 

    gotoCadastroClientes() {
      this.router.navigate(['cadastro']);
     }

     consultar() {
      const dadosFormulario = this.formularioDeConsulta.value;
      let cpf = dadosFormulario.cpf;
      this.propostaService.getPropostas(cpf).subscribe(res => this.propostas = res);
     }
  }