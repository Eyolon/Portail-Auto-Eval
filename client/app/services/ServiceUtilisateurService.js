function ServiceUtilisateurService($resource,$http){

	this.service = $resource('/api/service/:service', 
			{service:'@service'}, 
			{'post': {
            method: 'POST'
        }
    });
    
}
angular
.module('portailAutoEval')
.service('ServiceUtilisateurService', ServiceUtilisateurService);