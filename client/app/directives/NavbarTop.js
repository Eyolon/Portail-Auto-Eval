function navbarTop(){
    return{
        restrict:'E',
        templateUrl:'/directives/navbarTop.html',
        controller: NavbarTopController,
        controllerAs: 'navbarTopCtrl'
    };
}
function NavbarTopController($scope, ConnexionService, ipCookie) {
	var self = this;
	this.isConnected = ConnexionService.checkIsConnected();
	this.user = ipCookie('utilisateur');
	
	this.logout = function logout() {
		ConnexionService.logout();
	};
	
	$scope.$on("userLogin", function onUserLogIn(event, user) {
		self.isConnected = true;
		self.user=ipCookie('utilisateur');
	});
	
	$scope.$on("userLogout", function onUserLogOut(event) {
		self.isConnected = false;
		self.user = {};
	});
	
	
}
angular
    .module('portailAutoEval')
    .directive('navbarTop',navbarTop);