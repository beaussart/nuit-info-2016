(function() {
    'use strict';
    angular
        .module('testJhApp')
        .factory('Message', Message);

    Message.$inject = ['$resource', 'DateUtils'];

    function Message ($resource, DateUtils) {
        var resourceUrl =  'api/messages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateWriten = DateUtils.convertDateTimeFromServer(data.dateWriten);
                        data.dateSeen = DateUtils.convertDateTimeFromServer(data.dateSeen);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
