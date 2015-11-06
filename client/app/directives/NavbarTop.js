function navbarTop(){
    return{
        restrict:'E',
        templateUrl:'/directives/navbarTop.html',
        controller: NavbarTopController,
        controllerAs: 'navbarTopCtrl'
    };
}
function NavbarTopController($scope, ConnexionService) {
	var self = this;
	this.isConnected = ConnexionService.checkIsConnected();
	
	this.logout = function logout() {
		ConnexionService.logout();
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