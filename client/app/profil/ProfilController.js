function ProfilCtrl($filter, $http, $rootScope, ConnexionService, InscriptionService, ipCookie) {
	var self = this;
	this.currentPwd = "";
	this.user = {};
	this.userPwd = "";
	this.isProfilModified = false;
	this.services = [];
    this.typesUser = [];
    this.oldLogin = "";
    this.listUser = [];
    this.userToEdit = {};
	
	function getUser() {
		var id = ipCookie('utilisateur').id;
		$http.post('/api/userFull/' + id, {token: ipCookie("token")})
            .success(function(data, status, headers, config) {
                self.user = data;
                oldLogin = self.user.login;
                if(self.user.typeUtilisateur.libelle === "administrateur"){
                	getAllUser();
                }
                
            })
            .error(function(data, status, headers, config) {
                console.log(data);
            });
	}
	
	function getAllUser() {	
    	self.services = InscriptionService.services.post({}, onSuccess, onError);
    	self.typesUser = InscriptionService.typesUser.post({}, onSuccess, onError);	
        self.listUser = InscriptionService.listUtilisateur.post({}, onSuccess, onError);
        console.log(self.listUser);
            
	}
	
	function updateUser() {
		var pass = self.currentPwd;
		if(self.user.pwd !== undefined && self.user.pwd !== "") {
			pass = self.user.pwd;
		}
		$http.post('/api/user', 
			{
				id: self.user.id,
				login: self.user.login,
	            pwd: pass,
	            service: self.user.service,
	            typeUser: self.user.typeUtilisateur
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
	
	function updateUserAdmin() {
		var pass = self.currentPwd;
		if(self.userToEdit.pwd !== undefined && self.userToEdit.pwd !== "") {
			pass = self.userToEdit.pwd;
		}
		$http.post('/api/user', 
			{
				id: self.userToEdit.id,
				login: self.userToEdit.login,
	            pwd: pass,
	            service: self.userToEdit.service,
	            typeUser: self.userToEdit.typeUtilisateur
			})
            .success(function(data, status, headers, config) {
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
		if((self.user.pwd !== undefined && self.user.pwd !== "" && self.userPwd !== undefined && self.userPwd !== "" && self.user.pwd === self.userPwd && self.currentPwd !== undefined && self.currentPwd !== "") || (self.currentPwd !== undefined && self.currentPwd !== "")) {
			checkAccount(self.user.login, self.currentPwd, updateUser);
		}
	}
	
	this.saveAdmin = function saveAdmin(){
		if((self.userToEdit.pwd === self.userPwd)) {
			updateUserAdmin();
		}
	}

	function onSuccess() {
        
	}
	
	function onError() {
		
	}
	
	getUser();
	
}
angular
    .module('portailAutoEval')
	.controller('ProfilCtrl', ProfilCtrl);