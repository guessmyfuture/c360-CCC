'use strict';

describe('Controller Tests', function() {

    describe('Building Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBuilding;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBuilding = jasmine.createSpy('MockBuilding');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Building': MockBuilding
            };
            createController = function() {
                $injector.get('$controller')("BuildingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'campuz360App:buildingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
