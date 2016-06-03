function ConsultationController($http, $state, $scope, ConsultationService, ipCookie) {
    var self = this;
    this.etablissement = undefined;
    this.etablissements = [];
    this.service = undefined;
    this.services = [];
    this.formulaire = undefined;
    this.formulaires = [];
    this.question = undefined;
    this.questions = [];
    this.isSuccess = true;
    this.notes = [];
    this.user = {};
    
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

    this.getListEtablissement = function getListEtablissement() {
        
        
        var id = ipCookie('utilisateur').id;
		$http.post('/api/userFull/' + id, {token: ipCookie("token")})
            .success(function(data, status, headers, config) {
                self.user = data;
                self.oldLogin = self.user.login;
                if(self.user.typeUtilisateur.libelle === "administrateur"){
                	self.etablissements.push(self.user.etablissement);
                }
                
                if(self.user.typeUtilisateur.libelle === "super_administrateur"){
                	self.etablissements = ConsultationService.etablissements.post({}, onSuccess, onError);
                }
                
            })
            .error(function(data, status, headers, config) {
                console.log(data);
            });
        
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

    self.getListEtablissement();

    this.update = function update() {

        /*L'idée principal est la suivante: on va charger les notes par établissement et affiner au fur et a mesure
         * selon les critères choisis : services et question.
         * On va chargera les premières "investigation" l'axe d'entrée commun étant toujours l'établissement
         * Donc on va ici sobrement découper les résultat par palier sans tenir compte des autres critères
         * Mais cependant si ils ont été définis on en tiendra compte (en lancant successivement les fonctions qui 
         * sont assignés aux différent critère si ils existe
         * */
        var tampon = [];
        tampon = ConsultationService.listNotes.post({
            idEtablissement: this.etablissement.id
        }, onSuccess, onError);

        var palier1 = 0;
        var palier2 = 0;
        var palier3 = 0;
        var palier4 = 0;
        var palier5 = 0;
        var palier6 = 0;

        self.formulaires = [];
        self.services = [];
        self.questions = [];
        var votant = [];
        tampon.$promise.then(function (message) {
            //Since you are overwriting the object here, there will no longer be a $Promise property so be careful about it when you try to chain through elsewhere after this
            self.services = message.listService;
            self.formulairesByService = message.listFormulaire;
            self.questionsByFormulaires = message.listQuestion;
            if(message.listNote !== undefined && message.listNote.length > 0) {
                tampon = message.listNote.filter(function (obj) {
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
                    
                    votant.push(obj.utilisateur);
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

            self.services = cleanArray(self.services);
            votant = cleanArray(votant);
            self.nbrVotant = votant.length;
            self.notes = cleanArray(self.notes);

        });
    };

    this.update2 = function update2() {

        var palier1 = 0;
        var palier2 = 0;
        var palier3 = 0;
        var palier4 = 0;
        var palier5 = 0;
        var palier6 = 0;

        self.formulaires = self.formulairesByService[self.service.id];
        self.questions = [];
        var votant = [];

        for (var keyNote in self.notes) {
            var obj = self.notes[keyNote];

            if (obj.valeur > 0 && obj.valeur < 11 && obj.utilisateur.service.id === self.service.id) {
                palier1++;
            }
            if (obj.valeur > 10 && obj.valeur < 26 && obj.utilisateur.service.id === self.service.id) {
                palier2++;
            }
            if (obj.valeur > 25 && obj.valeur < 51 && obj.utilisateur.service.id === self.service.id) {
                palier3++;
            }
            if (obj.valeur > 50 && obj.valeur < 76 && obj.utilisateur.service.id === self.service.id) {
                palier4++;
            }
            if (obj.valeur > 75 && obj.valeur < 91 && obj.utilisateur.service.id === self.service.id) {
                palier5++;
            }
            if (obj.valeur > 90 && obj.valeur <= 100 && obj.utilisateur.service.id === self.service.id) {
                palier6++;
            }

            /*if (obj.utilisateur.service.id === self.service.id) {
                self.formulaires.push(obj.question.formulaire);
            }*/
            if (obj.utilisateur.service.id === self.service.id) {
                votant.push(obj.utilisateur.id);
            }
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

        self.formulaires = cleanArray(self.formulaires);
        votant = cleanArray(votant);
        self.nbrVotant = votant.length;

    };

    this.update3 = function update3() {

        var palier1 = 0;
        var palier2 = 0;
        var palier3 = 0;
        var palier4 = 0;
        var palier5 = 0;
        var palier6 = 0;

        self.questions = self.questionsByFormulaires[self.formulaire.id];
        var votant = [];

        for (var key in self.notes) {
            var obj = self.notes[key];

            if (obj.valeur > 0 && obj.valeur < 11 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                palier1++;
            }
            if (obj.valeur > 10 && obj.valeur < 26 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                palier2++;
            }
            if (obj.valeur > 25 && obj.valeur < 51 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                palier3++;
            }
            if (obj.valeur > 50 && obj.valeur < 76 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                palier4++;
            }
            if (obj.valeur > 75 && obj.valeur < 91 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                palier5++;
            }
            if (obj.valeur > 90 && obj.valeur <= 100 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                palier6++;
            }

            /*if (obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                self.questions.push(obj.question);
            }*/

            if (obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id) {
                votant.push(obj.utilisateur.id);
            }
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

        self.formulaires = cleanArray(self.formulaires);
        votant = cleanArray(votant);
        self.nbrVotant = votant.length;

    };

    this.update4 = function update4() {
        var palier1 = 0;
        var palier2 = 0;
        var palier3 = 0;
        var palier4 = 0;
        var palier5 = 0;
        var palier6 = 0;

        var votant = [];

        for (var key in self.notes) {
            var obj = self.notes[key];

            if (obj.valeur > 0 && obj.valeur < 11 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                palier1++;
            }
            if (obj.valeur > 10 && obj.valeur < 26 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                palier2++;
            }
            if (obj.valeur > 25 && obj.valeur < 51 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                palier3++;
            }
            if (obj.valeur > 50 && obj.valeur < 76 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                palier4++;
            }
            if (obj.valeur > 75 && obj.valeur < 91 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                palier5++;
            }
            if (obj.valeur > 90 && obj.valeur <= 100 && obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                palier6++;
            }

            if (obj.utilisateur.service.id === self.service.id && obj.question.formulaire.id === self.formulaire.id && obj.question.id === self.question.id) {
                votant.push(obj.utilisateur.id);
            }
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

        self.formulaires = cleanArray(self.formulaires);
        votant = cleanArray(votant);
        self.nbrVotant = votant.length;

    };

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
        result += keys.join(columnDelimiter);
        result += lineDelimiter;

        data.forEach(function (item) {

            result += item.id;
            result += columnDelimiter;
            result += item.question.valeur;
            result += columnDelimiter;
            result += item.utilisateur.service.libelle;
            result += columnDelimiter;
            result += item.valeur;

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
        saveFile(self.etablissement.libelle + '_Export.csv', 'application/csv', convertArrayOfObjectsToCSV({
            data: self.notes
        }));
    };


}
angular
    .module('portailAutoEval')
    .controller('ConsultationController', ConsultationController);