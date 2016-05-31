function PriorisationController($http, $state, $scope, PriorisationService,  ipCookie) {
    var self = this;
    this.isSuccess = true;
    this.notes = [];
    this.priorisationList = [];
    this.user = ipCookie('utilisateur');
    this.formulairesByService = {};
    this.questionsByFormulaires = {};
    this.nbrVotant = 0;
    
    var colorArray = ['#000000', '#582900', '#FE1B00', '#ED7F10', '#FFFF00', '#9EFD38'];
    // NOIR,MARON,ROUGE,ORANGE,JAUNE,VERT

    function cleanArray(array) {
        var cache = {};
        array = array.filter(function (elem, index, array) {
            return cache[elem.id] ? 0 : cache[elem.id] = 1;
        });
        return array;
    }
    
    this.getListPriorites = function getListPriorites(){
    	self.priorisationList = PriorisationService.listPriorisation.post({idUser: self.user.id}, onSuccess, onError);    	
    };
    
    this.getListNotes = function getListNotes(){
    	self.notes = PriorisationService.listNotes.post({idUser: self.user.id}, onSuccess, onError);    	
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
            color: colorArray
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

    self.getListPriorites();
    

    this.update = function update() {
    	
        var tampon = [];
        
        tampon = PriorisationService.listNotes.post({idUser: self.user.id}, onSuccess, onError);
        
       
        var palier1 = 0;
        var palier2 = 0;
        var palier3 = 0;
        var palier4 = 0;
        var palier5 = 0;
        var palier6 = 0;
        
        tampon.$promise.then(function (message) {
        	if(message.ListNote !== undefined && message.ListNote.length > 0) {
                tampon = message.ListNote.filter(function (obj) {
                	
                    if (obj.valeur > 0 && obj.valeur < 11) {
                        palier1++;
                    }
                    if (obj.valeur > 10 && obj.valeur < 26) {
                        palier2++;
                    }
                    if (obj.valeur > 25 && obj.valeur < 51) {
                        palier3++;
                    }
                    if (obj.valeur > 50 && obj.valeur < 76) {
                        palier4++;
                    }
                    if (obj.valeur > 75 && obj.valeur < 91) {
                        palier5++;
                    }
                    if (obj.valeur > 90 && obj.valeur <= 100) {
                        palier6++;
                    }
                    
                    self.notes.push(obj); //On fait de l'exploit pour plus avoir a le refaire
                });
            }

            $scope.data = [{
                    key: "0-10",
                    y: palier1
                },
                {
                    key: "11-25",
                    y: palier2
                },
                {
                    key: "26-50",
                    y: palier3
                },
                {
                    key: "51-75",
                    y: palier4
                },
                {
                    key: "76-90",
                    y: palier5
                },
                {
                    key: "91-100",
                    y: palier6
                }];

            
            self.notes = cleanArray(self.notes);

        });
    };
    
    self.update();

    function convertArrayOfObjectsToCSV(args) {
        var result, ctr, keys, columnDelimiter, lineDelimiter, data;

        data = args.data || undefined;
        if (data === undefined || data.length === 0) {
            return undefined;
        }

        columnDelimiter = args.columnDelimiter || ';';
        lineDelimiter = args.lineDelimiter || '\n';

        keys = Object.keys(data[0]);
        
        result = '';
        //result += keys.join(columnDelimiter);
        result += "Question ;Justification ;Indice de priorisation ;Axe amelioration 1;Axe amelioration 2";
        result += lineDelimiter;

        data.forEach(function (item) {

            result += item.utilisateur.question.valeur;
            result += columnDelimiter;
            result += item.remarque;
            result += columnDelimiter;
            result += item.utilisateur.priorisation;
            result += columnDelimiter;
            result += item.utilisateur.axeAmelioration1;
            result += columnDelimiter;
            result += item.utilisateur.axeAmelioration2;

            result += lineDelimiter;

        });

        return result;
    }

    function saveFile(name, type, data) {
        if (data !== undefined && navigator.msSaveBlob) {
            return navigator.msSaveBlob(new Blob([data], {
                type: type
            }), name);
        }
        var a = $("<a style='display: none;'/>");
        var url = window.URL.createObjectURL(new Blob([data], {
            type: type
        }));
        a.attr("href", url);
        a.attr("download", name);
        $("body").append(a);
        a[0].click();
        window.URL.revokeObjectURL(url);
        a.remove();
    }

    this.download = function download() {
        saveFile(self.user.login + '_Export_Priorisation.csv', 'application/csv', convertArrayOfObjectsToCSV({
            data: self.priorisationList
        }));
    };


}
angular
    .module('portailAutoEval')
    .controller('PriorisationController', PriorisationController);