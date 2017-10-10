app.controller('billBuyCreateUpdateCtrl', ['BillBuyTypeService', 'BillBuyService', 'BranchService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'billBuy',
    function (BillBuyTypeService, BillBuyService, BranchService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, billBuy) {

        $scope.billBuy = billBuy;

        $scope.title = title;

        $scope.action = action;

        $timeout(function () {
            BillBuyTypeService.findAll().then(function (data) {
                $scope.billBuyTypes = data;
            });
            BranchService.fetchBranchCombo().then(function (data) {
                $scope.branches = data;
            });
        }, 2000);

        $scope.submit = function () {
            switch ($scope.action) {
                case 'create' :
                    BillBuyService.create($scope.billBuy).then(function (data) {
                        $uibModalInstance.close(data);
                    });
                    break;
                case 'update' :
                    BillBuyService.update($scope.billBuy).then(function (data) {
                        $uibModalInstance.close(data);
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);