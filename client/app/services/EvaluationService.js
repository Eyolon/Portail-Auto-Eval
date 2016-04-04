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
    
	/*this.getQuestionnaire = function getQuestionnaire(nameForm, onSuccess, onError){
		$http.post('/api/evaluation/'+nameForm)
			.success(function(data, status, headers, config)  {
	                onSuccess();
	        })
			.error(function(data, status, headers, config){
					onError();
	        });
	};*/
	
	this.postResponse = function postResponse(){
	};
	
}


angular
    .module('pocApp')
    .service('EvaluationService', EvaluationService);