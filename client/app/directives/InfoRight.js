function infoRight(){
    return{
        restrict:'E',
        templateUrl:'/directives/infoRight.html',
        controller:infoController,
        controllerAs: 'infoRight'
    };
}
function infoController() {
	var self = this;
}
angular
    .module('pocApp')
    .directive('infoRight',infoRight);