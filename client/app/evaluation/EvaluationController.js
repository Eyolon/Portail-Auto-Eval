function EvaluationController(EvaluationService, ipCookie) {
	var self = this;
	this.isSuccess = true;
    this.listQuestionnaire = [];
    this.listQuestionnaireSelectionnee = [];
	this.questionnaire = {};
	this.user = ipCookie('utilisateur');
	
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.repondre = function repondre() {
        //il faut faire passer l'id utilisateur et la remarque sera le path du drive pour la preuve		
		var temp = self.listQuestionnaireSelectionnee;
		self.listQuestionnaireSelectionnee = [];
		
		//MERCI D'AVOIR REPONDU ?
		
		EvaluationService.setAnswer(temp,self.user);
		
    };
    
    this.getListQuestionnaires = function getListQuestionnaires(){
    	console.log(self.user);
    	self.listQuestionnaire = EvaluationService.listFormulaireFull.post({idUser: self.user.id}, onSuccess, onError);    	
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