function login(){

    return{
        restrict:'E',
        templateUrl:'/directives/login.html',
        controller: loginController,
        controllerAs: 'login'
    };
}
function loginController($state, $scope, ConnexionService, ipCookie) {
	var self = this;
	this.connect = {};
	this.isConnected = ConnexionService.checkIsConnected();
	this.user = ipCookie('utilisateur');
	this.isSuccess = true;
	
	function updateConnectionStatus(utilisateur) {
		self.isConnected = ConnexionService.checkIsConnected();
		if(utilisateur) {
			self.user = utilisateur;
		} else {
			self.user = ipCookie('utilisateur');
		}		
	}
	
	function redirectToHome() {
		$state.go('home');
	}
	
	function onError() {
		self.isSuccess = false;
	}
	
	this.seConnecter = function seConnecter() {
		ConnexionService.seConnecter(self.connect.email, self.connect.pwd, redirectToHome, onError);
	};
	
	this.logout = function logout() {
		ConnexionService.logout();
		redirectToHome();
	};
	
	$scope.$on("userLogin", function onUserLogIn(event, user) {
		self.isConnected = true;
		self.user = user;
		self.isSuccess = true;
	});
	
	$scope.$on("userLogout", function onUserLogOut(event) {
		self.isConnected = false;
		self.user = {};
		self.isSuccess = true;
	});
}
angular
    .module('pocApp')
    .directive('login',login);