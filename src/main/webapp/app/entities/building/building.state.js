(function() {
    'use strict';

    angular
        .module('campuz360App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('building', {
            parent: 'entity',
            url: '/building?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Buildings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building/buildings.html',
                    controller: 'BuildingController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('building-detail', {
            parent: 'entity',
            url: '/building/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Building'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building/building-detail.html',
                    controller: 'BuildingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Building', function($stateParams, Building) {
                    return Building.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('building.new', {
            parent: 'building',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                buildingName: null,
                                buildingCoverImage: null,
                                buildingCoverImageContentType: null,
                                buildingImage1: null,
                                buildingImage1ContentType: null,
                                buildingImage2: null,
                                buildingImage2ContentType: null,
                                buildingImage3: null,
                                buildingImage3ContentType: null,
                                buildingEstablishedYear: null,
                                location: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: true });
                }, function() {
                    $state.go('building');
                });
            }]
        })
        .state('building.edit', {
            parent: 'building',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building.delete', {
            parent: 'building',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-delete-dialog.html',
                    controller: 'BuildingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
