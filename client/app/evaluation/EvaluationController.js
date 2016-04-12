function evaluation(){

    return{
        restrict:'E',
        templateUrl:'/evaluation/evaluation.html',
        controller: EvaluationController,
        controllerAs: 'evaluation'
    };
}
function EvaluationController($state, EvaluationService) {
	var self = this;
	//this.evaluation = {};
	this.isSuccess = true;
    this.listQuestionnaire = [];
	
	function redirectToEvaluation() {
        self.isSuccess = true;
		$state.go('evaluation');
	}
	
	function onError() {
		self.isSuccess = false;
	}
    
    this.getQuestionnaire = function getQuestionnaire(){
        self.listQuestionnaire = EvaluationService.listQuestionnaire();
	};
	
	this.getQuestionnaire = function getQuestionnaire(){
        EvaluationService.questionnaire({nomForm: 'FormA'}, redirectToEvaluation, onError);
		/*EvaluationService.getQuestionnaire('Questionnaire de TEST1', redirectToEvaluation, onError);*/
	};
}
angular
	.module('pocApp')
	.controller('EvaluationController', EvaluationController);