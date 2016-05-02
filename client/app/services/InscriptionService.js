function InscriptionService($resource,$http){

	this.services = $resource('/api/services',
        {},
        {
            'post': {method:'POST', isArray:true}
        }
   );
	
	this.typesUser = $resource('/api/typesUser',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	   );
	
	this.listUtilisateur = $resource('/api/userFull',
			{},
			{
				 'post': {method:'POST', isArray:true}
			});


}


angular
    .module('portailAutoEval')
    .service('InscriptionService', InscriptionService);