function EvaluationController(EvaluationService) {
	var self = this;
	this.isSuccess = true;
    this.listQuestionnaire = [];
	
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
    
    this.getListQuestionnaires = function getListQuestionnaires(){
        self.listQuestionnaire = EvaluationService.listQuestionnaire.post();
	};
	
	this.getQuestionnaire = function getQuestionnaire(){
        EvaluationService.questionnaire({nomForm: 'FormA'}, onSuccess, onError);
	};
    
    self.getListQuestionnaires();
}
angular
	.module('portailAutoEval')
	.controller('EvaluationController', EvaluationController);