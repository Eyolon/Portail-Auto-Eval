function DeveloppeurCtrl($filter, $resource) {
	var self = this;
	
	this.resultUserList = [];

	var users = $resource('/api/users',
        {},
        {
            'get': {method:'GET', isArray:true}
        }
    );
	var userById = $resource('/api/user/:userId',
        {},
        {
            'get': {method:'GET', isArray:false}
        }
    );
	this.resultUserList = users.get(function(result){
		self.resultUserList = result;
		angular.forEach(self.resultUserList, function(user){
			var date = new Date(user.birthday);
			user.birthday = $filter('date')(date, "dd/MM/yyyy 'à' h:mm");
		});
	});
}
angular
    .module('pocApp')
	.controller('DeveloppeurCtrl', DeveloppeurCtrl);