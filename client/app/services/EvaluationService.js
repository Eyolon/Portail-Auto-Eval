function EvaluationService($resource){

    this.listQuestionnaire = $resource('/api/evaluation',
        {},
        {
            'post': {method:'POST', isArray:true}
        }
    );

    this.questionnaire = $resource('/api/evaluation/:nomForm',
        {},
        {
            'post': {method:'POST', isArray:false}
        }
    );
	
}


angular
    .module('pocApp')
    .service('EvaluationService', EvaluationService);