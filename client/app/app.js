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
            console.log('From state: ' + toState.url);
            console.log('To state: ' + fromState.url);
            if(fromState.name === '') {
                console.log('Premier affichage.');
                InitService.init();
            }
            var utilisateur = ipCookie('utilisateur');
            if(toState.right === undefined || (utilisateur !== undefined && utilisateur.typeUtilisateur !== undefined && toState.right.includes(utilisateur.typeUtilisateur.libelle))) {
                console.log('Nouvel état: ' + toState.url); 
            } else {
                Notification.info("Vous n'avez pas accès à cet écran avec cette session.");
                console.log('Accès refusé pour: ' + toState.url); 
                event.preventDefault();
            }
        });
    });