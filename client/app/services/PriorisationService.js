function PriorisationService($resource) {

	this.listPriorisation = $resource('/api/priorisation/:idUser', 
    		{
    			idUser:'@idUser'
    		}, {
    			'post': {
    				method: 'POST',
    				isArray: true
    			}
    });
	
	this.listNotes = $resource('/api/reponseUser/:idUser', 
    		{
    			idUser:'@idUser'
    		}, {
    			'post': {
    				method: 'POST',
    				isArray: false
    			}
    });
	
}
angular
    .module('portailAutoEval')
    .service('PriorisationService',	PriorisationService);