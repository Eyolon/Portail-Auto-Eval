function navbarTop(){
    return{
        restrict:'E',
        templateUrl:'/directives/navbarTop.html',
        controller: NavbarTopController,
        controllerAs: 'navbarTopCtrl'
    };
}
function NavbarTopController($scope, ConnexionService, EvaluationService) {
	var self = this;
	this.isConnected = ConnexionService.checkIsConnected();
	this.isSuccessLoadEvaluation = false;
	
	this.logout = function logout() {
		ConnexionService.logout();
	};
	
	function redirectToEvaluation() {
        self.isSuccessLoadEvaluation = true;
	}
	
	
	this.getEvaluation = function getEvaluation(){
		
		EvaluationService.questionnaire.post({nomForm: 'FormA'},redirectToEvaluation(), {});
	
	};
	
	$scope.$on("userLogin", function onUserLogIn(event, user) {
		self.isConnected = true;
	});
	
	$scope.$on("userLogout", function onUserLogOut(event) {
		self.isConnected = false;
	});
}
angular
    .module('pocApp')
    .directive('navbarTop',navbarTop);