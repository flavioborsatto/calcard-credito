export class Proposta {
    id:number;
    nome:string;
    cpf:string;
    idade:number;
    sexo:string;
    estadoCivil:string;
    estado:string;
    dependentes:number;
    renda:number;
    situacao:string;
    descricaoSituacao:string;

    constructor(id:number,
        nome:string,
        cpf:string,
        idade:number,
        sexo:string,
        estadoCivil:string,
        estado:string,
        dependentes:number,
        renda:number,
        situacao:string,
        descricaoSituacao:string) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.idade = idade;
            this.sexo = sexo;
            this.estadoCivil = estadoCivil;
            this.estado = estado;
            this.dependentes = dependentes;
            this.renda = renda;
            this.situacao = situacao;
            this.descricaoSituacao = descricaoSituacao;
        }
}