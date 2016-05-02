function InitService(ConnexionService) {
    this.init = function init() {
		ConnexionService.init();
    };
}

angular
    .module('portailAutoEval')
    .service('InitService', InitService);