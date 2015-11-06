angular
    .module('pocApp')
    .config(function ($stateProvider, $urlRouterProvider) {
        ///////////
        /* VIEWS */
        ///////////
        $stateProvider.state('main', {
            abstract: true,
            sticky: true
        });

        $stateProvider.state('home', {
            parent: 'main',
            url: '/home',
            title: 'Home',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/home/home.html',
                    controller: 'HomeCtrl',
                    controllerAs: 'HomeCtrl'
                }
            }
        });
		
		/*$stateProvider.state('books', {
            parent: 'main',
            url: '/books',
            title: 'Books',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/books/books.html',
                    controller: 'BooksCtrl',
                    controllerAs: 'BooksCtrl'
                }
            }
        });
		
		$stateProvider.state('book', {
            parent: 'books',
            url: '/{bookId:int}',
            title: 'Book',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/book/book.html',
                    controller: 'BookCtrl',
                    controllerAs: 'BookCtrl'
                }
            }
        });*/
		
		$stateProvider.state('contacts', {
            parent: 'main',
            url: '/contacts',
            title: 'Contacts',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/contacts/contacts.html',
                    controller: 'ContactsCtrl',
                    controllerAs: 'ContactsCtrl'
                }
            }
        });


        $stateProvider.state('inscription', {
            parent: 'main',
            url: '/inscription',
            title: 'Registration',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/inscription/inscription.html',
                    controller: 'InscriptionCtrl',
                    controllerAs: 'InscriptionCtrl'
                }
            }
        });
        $stateProvider.state('connection', {
            parent: 'main',
            url: '/connection',
            title: 'Connection',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/connection/connection.html',
                    controller: 'ConnectionCtrl',
                    controllerAs: 'ConnectionCtrl'
                }
            }
        });
		
		/*$stateProvider.state('historique', {
            parent: 'connection',
            url: '/historique',
            title: 'History Borrowing',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/historique/historique.html',
                    controller: 'HistoriqueCtrl',
                    controllerAs: 'HistoriqueCtrl'
                }
            }
        });
		$stateProvider.state('reservation', {
            parent: 'connection',
            url: '/reservation',
            title: 'Reservation',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/reservation/reservation.html',
                    controller: 'ReservationsCtrl',
                    controllerAs: 'ReservationsCtrl'
                }
            }
        });*/
		
		$stateProvider.state('profil', {
            parent: 'connection',
            url: '/profil',
            title: 'My Account',
            reloadOnSearch: true,
            views: {
                'main@': {
                    templateUrl: '/profil/profil.html',
                    controller: 'ProfilCtrl',
                    controllerAs: 'ProfilCtrl'
                }
            }
        });
		
		/*$stateProvider.state('emprunts', {
            parent: 'connection',
            url: '/emprunts',
            title: 'Current Loan',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/emprunts/emprunts.html',
                    controller: 'EmpruntsCtrl',
                    controllerAs: 'EmpruntsCtrl'
                }
            }
        });
		$stateProvider.state('cgu', {
            parent: 'main',
            url: '/cgu',
            title: 'Terms and Conditions',
            reloadOnSearch: false,
            views: {
                'main@': {
                    templateUrl: '/cgu/cgu.html',
                    controller: 'CguCtrl',
                    controllerAs: 'CguCtrl'
                }
            }
        });*/		
		

        ///////////////
        /* OTHERWISE */
        ///////////////
        //TODO 404
        $urlRouterProvider.otherwise('/home');
    });