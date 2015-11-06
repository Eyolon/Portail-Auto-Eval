function ConnectionCtrl($http, $state, ConnexionService) {
	var self = this;
	this.connexion = {};
	this.isSuccess = true;
	
	function redirectToHome() {
		$state.go('home');
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.seConnecter = function seConnecter() {
		ConnexionService.seConnecter(self.connexion.mail, self.connexion.password, redirectToHome, onError);
	};
}
angular
    .module('pocApp')
	.controller('ConnectionCtrl', ConnectionCtrl);