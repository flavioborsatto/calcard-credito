export class ConfigService {
 
    private urlService:string;
 
    constructor(){
 
        this.urlService = '/api/';
    }
 
    getUrlService(): string {
 
        return this.urlService;
    }
 
}
