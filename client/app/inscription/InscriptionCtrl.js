function InscriptionCtrl($http, $state, ConnexionService, InscriptionService) {
    var self = this;
    this.user = {};
    this.pwdCheck = "";
    this.services = [];
    this.typesUser = [];
    this.isSuccess = true;

    this.sInscrire = function sInscrire() {
        if (self.pwdCheck !== undefined && self.pwdCheck !== "" && self.user.pwd !== undefined && self.user.pwd !== "" && self.pwdCheck === self.user.pwd) {
            insertUser();
        }
    };
    
	function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}
    
    this.getServices = function getServices(){
    	self.services = InscriptionService.services.post({}, onSuccess, onError);
    };
    
    this.getTypesUser = function getTypesUser(){
    	self.typesUser = InscriptionService.typesUser.post({}, onSuccess, onError);

    };

    function insertUser() {
        $http.post('/api/insertUser', {
                login: self.user.identifiant,
                pwd: self.user.pwd,
                service: self.user.service.id,
                typeUser: self.user.typeUser.id
            })
            .success(function (data, status, headers, config) {
                //ConnexionService.seConnecter(self.user.adresseMail, self.user.pwd);
                //$state.go('home');
            })
            .error(function (data, status, headers, config) {
                console.log(data);
            });
    }
    
    self.getTypesUser();
    self.getServices();
    
}
angular
    .module('portailAutoEval')
    .controller('InscriptionCtrl', InscriptionCtrl);