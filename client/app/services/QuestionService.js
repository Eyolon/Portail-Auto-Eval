function QuestionService($resource, $http) {

	this.listQuestion = $resource('/api/question',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.listCritere = $resource('/api/critere',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.listFormulaire = $resource('/api/evaluation',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.setQuestion = function setQuestion(question){
		$http.post('/api/editQuestion',{question: question});		
	};
}
angular
    .module('portailAutoEval')
    .service('QuestionService', QuestionService);