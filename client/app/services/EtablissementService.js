function EtablissementService($resource,$http){

	this.etablissement = $resource('/api/etablissement/:etablissement', 
			{etablissement:'@etablissement'}, 
			{'post': {
            method: 'POST'
        }
    });
    
}
angular
.module('portailAutoEval')
.service('EtablissementService', EtablissementService);