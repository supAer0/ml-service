// //let KO know that we will take care of managing the bindings of our children
// ko.bindingHandlers.stopBinding = {
//     init: function() {
//         return { controlsDescendantBindings: true };
//     }
// };
//
// //KO 2.1, now allows you to add containerless support for custom bindings
// ko.virtualElements.allowedBindings.stopBinding = true;

// The Instance model
function Instance(id, name,keyName,status,dateCreate, abTest) {
    var self = this;
    self.id=id;
    self.name=ko.observable(name);
    self.keyName=ko.observable(keyName);
    self.status=ko.observable(status);
    self.abTest=ko.observable(abTest);
    self.dateCreate=ko.observable(dateCreate);
}
// The Instance view model
function InstanceViewModel() {
    var self = this;
    self.instances = ko.observableArray([]);

    self.id = ko.observable(null);
    self.name = ko.observable("");
    self.keyName = ko.observable("");
    self.status = ko.observable(true);
    self.abTest = ko.observable(false);
    self.dateCreate = ko.observable("");

    var ident = document.location.pathname.match(/\d+/);
    self.load = function () {
        $.ajax("/api/instances?id="+ident, {
            dataType: 'json',
            success: function (data) {
                console.log("Успешно обработан json запрос. Записи загружены");
                console.log(data);
                for (var i = 0; i < data.length; i++) {
                    self.instances.push(new Instance(data[i].id, data[i].name,data[i].keyName,data[i].status,data[i].dateCreate,data[i].abTest));
                }
                //console.log(self.instances());
            },
            error: function (data) {
                alert("error! " + data.error);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.addInstance = function () {
        var instance = new Instance();
        instance.id = self.id();
        instance.name(self.name());
        instance.keyName(self.keyName());
        instance.status(self.status());
        instance.abTest(self.abTest());
        var jsonData = ko.toJSON(instance);
        console.log(jsonData);
        // make POST request
        $.ajax("/api/createInstance?id="+ident, {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
                console.log(allData);
                var isNew = false;
                if (instance.id == null)
                     isNew = true;
                if(isNew)
                    self.instances.push(new Instance(allData.id,allData.name,allData.keyName,allData.status, allData.dateCreate,allData.abTest));
                else{
                    for (i = 0; i < self.instances().length; i++) {
                        if (instance.id == self.instances()[i].id) {
                            self.instances()[i].name(allData.name);
                            self.instances()[i].keyName(allData.keyName);
                            self.instances()[i].status(allData.status);
                            self.instances()[i].abTest(allData.abTest);

                            break;
                        }
                    }
                }
                self.cleanForm();
            },
            error: function (allData) {
                //alert(allData.responseText);
                alert("Упс, что-то пошло не так, попбуйти еще раз");
                console.log('error! Не могу отправить json запрос');
                console.log(allData);
            }
        });
        $('#createOrViewInsnace').modal("hide");
    };

    self.editInstance = function (instance) {
        $('#createOrViewInsnace').modal();
        console.log(instance);
        self.id(instance.id);
        self.name(instance.name());
        self.keyName(instance.keyName());
        self.status(instance.status());
        self.abTest(instance.abTest());
    };

    self.removeInstance = function (instance) {
        // var jsonData = ko.toJSON(instance);
        // console.log(jsonData);
        if (confirm("Вы подтверждаете удаление?")) {
            $.ajax("/api/deleteInstance?id=" + instance.id, {
                type: "get",
                contentType: 'application/json; charset=utf-8',
                success: function (allData) {
                    self.instances.remove(instance);
                },
                error: function (allData) {
                    alert("Упс, что-то пошло не так, попбуйти еще раз");
                    console.log('error! Не могу отправить json запрос');
                    console.log(allData);
                }
            });
        }


    };

    self.cleanForm = function () {
        self.name("");
        self.keyName("");
        self.status(true);
        self.id(null);
        self.abTest(false);
    };

    self.reload = function () {
        self.instances.removeAll();
        self.load();
    };


    self.reload();
}



// Activates knockout.js
//создаем экземпляр ViewModel
// var instanceViewModel = new InstanceViewModel();
// ko.applyBindings(instanceViewModel, document.getElementById("instanceViewModel"));

