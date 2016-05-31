function EvaluationService($resource,$http){

    this.listQuestionnaire = $resource('/api/evaluation',
        {},
        {
            'post': {method:'POST', isArray:true}
        }
    );

    
    this.listFormulaireFull = $resource('/api/evaluation/:idUser',
            {idUser:'@idUser'},
            {
                'post': {method:'POST', isArray:true}
            }
        );
    
    this.questionnaire = $resource('/api/evaluation/:nomForm',
        {noForm:'@nomForm'},
        {
            'post': {method:'POST', isArray:false}
        }
    );
    
	
    this.setAnswer = function setAnswer(answers, user){
    	
    	
    	for(var key in answers){//List de formulaires
    		
    		if(!answers.hasOwnProperty(key)) {
                continue;
            }
    		
    		
    		var obj = answers[key];// On extrait le formulaire
    		//var obj2 = obj.listQuestions;//list de question
    			
    		
    			var idReponse = undefined;
    			if(obj.notes !== undefined){
	    			for(var note in obj.notes){
	    				if(obj.notes[note].id !== undefined){
	    				idReponse = obj.notes[note].id;
	    				}
	    			}
    			}   			
    			
    			var idQuestion = obj.id;
    		
    			var reponseFull = obj.notes;//notre réponse
    			
    			var noteComplement = {};
    			
    			if(reponseFull.valeur !== undefined) {
    				//Donc la on envois toute les questions nouvellement répondue uniquement
    				noteComplement = {
    						id :undefined,
    						valeur :reponseFull.valeur,  
    						remarque :undefined, 
    						gravite :undefined, 
    						axeAmelioration1 :undefined,
    						axeAmelioration2 :undefined,
    						amelioration: undefined,
    						idQuestion :idQuestion
    					};
    				
    				if(idReponse !== undefined){noteComplement.id = idReponse;}
    				if(reponseFull.remarque !== undefined){noteComplement.remarque = reponseFull.remarque;}
					if(reponseFull.axeAmelioration1 !== undefined){noteComplement.axeAmelioration1 = reponseFull.axeAmelioration1;}
					if(reponseFull.axeAmelioration2 !== undefined){noteComplement.axeAmelioration2 = reponseFull.axeAmelioration2;}
					if(reponseFull.gravite !== undefined){noteComplement.gravite = reponseFull.gravite;}
					if(reponseFull.amelioration !== undefined){noteComplement.amelioration = reponseFull.amelioration;}
										
    				reponseFull = noteComplement;
    				
    				$http.post('/api/reponse',{reponseFull:reponseFull, utilisateur:user});
    				
    			}
    			
    			else{
    				for(var obj2 in reponseFull){
	    				
	    				
	    				//Donc la on envois toute les questions editer uniquement
	    				noteComplement = {
	    						id :reponseFull[obj2].id,
	    						valeur :reponseFull[obj2].valeur,  
	    						remarque :undefined, 
	    						gravite :undefined, 
	    						axeAmelioration1 :undefined,
	    						axeAmelioration2 :undefined,
	    						amelioration: undefined,
	    						idQuestion :idQuestion
	    					};
	    				
	    				
	    				if(reponseFull[obj2].remarque !== undefined){noteComplement.remarque = reponseFull[obj2].remarque;}
						if(reponseFull[obj2].utilisateur.axeAmelioration1 !== undefined){noteComplement.axeAmelioration1 = reponseFull[obj2].utilisateur.axeAmelioration1;}
						if(reponseFull[obj2].utilisateur.axeAmelioration2 !== undefined){noteComplement.axeAmelioration2 = reponseFull[obj2].utilisateur.axeAmelioration2;}
						if(reponseFull[obj2].utilisateur.gravite !== undefined){noteComplement.gravite = reponseFull[obj2].utilisateur.gravite;}
						if(reponseFull[obj2].utilisateur.amelioration !== undefined){noteComplement.amelioration = reponseFull[obj2].utilisateur.amelioration;}
											
	    				reponseFull = noteComplement;
	    				
	    				$http.post('/api/reponse',{reponseFull:reponseFull, utilisateur:user});
	    				}
	    			}
    			
    		
    	}
    };

}


angular
    .module('portailAutoEval')
    .service('EvaluationService', EvaluationService);