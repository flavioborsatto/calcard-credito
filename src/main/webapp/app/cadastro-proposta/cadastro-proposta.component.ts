import { Component, OnInit } from '@angular/core';
 
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';

 
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { map, filter, scan } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Proposta } from '../services/proposta';
import { PropostaService } from '../services/proposta.service';
import { Response } from '../services/response';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

 
@Component({
    selector: 'app-cadastro-proposta',
    templateUrl: './cadastro-proposta.component.html',
    styleUrls:["./cadastro-proposta.component.css"]
  })
  export class CadastroComponent implements OnInit {
 
    private titulo:string;
   // private proposta:Proposta = new Proposta();
   formularioDeUsuario: FormGroup;
 
    constructor(private propostaService: PropostaService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private fb: FormBuilder){}
 
    /*CARREGADO NA INICIALIZAÇÃO DO COMPONENTE */
    ngOnInit() {
      this.criarFormularioDeUsuario();
   }

   criarFormularioDeUsuario() {
    this.formularioDeUsuario = this.fb.group({
      nome:['', 
        Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100)
      ]) 
    ], 
      cpf:['',
        Validators.compose([Validators.required]
        )],
      idade:['',
         Validators.compose([Validators.required]
        )],
      sexo:['',
        Validators.compose([Validators.required]
       )],
      estadoCivil:['',
       Validators.compose([Validators.required]
      )],
      estado:['', 
         Validators.compose([Validators.required]
      )],
      dependentes:['', 
         Validators.compose([Validators.required]
       )],
      renda:['', 
         Validators.compose([Validators.required]
        )],
    });
  }
 
    /*FUNÇÃO PARA SALVAR UM NOVO REGISTRO OU ALTERAÇÃO EM UM REGISTRO EXISTENTE */
    salvar():void {
 
      const dadosFormulario = this.formularioDeUsuario.value;

    const proposta = new Proposta(
      null,
      dadosFormulario.nome,
      dadosFormulario.cpf,
      dadosFormulario.idade,
      dadosFormulario.sexo,
      dadosFormulario.estadoCivil,
      dadosFormulario.estado,
      dadosFormulario.dependentes,
      dadosFormulario.renda,
      null,
      null
    );
        /*CHAMA O SERVIÇO PARA ADICIONAR UMA NOVA PROPOSTA */
         this.propostaService.addProposta(proposta).subscribe(response => {
 
           //PEGA O RESPONSE DO RETORNO DO SERVIÇO
           let res:Proposta = <Proposta>response;
          
           //redireciona para pagina de resultado
           this.router.navigate(['resultado'], 
           {queryParams: res});

          console.log(res);
         
         },
         (erro) => {   
           /**AQUI VAMOS MOSTRAR OS ERROS NÃO TRATADOS
             EXEMPLO: SE APLICAÇÃO NÃO CONSEGUIR FAZER UMA REQUEST NA API                        */                 
            alert(erro);
         });
 
      }
    }