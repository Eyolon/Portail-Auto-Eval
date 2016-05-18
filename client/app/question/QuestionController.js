function QuestionController($http, $state, $scope, QuestionService) {
	var self = this;
	this.listQuestion = [];
	this.listFormulaire = [];
	this.questionSelected = {};
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
	
	this.getListQuestion();
	
}
angular
.module('portailAutoEval')
.controller('QuestionController', QuestionController);