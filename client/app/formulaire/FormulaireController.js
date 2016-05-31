function FormulaireController($scope, FormulaireService, Notification) {
	var self = this;
	this.listType = [];
	this.listFormulaire = [];
	this.listEtablissement = [];
	this.listService = [];
	this.listQuestion = [];
	this.formulaireSelected = {};
	$scope.formulaireToEdit = {
		formulaire: {},
		service: {},
		etablissement: {}
	};
	this.isSuccess = true;
	this.readyToSubmit = false;
	this.linkToAdd = [];
	this.questionToAdd = {};
	
	this.getListEtablissement = function getListEtablissement(){
		self.listEtablissement = FormulaireService.listEtablissement.post({}, function (results) {
			onSuccess();
			$scope.formulaireToEdit.etablissement = results[0];
		}, onError);
	};
	
	this.getListType = function getListType(){
		self.listType = FormulaireService.listType.post({}, onSuccess, onError);
	};
	
	this.getListFormulaire = function getListFormuaire(){
		self.listFormulaire = FormulaireService.listFormulaire.post({}, function (results) {
			onSuccess();
			$scope.formulaireToEdit = results[0];
		}, onError);
	};
	
	this.getListService = function getListService(){
		self.listService = FormulaireService.listService.post({}, function (results) {
			onSuccess();
			$scope.formulaireToEdit.service = results[0];
		}, onError);
	};
	
	this.getListQuestion = function getListQuestion(){
		self.listQuestion = FormulaireService.listQuestion.post({}, onSuccess, onError);
	};
	
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.validerEdit = function validerEdit(){
		FormulaireService.setFormulaire($scope.formulaireToEdit);
		Notification.success("Echange Achevé");
	};
	
	this.ajoutQuestion = function ajoutQuestion(){
		$scope.formulaireToEdit.listQuestion.push(self.questionToAdd);
	};
	
	this.supprimerQuest = function supprimerQuest(question){
		var index = $scope.formulaireToEdit.listQuestion.indexOf(question);
		$scope.formulaireToEdit.listQuestion.splice(index, 1);	
	};
	
	this.validerLink = function validerLink(){
		FormulaireService.setFormulaire($scope.formulaireToEdit);		
	};
	
	this.validerAdd = function validerAdd(){
	
		if($scope.formulaireToEdit !== undefined && 
			$scope.formulaireToEdit.type !== undefined && 
			$scope.formulaireToEdit.service !== undefined && 
			$scope.formulaireToEdit.formulaire !== undefined &&
			$scope.formulaireToEdit.etablissement !== undefined){

				$scope.readyToSubmit = true;
		}
	};
	
	self.getListFormulaire();
	self.getListType();
	self.getListService();
	self.getListQuestion();
	self.getListEtablissement();
	
}
angular
.module('portailAutoEval')
.controller('FormulaireController', FormulaireController);