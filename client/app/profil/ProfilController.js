function ProfilCtrl($filter, $http, $rootScope, ConnexionService, InscriptionService, ipCookie) {
	var self = this;
	this.currentPwd = "";
	this.user = {};
	this.userPwd = "";
	this.isProfilModified = false;
	this.services = [];
    this.typesUser = [];
    this.oldLogin = "";
	
	function getUser() {
		var id = ipCookie('utilisateur').id;
		$http.post('/api/userFull/' + id, {token: ipCookie("token")})
            .success(function(data, status, headers, config) {
                self.user = data;
                oldLogin = self.user.login;
            })
            .error(function(data, status, headers, config) {
                console.log(data);
            });
	}
	
	function onSuccess() {
        //pas tellement utile
	}
	
	function onError() {
		// voir si on peu mettre un message d'erreur
	}
	
	this.getServices = function getServices(){
    	self.services = InscriptionService.services.post({}, onSuccess, onError);
	};
    
    this.getTypesUser = function getTypesUser(){
    	self.typesUser = InscriptionService.typesUser.post({}, onSuccess, onError);

    };
	
	function updateUser() {
		var pass = self.currentPwd;
		if(self.user.pwd !== undefined && self.user.pwd !== "") {
			pass = self.user.pwd;
		}
		$http.post('/api/user', 
			{
				login: self.user.identifiant,
	            pwd: pass,
	            service: self.user.service.id,
	            typeUser: self.user.typeUtilisateur.id
			})
            .success(function(data, status, headers, config) {
                ipCookie('utilisateur', data.user, {expires : 7});
                ipCookie('token', data.token, {expires : 7});
				$rootScope.$broadcast("userLogin", ipCookie('utilisateur'));
				self.isProfilModified = true;
            })
            .error(function(data, status, headers, config) {
                console.log(data);
            });
	}
	
	function checkAccount(login, password, onSuccess, onError) {
		$http.post('/api/checkUser/' + oldLogin, {password: password, login: login})
            .success(function(data, status, headers, config) {
                if(onSuccess) {
					onSuccess(data, status, headers, config);
				}
            })
            .error(function(data, status, headers, config) {
				if(onError) {
					onError();
				}
            });
	}
	
	this.save = function save(){
		console.log(self.user);
		console.log(self.oldLogin);
		if((self.user.pwd !== undefined && self.user.pwd !== "" && self.userPwd !== undefined && self.userPwd !== "" && self.user.pwd === self.userPwd && self.currentPwd !== undefined && self.currentPwd !== "") || (self.currentPwd !== undefined && self.currentPwd !== "")) {
			checkAccount(self.user.login, self.currentPwd, updateUser);
		}
	};
	
	getUser();
	self.getTypesUser();
    self.getServices();
}
angular
    .module('portailAutoEval')
	.controller('ProfilCtrl', ProfilCtrl);