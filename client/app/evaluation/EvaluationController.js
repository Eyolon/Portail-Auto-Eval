function EvaluationController(EvaluationService, ipCookie) {
	var self = this;
	this.isSuccess = true;
    this.listQuestionnaire = [];
	this.questionnaire;
	this.user = ipCookie('utilisateur');
	this.listNoteAllReadyCommited = [];
	
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.repondre = function repondre() {
        //il faut faire passer l'id utilisateur et la remarque sera le path du drive pour la preuve
        EvaluationService.setAnswer(self.listQuestionnaire,self.user);		
    };
    
    this.getListQuestionnaires = function getListQuestionnaires(){
    	self.listQuestionnaire = EvaluationService.listFormulaireFull.post({idService: self.user.service['id']}, onSuccess, onError);
    };
	
    this.getListNoteAllReadyCommited = function getListNoteAllReadyCommited(){
    	EvaluationService.listNoteAllReadyCommited.post({idUser: self.user.id}, onSuccess, onError);
    }
    
	/*Plus un cas de test plutot qu'utile*/
	this.getQuestionnaire = function getQuestionnaire(){
		self.questionnaire = EvaluationService.questionnaire.post({nomForm: 'FormA'}, onSuccess, onError);
	};
    
    self.getListQuestionnaires();
    self.getListNoteAllReadyCommited();
}
angular
	.module('portailAutoEval')
	.controller('EvaluationController', EvaluationController);