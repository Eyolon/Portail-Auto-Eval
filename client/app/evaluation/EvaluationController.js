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
        self.listQuestionnaire = EvaluationService.listQuestionnaire.post();
	};
	
	this.getQuestionnaire = function getQuestionnaire(){
        EvaluationService.questionnaire.post({nameForm: 'Questionnaire de TEST1'}, redirectToEvaluation, onError);
		/*EvaluationService.getQuestionnaire('Questionnaire de TEST1', redirectToEvaluation, onError);*/
	};
}
angular
	.module('pocApp')
	.controller('EvaluationController', EvaluationController);