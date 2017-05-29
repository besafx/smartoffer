app.controller('accountByBranchCtrl', ['BranchService', '$scope', '$rootScope', '$timeout', '$uibModalInstance',
    function (BranchService, $scope, $rootScope, $timeout, $uibModalInstance) {

        $timeout(function () {
            $scope.buffer = {};
            BranchService.fetchTableData().then(function (data) {
                $scope.branches = data;
            });
        }, 1500);

        $scope.submit = function () {
            if ($scope.buffer.startDate && $scope.buffer.endDate) {
                window.open('/report/AccountByBranch/'
                    + $scope.buffer.branch.id + "?"
                    + "startDate=" + $scope.buffer.startDate.getTime() + "&"
                    + "endDate=" + $scope.buffer.endDate.getTime());
            } else {
                window.open('/report/AccountByBranch/' + $scope.buffer.branch.id);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);