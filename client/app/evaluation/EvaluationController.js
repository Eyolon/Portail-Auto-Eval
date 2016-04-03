function EvaluationController($resource, EvaluationService) {
	var self = this;
	//this.evaluation = {};
	this.isSuccess = true;
	
	function redirectToEvaluation() {
		$state.go('evaluation');
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.getQuestionnaire = function getQuestionnaire(){
		EvaluationService.getQuestionnaire('Questionnaire de TEST1', redirectToEvaluation, onError);
	};
}
angular
	.module('pocApp')
	.controller('EvaluationController', EvaluationController);