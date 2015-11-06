function CguCtrl($resource) {
	this.self = this;
	this.resultBook = {};

	var bookById = $resource('/api/book/:bookId',
        {},
        {
            'get': {method:'GET', isArray:false}
        }
    );
	
	this.resultBook = bookById.get({bookId: '1'});
}
angular
    .module('pocApp')
	.controller('CguCtrl', CguCtrl);