function ConsultationController($http, $state, $scope, ConsultationService) {
	var self = this;
    this.etablissement = {};
    this.etablissements = [];
    this.service = {};
    this.services = [];
    this.question = {};
    this.questions = [];
    this.isSuccess = true;
    this.notes = [];
    var colorArray = ['#000000', '#582900', '#FE1B00', '#ED7F10', '#FFFF00', '#9EFD38'];
    // NOIR,MARON,ROUGE,ORANGE,JAUNE,VERT
    
    this.getListEtablissement = function getListEtablissement(){
    	self.etablissements = ConsultationService.etablissements.post({}, onSuccess, onError);    	
    };
    
    this.getListServices = function getListServices(){
    	self.services = ConsultationService.services.post({}, onSuccess, onError);    	
    };
    
    this.getListQuestions = function getListQuestions(){
    	self.questions = ConsultationService.questions.post({}, onSuccess, onError);    	
    };
    
    function onSuccess() {
        self.isSuccess = true;
	}
	
	function onError() {
		self.isSuccess = false;
	}

    $scope.options = {
        chart: {
            type: 'pieChart',
            height: 500,
            x: function (d) {
                return d.key;
            },
            y: function (d) {
                return d.y;
            },
            showLabels: true,
            duration: 500,
            labelThreshold: 0.01,
            labelSunbeamLayout: true,
            legend: {
                margin: {
                    top: 5,
                    right: 35,
                    bottom: 5,
                    left: 0
                }
            },
            legendPosition: 'right',
            color:colorArray 
        }
    };

    $scope.config = {
        visible: true, // default: true
        extended: false, // default: false
        disabled: false, // default: false
        refreshDataOnly: true, // default: true
        deepWatchOptions: true, // default: true
        deepWatchData: true, // default: true
        deepWatchDataDepth: 2, // default: 2
        debounce: 10 // default: 10
    };
    
    self.getListEtablissement();
    
    this.update = function update(){
    	
    	/*L'idée principal est la suivante: on va charger les notes par établissement et affiner au fur et a mesure
    	 * selon les critères choisis : services et question.
    	 * On va chargera les premières "investigation" l'axe d'entrée commun étant toujours l'établissement
    	 * Donc on va ici sobrement découper les résultat par palier sans tenir compte des autres critères
    	 * Mais cependant si ils ont été définis on en tiendra compte (en lancant successivement les fonctions qui 
    	 * sont assignés aux différent critère si ils existe
    	 * */
    	self.notes = ConsultationService.listNotes.post({idEtablissement:this.etablissement.id}, onSuccess, onError);
    	
    	console.log(self.notes);
    	
    	var objs = self.notes.map(JSON.parse);
    	
    	console.log(objs); // result : []... WHAT ??,?
    	
    	var palier1=0;
    	var palier2=0;
    	var palier3=0;
    	var palier4=0;
    	var palier5=0;
    	var palier6=0;
    	
    	$scope.data = [
    	               {key: "0-10",y: palier1},
    	               {key: "10-25",y: palier2},
    	               {key: "25-50",y: palier3},
    	               {key: "50-75",y: palier4},
    	               {key: "75-90",y: palier5},
    	               {key: "90-100",y: palier6}];
    	
    	self.getListServices();
    }
    
    this.update2 = function update2(){
    	self.getListQuestions();
    	
    }

    this.update3 = function update3(){
    	

	}


}
angular
    .module('portailAutoEval')
    .controller('ConsultationController', ConsultationController);