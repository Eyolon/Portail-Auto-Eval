function EtablissementController(Notification, EtablissementService, ipCookie) {
	var self = this;
	this.batimentToAdd = undefined;
	
	this.addBatiment = function addBatiment() {
		EtablissementService.etablissement.post({etablissement: self.batimentToAdd}, onSuccess, onError);        
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
.controller('EtablissementController', EtablissementController);