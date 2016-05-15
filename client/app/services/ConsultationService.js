function ConsultationService($http, $resource) {
	
	this.etablissements = $resource('/api/etablissement', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });
	
	this.questions = $resource('/api/etablissement', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });
	
	this.services = $resource('/api/services', {}, {
        'post': {
            method: 'POST',
            isArray: true
        }
    });
	
	this.listNotes = $resource('/api/reponse/:idEtablissement', 
			{idEtablissement:'@idEtablissement'}, 
			{'post': {
            method: 'POST',
            isArray: true
        }
    });
	
	
}
angular
.module('portailAutoEval')
.service('ConsultationService', ConsultationService);