function EvaluationController(EvaluationService, ipCookie) {
	var self = this;
	this.isSuccess = true;
    this.listQuestionnaire = [];
	this.questionnaire;
	this.user = ipCookie('utilisateur'); 
	
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
    
    this.getListQuestionnaires = function getListQuestionnaires(){
    	//self.listQuestionnaire = EvaluationService.listFormulaireFull.post({idService: '1'}, onSuccess, onError);
        
    	self.listQuestionnaire = EvaluationService.listFormulaireFull.post({idService: self.user.service['id']}, onSuccess, onError);
    };
	
	/*Plus un cas de test plutot qu'utile*/
	this.getQuestionnaire = function getQuestionnaire(){
		self.questionnaire = EvaluationService.questionnaire.post({nomForm: 'FormA'}, onSuccess, onError);
	};
    
    self.getListQuestionnaires();
}
angular
	.module('portailAutoEval')
	.controller('EvaluationController', EvaluationController);