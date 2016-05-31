function FormulaireService($resource, $http) {

	this.listQuestion = $resource('/api/question',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.listService = $resource('/api/services',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.listType = $resource('/api/typesUser',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.listFormulaire = $resource('/api/formulaire',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.setFormulaire = function setFormulaire(formulaire){
		$http.post('/api/editFormulaire',{formulaire: formulaire});		
	};
}
angular
    .module('portailAutoEval')
    .service('FormulaireService', FormulaireService);