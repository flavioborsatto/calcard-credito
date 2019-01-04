import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Proposta } from '../services/proposta';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  private proposta:Proposta;

  constructor(private router: Router) { }

  ngOnInit() {
  }

  gotoCadastroClientes() {
    this.router.navigate(['cadastro']);
   }
}
