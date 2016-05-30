function ConnexionService($http, $resource, $rootScope, ipCookie) {
    var self = this;
	var isConnected = false;

    this.init = function init() {
		if(ipCookie('token') && ipCookie('utilisateur')) {
            self.seConnecter();
			isConnected = true;
		} else {
			isConnected = false;
		}
    };
    
    var connection = $resource('/api/logger/:login', 
			{
                login: '@login',
                password: '@password',
                token: '@token' 
            }, 
			{'get': { method: 'GET'}
    });

    this.seConnecter = function seConnecter(login, password, onSuccess, onError) {
        var loginToSend = login === undefined ? '' : '/' + login;
        $http.post('/api/logger' + loginToSend, {password: password, token: ipCookie('token')})
            .success(function(data, status, headers, config) {
                isConnected = true;
                ipCookie('utilisateur', data.utilisateur, {expires : 7});
                ipCookie('token', data.token, {expires : 7});
                if(onSuccess) {
					onSuccess(data, status, headers, config);
				}
				$rootScope.$broadcast("userLogin", data.utilisateur);
            })
            .error(function(data, status, headers, config) {
                self.logout();
				if(onError) {
					onError();
				}
            });
    };

    this.checkIsConnected = function checkIsConnected() {
        if(isConnected === false) {
            if (ipCookie('token') && ipCookie('utilisateur')) {
                isConnected = true;
            }
            else {
                isConnected = false;
            }
        }
        return isConnected;
    };
	
	this.logout = function logout() {
		isConnected = false;
        ipCookie.remove('utilisateur');
        ipCookie.remove('token');
		$rootScope.$broadcast("userLogout");
    };
}

angular
    .module('portailAutoEval')
    .service('ConnexionService', ConnexionService);