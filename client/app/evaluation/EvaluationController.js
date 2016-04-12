function EvaluationController(EvaluationService) {
	var self = this;
	this.isSuccess = true;
    this.listQuestionnaire = [];
	this.questionnaire;
    
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
        self.questionnaire = EvaluationService.questionnaire.post({nomForm: 'FormA'}, onSuccess, onError);
	};
    
    self.getListQuestionnaires();
}
angular
	.module('pocApp')
	.controller('EvaluationController', EvaluationController);