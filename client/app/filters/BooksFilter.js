angular.module('pocApp').filter('booksFilter', function ($filter) {
    function containsInList(list, value) {
        var isFound = false;
        if (list !== undefined) {
            list.some(function (item) {
                if ($filter('uppercase')(item).indexOf($filter('uppercase')(value)) !== -1) {
                    isFound = true;
                    return true;
                }
            });
        } else {
            return false;
        }
        return isFound;
    }
    
    function containsInListOfKeys(list, value1, value2) {
        var isFound = false;
        if (list !== undefined) {
            isFound = true;
            angular.forEach(list, function (item) {
                if ($filter('uppercase')(value1).indexOf($filter('uppercase')(item)) === -1 && $filter('uppercase')(value2).indexOf($filter('uppercase')(item)) === -1) {
                    isFound = false;
                }
            });
        } else {
            return false;
        }
        return isFound;
    }

    function containsInListAuteurs(list, value) {
        var isFound = false;
        if (list !== undefined && value !== undefined) {
            var searchValues = value.split(" ");
            list.some(function (item) {
                if (containsInListOfKeys(searchValues, item.nom, item.prenom)) {
                    isFound = true;
                    return true;
                }
            });
        } else {
            return false;
        }
        return isFound;
    }

    return function (listOeuvre, search) {
        var result = [];
        if (search !== undefined && ((search.auteur !== undefined && search.auteur !== "") || (search.titre !== undefined && search.titre !== "") || (search.typeOeuvre !== undefined && search.typeOeuvre !== "") || (search.genreOeuvre !== undefined && search.genreOeuvre !== ""))) {
            for (var i = 0; i < listOeuvre.length; i++) {
                if ((angular.isDefined(search.titre) && search.titre !== "" && $filter('uppercase')(listOeuvre[i].oeuvre.titre).indexOf($filter('uppercase')(search.titre)) !== -1) || angular.isUndefined(search.titre) || search.titre === "") {
                    if ((angular.isDefined(search.typeOeuvre) && search.typeOeuvre !== "" && $filter('uppercase')(listOeuvre[i].oeuvre.typeOeuvre).indexOf($filter('uppercase')(search.typeOeuvre)) !== -1) || angular.isUndefined(search.typeOeuvre) || search.typeOeuvre === "") {
                        if ((angular.isDefined(search.genreOeuvre) && search.genreOeuvre !== "" && listOeuvre[i].genreOeuvre !== undefined && containsInList(listOeuvre[i].genreOeuvre, search.genreOeuvre)) || angular.isUndefined(search.genreOeuvre) || search.genreOeuvre === "") {
                            if ((angular.isDefined(search.auteur) && search.auteur !== "" && listOeuvre[i].auteurs !== undefined && containsInListAuteurs(listOeuvre[i].auteurs, search.auteur)) || angular.isUndefined(search.auteur) || search.auteur === "") {
                                result.push(listOeuvre[i]);
                            }
                        }
                    }
                }
            }
        } else {
            result = listOeuvre;
        }
        return result;
    };
});