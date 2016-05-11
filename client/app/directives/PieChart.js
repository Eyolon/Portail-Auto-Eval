function pieChart($window) {
    return {
        restrict: 'E',
        scope: {
            data:'='
        },
        link: function link(scope, element) {
            var currentElement = d3.select(element[0]);

			var margin = {top: 10, right: 10, bottom: 10, left: 10};

			var width, height, drawableHeight, drawableWidth;
			function updateSize() {
				width = element[0].parentElement.clientWidth;
				height = element[0].parentElement.clientHeight;
				drawableHeight = height - margin.top - margin.bottom;
				drawableWidth = width - margin.left - margin.right;
			}
			updateSize();

			scope.render = function (newData) {

				currentElement.selectAll('*').remove();

                //TODO la création du pie
			};

			var w = angular.element($window);
			w.bind('resize', function () {
				updateSize();
				scope.render(scope.data);
				scope.$apply();
			});

			scope.$watch('data', function (newData, oldData) {
				if (newData !== undefined) {
					scope.render(scope.data);
				}
			});

			function onDestroy() {
				w.unbind('resize');
			}
			scope.$on('$destroy', onDestroy);
        }
    };
}
angular
    .module('portailAutoEval')
    .directive('pieChart',pieChart);