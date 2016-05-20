function QuestionController($http, $state, $scope, QuestionService) {
	var self = this;
	this.listQuestion = [];
	this.listFormulaire = [];
	this.questionSelected = {};
	this.questionToEdit = undefined;
	this.formulaireSelected = {};
	this.isSuccess = true;
	
	this.getListQuestion = function getListQuestion(){
		self.listQuestion = QuestionService.listQuestion.post({}, onSuccess, onError);
	};
	
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.update = function update() {
		self.questionToEdit = self.questionSelected;
	};
	
	this.validerEdit = function validerEdit(){
		QuestionService.setQuestion(self.questionToEdit);	
	};
	
	this.validerAdd = function validerAdd(){
		
	};
	
	self.getListQuestion();
	
}
angular
.module('portailAutoEval')
.controller('QuestionController', QuestionController);