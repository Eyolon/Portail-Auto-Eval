function ServiceUtilisateurController(Notification, ServiceUtilisateurService, ipCookie) {
	var self = this;
	this.serviceToAdd = undefined;
	
	this.addBatiment = function addBatiment() {
		ServiceUtilisateurService.service.post({service: self.serviceToAdd}, onSuccess, onError);        
    };
	
	function onSuccess() {
        Notification.success("Etablissement Ajouté");
    }

    function onError() {
    	Notification.error("Erreur dans l'ajout de l'établissement");
    }
	
}
angular
.module('portailAutoEval')
.controller('ServiceUtilisateurController', ServiceUtilisateurController);