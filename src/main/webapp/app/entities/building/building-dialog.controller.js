(function() {
    'use strict';

    angular
        .module('campuz360App')
        .controller('BuildingDialogController', BuildingDialogController);

    BuildingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Building'];

    function BuildingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Building) {
        var vm = this;

        vm.building = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.building.id !== null) {
                Building.update(vm.building, onSaveSuccess, onSaveError);
            } else {
                Building.save(vm.building, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('campuz360App:buildingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setBuildingCoverImage = function ($file, building) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        building.buildingCoverImage = base64Data;
                        building.buildingCoverImageContentType = $file.type;
                    });
                });
            }
        };

        vm.setBuildingImage1 = function ($file, building) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        building.buildingImage1 = base64Data;
                        building.buildingImage1ContentType = $file.type;
                    });
                });
            }
        };

        vm.setBuildingImage2 = function ($file, building) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        building.buildingImage2 = base64Data;
                        building.buildingImage2ContentType = $file.type;
                    });
                });
            }
        };

        vm.setBuildingImage3 = function ($file, building) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        building.buildingImage3 = base64Data;
                        building.buildingImage3ContentType = $file.type;
                    });
                });
            }
        };

    }
})();
