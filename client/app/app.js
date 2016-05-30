angular.module('portailAutoEval', [
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
        'nvd3',
        'ui-notification'])
    .config(function(NotificationProvider) {
        NotificationProvider.setOptions({
            delay: 10000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'right',
            positionY: 'bottom'
        });
    })
	.run(function($rootScope, ipCookie, Notification, InitService) {
        InitService.init();
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            var utilisateur = ipCookie('utilisateur');
            if(utilisateur !== undefined) {
                if(utilisateur.typeUtilisateur !== undefined && (toState.right === undefined || toState.right.includes(utilisateur.typeUtilisateur.libelle))) {
                    console.log('Nouvel état: ' + toState.url); 
                } else {
                    Notification.info("Vous n'avez pas accès à cet écran avec cette session.");
                    console.log('Accès refusé pour: ' + toState.url); 
                    event.preventDefault();
                }
            } else if (toState.right !== undefined){
                Notification.info("Vous devez être identifier pour accéder à la page indiquée.");
                console.log('Accès refusé pour: ' + toState.url); 
                event.preventDefault();
            } else {
                console.log('Nouvel état: ' + toState.url);
            }
        });
    });