function HomeCtrl($resource) {
	var self = this;
    this.cards = [];
    this.carouselIndex = 0;
	
	var allOeuvres = $resource('/api/oeuvres',
        {},
        {
            'get': {method:'GET', isArray:true}
        }
    );
	
	/*this.resultAllOeuvres = allOeuvres.get({}, function (result) {
		self.resultAllOeuvres = result;
		angular.forEach(self.resultAllOeuvres, function (oeuvre) {
			self.cards.push({id:  oeuvre.oeuvre.id,image: "/images/" + oeuvre.oeuvre.id + "-cover.jpg" , title: oeuvre.oeuvre.titre});
		});
	}, function (error) {
		console.log(error);
	});*/
}
angular
    .module('pocApp')
	.controller('HomeCtrl', HomeCtrl);