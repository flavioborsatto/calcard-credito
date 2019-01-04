import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { ContentComponent } from './content/content.component';
import { FooterComponent } from './footer/footer.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { CadastroComponent } from './cadastro-proposta/cadastro-proposta.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ConsultaPropostasComponent } from './consulta-propostas/consulta-propostas.component';
import { ConfigService } from './services/config.service';
import { PropostaService } from './services/proposta.service';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { ResultadoComponent } from './resultado/resultado.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ContentComponent,
    FooterComponent,
    CadastroComponent,
    ConsultaPropostasComponent,
    ResultadoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatDialogModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpModule,
    FormsModule
  ],
  providers: [ConfigService, PropostaService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
