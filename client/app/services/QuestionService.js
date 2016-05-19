function QuestionService($resource, $http) {

	this.listQuestion = $resource('/api/question',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
	
	this.setQuestion = function setQuestion(question){
		$http.post('/api/question',{question: question});		
	};
}
angular
    .module('portailAutoEval')
    .service('QuestionService', QuestionService);