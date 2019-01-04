import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Proposta } from '../services/proposta';
import { Router } from '@angular/router';

@Component({
  selector: 'app-resultado',
  templateUrl: './resultado.component.html',
  styleUrls: ['./resultado.component.css']
})
export class ResultadoComponent implements OnInit {

private proposta:Proposta;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      console.log(params);

      this.proposta = <Proposta>params;

    }
  )}

  gotoCadastroClientes() {
    this.router.navigate(['cadastro']);
   }

   gotoConsultaPropostas() {
    this.router.navigate(['consulta'],  { queryParams: { cpf: this.proposta.cpf } });
   }

}
