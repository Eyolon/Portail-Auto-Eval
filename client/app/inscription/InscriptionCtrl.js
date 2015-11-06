function InscriptionCtrl($http, $state, ConnexionService) {
	var self = this;
	this.user = {};
	this.pwdCheck = "";
	
	this.sInscrire = function sInscrire() {
		if(self.pwdCheck !== undefined && self.pwdCheck !== "" && self.user.pwd !== undefined && self.user.pwd !== "" && self.pwdCheck === self.user.pwd && self.user.isAgree) {
			insertUser();
		}
	};
	
	function insertUser() {
		$http.post('/api/insertUser', 
			{
				id: self.user.id,
				lastname: self.user.lastname,
				firstname: self.user.firstname,
				pwd: self.user.pwd,
				adresseRue: self.user.adresseRue,
				adresseVille: self.user.adresseVille,
				adresseCodePostal: self.user.adresseCodePostal,
				adresseMail: self.user.adresseMail,
				telephone: self.user.telephone,
				genre: self.user.genre,
				birthday: self.user.birthday
			})
            .success(function(data, status, headers, config) {
                ConnexionService.seConnecter(self.user.adresseMail, self.user.pwd);
				$state.go('home');
            })
            .error(function(data, status, headers, config) {
                console.log(data);
            });
	}
}
angular
    .module('pocApp')
	.controller('InscriptionCtrl', InscriptionCtrl);