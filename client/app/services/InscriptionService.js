function InscriptionService($resource) {

    this.services = $resource('/api/services', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });

    this.typesUser = $resource('/api/typesUser', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });

    this.listUtilisateur = $resource('/api/userFull', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });
    
    this.listUtilisateurByEtablissement = $resource('/api/userFullByEtablissement/:idEtablissement', 
    		{
    			idEtablissement:'@idEtablissement'
    		}, {
    			'post': {
    				method: 'POST',
    				isArray: true
    			}
    });
    
    this.etablissements = $resource('/api/etablissement', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });
    
}
angular
    .module('portailAutoEval')
    .service('InscriptionService', InscriptionService);