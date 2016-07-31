(function() {
    'use strict';

    angular
        .module('logsApp')
        .controller('UserManagementDeleteController', UserManagementDeleteController);

    UserManagementDeleteController.$inject = ['$uibModalInstance', 'entity', 'User'];

    function UserManagementDeleteController ($uibModalInstance, entity, User) {
        var vm = this;

        vm.user = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            User.delete({id: vm.user.id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
