(function() {
    'use strict';

    angular
        .module('campuz360App')
        .factory('BuildingSearch', BuildingSearch);

    BuildingSearch.$inject = ['$resource'];

    function BuildingSearch($resource) {
        var resourceUrl =  'api/_search/buildings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
