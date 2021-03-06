(function() {
    'use strict';
    angular
        .module('testJhApp')
        .factory('ExtandedUser', ExtandedUser);

    ExtandedUser.$inject = ['$resource', 'DateUtils'];

    function ExtandedUser ($resource, DateUtils) {
        var resourceUrl =  'api/extanded-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastConnection = DateUtils.convertDateTimeFromServer(data.lastConnection);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
