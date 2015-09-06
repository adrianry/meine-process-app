(function (global) {
    'use strict';

    var CamSDK = global.CamSDK;
    var angular = global.angular;


    var camClient = new CamSDK.Client({
        mock: false,
        apiUri: '/engine-rest'
    });

    var ProcessDefinition = camClient.resource('process-definition');
    var ProcessInstance = camClient.resource('process-instance');

    var app = angular.module('example.app', ['cam.embedded.forms']);

    app.controller('appCtrl', ['$scope', function ($scope) {
        $scope.camForm = null;

        function loadProcesses() {
            ProcessDefinition.list(function (err, result) {
                if (err) {
                    throw err;
                }
                var total = result.count;
                var processDefinitionInstances = result.items;
                console.log("total definitionen: " + total);
            });
        }

        function startProcess(){
            console.log("los gehts");
            var params = {id:'meine-process-app:1:61b016ac-49bb-11e5-977c-005056c00001',variables:{}};
            ProcessDefinition.start(params, function (err, result) {
                if (err) {
                    console.log(err);
                }
                console.log("resultat: " + JSON.stringify(result));
            });
        }

        function FormKey(){
            //ProcessInstance.
        }


        startProcess();
    }]);

    angular.bootstrap(document, ['example.app']);

})(this);
