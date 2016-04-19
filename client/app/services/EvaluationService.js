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
    
    this.listNoteAllReadyCommited = $resource('/api/reponse/:idUser',
            {idUser:'@idUser'},
            {
                'post': {method:'POST', isArray:true}
            }
        );
	
    this.setAnswer = function setAnswer(answers,user){
    	for(var key in answers){//List de formulaires
    		
    		if(!answers.hasOwnProperty(key))continue;
    		var obj = answers[key];// On extrait le formulaire
    		
    		var obj2 = obj['listQuestions'];//list de question
    		for(var key2 in obj2){
    			if(!obj2.hasOwnProperty(key2))continue;
    			
    			var questionFull = obj2[key2];// notre question
    			
    			if(questionFull.notes !== undefined){
    				//Donc la on envois toute les questions répondue uniquement
    				console.log(questionFull);
    				$http.post('/api/reponse',{
    					
    					questionFull: questionFull,
    					Utilisateur:user
    					
    				
    				});
    				
    			}
    			
    			
    			
    			
    			
    		}
    	}
    	
    	
    };

}


angular
    .module('portailAutoEval')
    .service('EvaluationService', EvaluationService);