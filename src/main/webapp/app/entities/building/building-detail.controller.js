(function() {
    'use strict';

    angular
        .module('campuz360App')
        .controller('BuildingDetailController', BuildingDetailController);

    BuildingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Building'];

    function BuildingDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Building) {
        var vm = this;

        vm.building = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('campuz360App:buildingUpdate', function(event, result) {
            vm.building = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
