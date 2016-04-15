function EvaluationService($resource){

    this.listQuestionnaire = $resource('/api/evaluation',
        {},
        {
            'post': {method:'POST', isArray:true}
        }
    );

    
    this.listFormulaireFull = $resource('/api/evaluation/:idService',
            {idService:'@idService'},
            {
                'post': {method:'POST', isArray:true}
            }
        );
    
    this.questionnaire = $resource('/api/evaluation/:nomForm',
        {noForm:'@nomForm'},
        {
            'post': {method:'POST', isArray:false}
        }
    );
	
}


angular
    .module('portailAutoEval')
    .service('EvaluationService', EvaluationService);