function QuestionController($http, $state, $scope, QuestionService) {
	var self = this;
	this.listQuestion = [];
	this.listFormulaire = [];
	this.listCritere = [];
	this.questionSelected = {};
	this.questionToEdit = undefined;
	this.formulaireSelected = {};
	this.isSuccess = true;
	this.readyToSubmit = false;
	
	this.getListQuestion = function getListQuestion(){
		self.listQuestion = QuestionService.listQuestion.post({}, onSuccess, onError);
	};
	
	this.getListFormulaire = function getListFormuaire(){
		self.listFormulaire = QuestionService.listFormulaire.post({}, onSuccess, onError);
	};
	
	this.getListCritere = function getListCritere(){
		self.listCritere = QuestionService.listCritere.post({}, onSuccess, onError);
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

		if(this.questionToEdit !== undefined && 
			this.questionToEdit.critere !== undefined && 
			this.questionToEdit.formulaire !== undefined && 
			this.questionToEdit.valeur !== undefined){

				this.readyToSubmit = true;
		}
	};
	
	self.getListQuestion();
	self.getListCritere();
	self.getListFormulaire();
	
}
angular
.module('portailAutoEval')
.controller('QuestionController', QuestionController);