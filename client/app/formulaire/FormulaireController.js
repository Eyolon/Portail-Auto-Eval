function FormulaireController($http, $state, $scope, FormulaireService) {
	var self = this;
	this.listType = [];
	this.listFormulaire = [];
	this.listService = [];
	this.listQuestion = [];
	this.formulaireSelected = {};
	this.formulaireToEdit = undefined;
	this.isSuccess = true;
	this.readyToSubmit = false;
	this.linkToAdd = [];
	
	
	this.getListType = function getListType(){
		self.listType = FormulaireService.listType.post({}, onSuccess, onError);
	};
	
	this.getListFormulaire = function getListFormuaire(){
		self.listFormulaire = FormulaireService.listFormulaire.post({}, onSuccess, onError);
	};
	
	this.getListService = function getListService(){
		self.listService = FormulaireService.listService.post({}, onSuccess, onError);
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
	
	this.update = function update() {
		self.formulaireToEdit = self.formulaireSelected;
	};
	
	this.validerEdit = function validerEdit(){
		FormulaireService.setFormulaire(self.formulaireToEdit);	
	};
	
	this.validerLink = function validerLink(){
		FormulaireService.setFormulaire(self.formulaireToEdit);		
	};
	
	this.deleteQuestion = function deleteQuestion(id){
		/*if(self.formulaireToEdit !== undefined){
			//self.formulaireToEdit.listQuestion
			
		} */
		
	};
	
	this.validerAdd = function validerAdd(){
		console.log(this.formulaireToEdit);
		if(this.formulaireToEdit !== undefined && 
			this.formulaireToEdit.type !== undefined && 
			this.formulaireToEdit.service !== undefined && 
			this.formulaireToEdit.nom !== undefined){

				this.readyToSubmit = true;
		}
	};
	
	self.getListType();
	self.getListService();
	self.getListFormulaire();
	self.getListQuestion();
	
}
angular
.module('portailAutoEval')
.controller('FormulaireController', FormulaireController);