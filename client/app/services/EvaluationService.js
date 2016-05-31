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
            
    		for(var key2 in obj){
    			if(!obj.hasOwnProperty(key2)) {
                    continue;
                }
    			
    			var reponseFull = obj[key2];// notre question
 
    			reponseFull = reponseFull.notes;//notre réponse
    			   			
    			if(reponseFull.valeur !== undefined) {
    				//Donc la on envois toute les questions répondue uniquement
    		   			
    				//var notePatched = [];
    				var noteComplement = {
    						id :reponseFull.id,
    						valeur :reponseFull.valeur, 
    						remarque : reponseFull.remarque, 
    						priorisation :reponseFull.priorisation, 
    						axeAmelioration1 :reponseFull.axeAmelioration1,
    						axeAmelioration2 :reponseFull.axeAmelioration2,
    						idQuestion :obj[key2].id};
    						
    				//notePatched.push(noteComplement);
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