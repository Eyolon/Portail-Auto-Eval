function ConsultationController($http, $state, $scope, ConsultationService) {
	var self = this;
    this.etablissement = null;
    this.etablissements = [];
    this.service = null;
    this.services = [];
    this.question = {};
    this.questions = [];
    this.isSuccess = true;
    this.notes = [];
    this.nbrVotant = 0;
    var colorArray = ['#000000', '#582900', '#FE1B00', '#ED7F10', '#FFFF00', '#9EFD38'];
    // NOIR,MARON,ROUGE,ORANGE,JAUNE,VERT
    
    function cleanArray(array) {
    	var cache = {};
    	array = array.filter(function(elem,index,array){
	    		return cache[elem.id]?0:cache[elem.id]=1;
	    	});
    	  return array;
    	}
    
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
    	
    	var palier1=0;
    	var palier2=0;
    	var palier3=0;
    	var palier4=0;
    	var palier5=0;
    	var palier6=0;
    	
    	self.services = [];
    	var votant = [];
    	self.notes.$promise.then(function(messages){
    	     //Since you are overwriting the object here, there will no longer be a $Promise property so be careful about it when you try to chain through elsewhere after this
    		self.notes = messages.filter(function(obj) {
    			
    	    	 if(obj.valeur>0 && obj.valeur<11)palier1++;
    	    	 if(obj.valeur>10 && obj.valeur<26)palier2++;
    	    	 if(obj.valeur>25 && obj.valeur<51)palier3++;
    	    	 if(obj.valeur>50 && obj.valeur<76)palier4++;
    	    	 if(obj.valeur>75 && obj.valeur<91)palier5++;
    	    	 if(obj.valeur>90 && obj.valeur<=100)palier6++;
    	    	 
    	    	 self.services.push(obj.utilisateur.service);
    	    	 votant.push(obj.utilisateur);
    	      });
    		
    		$scope.data = [
        	               {key: "0-10",y: palier1},
        	               {key: "11-25",y: palier2},
        	               {key: "26-50",y: palier3},
        	               {key: "51-75",y: palier4},
        	               {key: "76-90",y: palier5},
        	               {key: "91-100",y: palier6}];
        	
    		self.services = cleanArray(self.services);
    		votant = cleanArray(votant);
    		self.nbrVotant = votant.length;
    	  });
    }
    
    this.update2 = function update2(){
    	
    	self.notes = ConsultationService.listNotes.post({idEtablissement:this.etablissement.id}, onSuccess, onError);
    	
    	var palier1=0;
    	var palier2=0;
    	var palier3=0;
    	var palier4=0;
    	var palier5=0;
    	var palier6=0;
    	
    	self.questions = [];
    	var votant = [];
    	
    	console.log(self.service);
    	
    	self.notes.$promise.then(function(messages){
    	     //Since you are overwriting the object here, there will no longer be a $Promise property so be careful about it when you try to chain through elsewhere after this
    		self.notes = messages.filter(function(obj) {
    			
    				
    			
    	    	 if(obj.valeur>0 && obj.valeur<11 && obj.utilisateur.service.id === self.service.id)palier1++;
    	    	 if(obj.valeur>10 && obj.valeur<26 && obj.utilisateur.service.id === self.service.id)palier2++;
    	    	 if(obj.valeur>25 && obj.valeur<51 && obj.utilisateur.service.id === self.service.id)palier3++;
    	    	 if(obj.valeur>50 && obj.valeur<76 && obj.utilisateur.service.id === self.service.id)palier4++;
    	    	 if(obj.valeur>75 && obj.valeur<91 && obj.utilisateur.service.id === self.service.id)palier5++;
    	    	 if(obj.valeur>90 && obj.valeur<=100 && obj.utilisateur.service.id === self.service.id)palier6++;
    	    	 
    	    	 //self.questions.push(obj.utilisateur.service);
    	    	 votant.push(obj.utilisateur.id);
    	      });
    		
    		$scope.data = [
        	               {key: "0-10",y: palier1},
        	               {key: "11-25",y: palier2},
        	               {key: "26-50",y: palier3},
        	               {key: "51-75",y: palier4},
        	               {key: "76-90",y: palier5},
        	               {key: "91-100",y: palier6}];
        	
    		//self.questions = cleanArray(self.services);
    		votant = cleanArray(votant);
    		self.nbrVotant = votant.length;
    	  });
    }

    this.update3 = function update3(){
    	

	}


}
angular
    .module('portailAutoEval')
    .controller('ConsultationController', ConsultationController);