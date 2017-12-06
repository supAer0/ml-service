function SiteViewModel() {
    var self = this;

    self.siteId = ko.observable(null);
    self.siteTitle = ko.observable("");
    self.siteUrl = ko.observable("");
    var id = document.location.pathname.split("\/").pop();
    console.log(id);
    // self.load = function () {
    //     $.ajax("/api/site/", {
    //         dataType: 'json',
    //         success: function (data) {
    //             console.log("Успешно обработан json запрос. Записи загружены");
    //             console.log(data);
    //             for (i = 0; i < data.length; i++) {
    //                 self.sites.push(new Site(data[i].id, data[i].title,data[i].url));
    //             }
    //
    //             console.log(self.sites());
    //         },
    //         error: function (data) {
    //             alert("error! " + data.error);
    //             console.log('error! Не могу отправить json запрос');
    //             console.log(data);
    //         }
    //     });
    // };
    //
    // self.addSite = function () {
    //     var site = new Site();
    //     site.id = self.id();
    //     site.title(self.title());
    //     site.url(self.url());
    //     var jsonData = ko.toJSON(site);
    //     //console.log(jsonData);
    //     // make POST request
    //     $.ajax("/api/createSite", {
    //         data: jsonData,
    //         type: "post",
    //         contentType: 'application/json; charset=utf-8',
    //         success: function (allData) {
    //             //alert("Ваш url добавлен");
    //             self.sites.push(new Site(allData.id,allData.title,allData.url));
    //             self.title("");
    //             self.url("");
    //         },
    //         error: function (allData) {
    //             //alert(allData.responseText);
    //             alert("Упс, что-то пошло не так, попбуйти еще раз");
    //             console.log('error! Не могу отправить json запрос');
    //             console.log(allData);
    //         }
    //     });
    //     $('#createSite').modal("hide");
    // };
    //
    // self.reload = function () {
    //     self.sites.removeAll();
    //     self.load();
    // };
    //
    // self.reload();
}


// Activates knockout.js
//создаем экземпляр ViewModel
var siteViewModel = new SiteViewModel();
ko.applyBindings(siteViewModel);