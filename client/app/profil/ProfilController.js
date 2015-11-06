function ProfilCtrl($filter, $http, $rootScope, ConnexionService, ipCookie) {
	var self = this;
	this.email = "";
	this.currentPwd = "";
	this.user = {};
	this.userPwd = "";
	this.isProfilModified = false;
	
	function getUser() {
		var id = ipCookie('utilisateur').id;
		$http.post('/api/userFull/' + id, {token: ipCookie("token")})
            .success(function(data, status, headers, config) {
                self.user = data;
				self.email = data.adresseMail;
				self.user.birthday = new Date($filter('date')(data.birthday, 'yyyy-MM-dd'));
            })
            .error(function(data, status, headers, config) {
                console.log(data);
            });
	}
	getUser();
	
	function updateUser() {
		var pass = self.currentPwd;
		if(self.user.pwd !== undefined && self.user.pwd !== "") {
			pass = self.user.pwd;
		}
		$http.post('/api/user', 
			{
				id: self.user.id,
				lastname: self.user.lastname,
				firstname: self.user.firstname,
				pwd: pass,
				adresseRue: self.user.adresseRue,
				adresseVille: self.user.adresseVille,
				adresseCodePostal: self.user.adresseCodePostal,
				adresseMail: self.user.adresseMail,
				telephone: self.user.telephone,
				genre: self.user.genre,
				birthday: self.user.birthday
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
	
	function checkAccount(oldMail, email, password, onSuccess, onError) {
		$http.post('/api/checkUser/' + oldMail, {password: password, email: email})
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
			checkAccount(self.email, self.user.adresseMail, self.currentPwd, updateUser);
		}
	};
}
angular
    .module('pocApp')
	.controller('ProfilCtrl', ProfilCtrl);