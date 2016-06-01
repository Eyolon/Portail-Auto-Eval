function ServiceUtilisateurController(Notification, ServiceUtilisateurService, ipCookie) {
	var self = this;
	this.serviceToAdd = undefined;
	
	this.addService = function addService() {
		ServiceUtilisateurService.service.post({service: self.serviceToAdd}, onSuccess, onError);        
    };
	
	function onSuccess() {
        Notification.success("Service Ajouté");
    }

    function onError() {
    	Notification.error("Erreur dans l'ajout du service");
    }
	
}
angular
.module('portailAutoEval')
.controller('ServiceUtilisateurController', ServiceUtilisateurController);