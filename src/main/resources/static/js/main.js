// The url model
function Nisha(id, title) {
    var self = this;
    self.id=id;
    self.title=ko.observable(title);
}
// The url view model
function NishaViewModel() {
    var self = this;
    self.niches = ko.observableArray([]);

    self.id = ko.observable(null);
    self.title = ko.observable("");

    self.load = function () {
        $.ajax("api/niches", {
            dataType: 'json',
            success: function (data) {
                console.log("Успешно обработан json запрос. Записи загружены");
                console.log(data);
                for (i = 0; i < data.length; i++) {
                    self.niches.push(new Nisha(data[i].id, data[i].title));
                }

                console.log(self.niches());
            },
            error: function (data) {
                alert("error! " + data.error);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.addNisha = function () {
        var nisha = new Nisha();
        nisha.id = self.id();
        nisha.title(self.title());
        var jsonData = ko.toJSON(nisha);
        console.log(jsonData);
        // make POST request
        $.ajax("api/createNisha", {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
                //alert("Ваш url добавлен");
                self.niches.unshift(new Nisha(allData.id,allData.title));
                self.title("");
            },
            error: function (allData) {
                //alert(allData.responseText);
                alert("Упс, что-то пошло не так, попбуйти еще раз");
                console.log('error! Не могу отправить json запрос');
                console.log(allData);
            }
        });
    };

    self.reload = function () {
        self.niches.removeAll();
        self.load();
    };

    self.reload();
}


// Activates knockout.js
//создаем экземпляр ViewModel
var nishaViewModel = new NishaViewModel();
ko.applyBindings(nishaViewModel);