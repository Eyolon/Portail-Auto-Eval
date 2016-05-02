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
    		var obj2 = obj.listQuestions;//list de question
            
    		for(var key2 in obj2){
    			if(!obj2.hasOwnProperty(key2)) {
                    continue;
                }
    			
    			var questionFull = obj2[key2];// notre question
    			
    			if(questionFull.notes.valeur !== undefined) {
    				//Donc la on envois toute les questions répondue uniquement
    		
    				var notePatched = [];
    				var noteComplement = {valeur :questionFull.notes.valeur, remarque : questionFull.notes.remarque};
    				notePatched.push(noteComplement);
    				questionFull.notes = notePatched;
    				
    				$http.post('/api/reponse',{questionFull: questionFull,utilisateur:user});
    				
    			}
    		}
    	}
    };

}


angular
    .module('portailAutoEval')
    .service('EvaluationService', EvaluationService);