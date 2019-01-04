import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Headers} from '@angular/http';
import { RequestOptions } from '@angular/http';
 

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { map, filter, scan } from 'rxjs/operators';
import { Observable } from 'rxjs';
 
import {Proposta} from '../services/proposta';
import {ConfigService} from './config.service';
 
@Injectable()
export class PropostaService {
 
    private baseUrlService:string = '';
    private headers:Headers;
    private options:RequestOptions;
 
    constructor(private http: Http,
                private configService: ConfigService) { 
 
        /**SETANDO A URL DO SERVIÇO REST QUE VAI SER ACESSADO */
        this.baseUrlService = configService.getUrlService() + '';
 

       this.headers = new Headers({ 'Content-Type': 'application/json;charset=UTF-8' });                
       this.options = new RequestOptions({ headers: this.headers });
    }
 
    /**CONSULTA TODAS AS PropostaS CADASTRADAS PARA O CPF*/
    getPropostas(cpf:string){        
        return this.http.get(this.baseUrlService+'consulta/'+cpf).map(res => res.json());
    }
 
    /**ADICIONA UMA NOVA Proposta */
    addProposta(proposta: Proposta){
        return this.http.post(this.baseUrlService+'novaProposta', JSON.stringify(proposta),this.options)
        .map(res => res.json());
    } 
}