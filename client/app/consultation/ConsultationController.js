function ConsultationController($scope) {
    this.self = this;
    var colorArray = ['#000000', '#660000', '#CC0000', '#FF6666', '#FF3333', '#FF6666', '#FFE6E6'];

    $scope.data = [
        {
            key: "One",
            y: 5
        },
        {
            key: "Two",
            y: 2
        },
        {
            key: "Three",
            y: 9
        },
        {
            key: "Four",
            y: 7
        },
        {
            key: "Five",
            y: 4
        },
        {
            key: "Six",
            y: 3
        },
        {
            key: "Seven",
            y: 9
        }];

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
            legendPosition: 'right'
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

    /*$scope.colorFunction = function () {
        return function (d, i) {
            return colorArray[i];
        };
    };

    $scope.xFunction = function () {
        return function (d) {
            return d.key;
        };
    };

    $scope.yFunction = function () {
        return function (d) {
            return d.y;
        };
    };*/


}
angular
    .module('portailAutoEval')
    .controller('ConsultationController', ConsultationController);