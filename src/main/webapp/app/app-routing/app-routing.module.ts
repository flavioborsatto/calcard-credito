import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ContentComponent } from './../content/content.component';
import { CadastroComponent } from './../cadastro-proposta/cadastro-proposta.component';
import { ConsultaPropostasComponent } from '../consulta-propostas/consulta-propostas.component';
import { ResultadoComponent } from '../resultado/resultado.component';


const routes: Routes = [
  { path: '', component: ContentComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'consulta', component: ConsultaPropostasComponent },
  { path: 'resultado', component: ResultadoComponent },
  ];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  declarations: [],
  exports: [RouterModule]
})
export class AppRoutingModule { 

  
}
