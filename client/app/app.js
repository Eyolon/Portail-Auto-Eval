angular.module('pocApp', [
		//'ngAnimate',
        'ngResource',
        'ngSanitize',
        'ngTouch',
        'ipCookie',
		'angularMoment',
        'chieffancypants.loadingBar',
		'ui.router',
        'ct.ui.router.extras',
        'ui.bootstrap',
        'angularUtils.directives.dirPagination',
        //'angular-coverflow',
		//'angular-cardflow',
        'angular-carousel'])
	.run(function(InitService) {
        InitService.init();
    });