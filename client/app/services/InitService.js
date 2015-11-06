function InitService(ConnexionService) {
    var self = this;

    this.init = function init() {
		ConnexionService.init();
    };
}

angular
    .module('pocApp')
    .service('InitService', InitService);