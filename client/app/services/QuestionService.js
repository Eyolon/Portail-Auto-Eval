function QuestionService($resource) {

	this.listQuestion = $resource('/api/question',
	        {},
	        {
	            'post': {method:'POST', isArray:true}
	        }
	    );
}
angular
    .module('portailAutoEval')
    .service('QuestionService', QuestionService);