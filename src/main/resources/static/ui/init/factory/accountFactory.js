app.factory("AccountService", ['$http', '$log',
    function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/account/findAll").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/account/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (account) {
                return $http.post("/api/account/create", account).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/account/delete/" + id).then(function (response) {
                    return response.data;
                });
            },
            update: function (account) {
                return $http.put("/api/account/update", account).then(function (response) {
                    return response.data;
                });
            },
            findByBranch: function (branchId) {
                return $http.get("/api/account/findByBranch/" + branchId).then(function (response) {
                    return response.data;
                });
            },
            findByMaster: function (masterId) {
                return $http.get("/api/account/findByMaster/" + masterId).then(function (response) {
                    return response.data;
                });
            },
            findRequiredPrice: function (accountId) {
                return $http.get("/api/account/findRequiredPrice/" + accountId).then(function (response) {
                    return response.data;
                });
            },
            findRemainPrice: function (accountId) {
                return $http.get("/api/account/findRemainPrice/" + accountId).then(function (response) {
                    return response.data;
                });
            },
            findPaidPrice: function (accountId) {
                return $http.get("/api/account/findPaidPrice/" + accountId).then(function (response) {
                    return response.data;
                });
            },
            fetchTableData: function () {
                return $http.get("/api/account/fetchTableData").then(function (response) {
                    return response.data;
                });
            },
            fetchCount: function () {
                return $http.get("/api/account/fetchCount").then(function (response) {
                    return response.data;
                });
            },
            fetchAccountsCountByBranch: function (id) {
                return $http.get("/api/account/fetchAccountsCountByBranch/" + id).then(function (response) {
                    return response.data;
                });
            },
            fetchAccountsCountByMaster: function (id) {
                return $http.get("/api/account/fetchAccountsCountByMaster/" + id).then(function (response) {
                    return response.data;
                });
            },
            fetchAccountsCountByCourse: function (id) {
                return $http.get("/api/account/fetchAccountsCountByCourse/" + id).then(function (response) {
                    return response.data;
                });
            },
            filter: function (search) {
                return $http.get("/api/account/filter?" + search).then(function (response) {
                    return response.data;
                });
            }
        };
    }
]);