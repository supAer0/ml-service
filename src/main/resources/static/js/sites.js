var HOME_URL = "http://ml.clever-tech.xyz/";
var OPENAPI_PATH = HOME_URL + "openapi/getContent?token=";
// The site model
function Site(id, title,url, token) {
    var self = this;
    self.id=id;
    self.title=ko.observable(title);
    self.url=ko.observable(url);
    self.token=token;
}
// The url view model
function SitesViewModel() {
    var self = this;
    self.sites = ko.observableArray([]);

    self.id = ko.observable(null);
    self.title = ko.observable("");
    self.url = ko.observable("");
    self.token = ko.observable("");
    //THIS SITE
    self.siteId = ko.observable(null);
    self.siteTitle = ko.observable("");
    self.siteUrl = ko.observable("");
    self.siteToken = ko.observable("");
    var thisSite = new Site;
    var ident = document.location.pathname.match(/\d+/);

    self.load = function () {
        $.ajax("/api/sites", {
            dataType: 'json',
            success: function (data) {
                console.log("Успешно обработан json запрос. Записи загружены");
                console.log(data);
                for (i = 0; i < data.length; i++) {
                    self.sites.push(new Site(data[i].id, data[i].title,data[i].url,data[i].token));
                    if (data[i].id == ident) {
                        thisSite = new Site(data[i].id, data[i].title, data[i].url,data[i].token);
                    }
                }


                console.log("TEST"  + thisSite.title());
                self.siteId(thisSite.id);
                self.siteTitle(thisSite.title());
                self.siteUrl(thisSite.url());
                self.siteToken(OPENAPI_PATH + thisSite.token +"&ml_type=");

                console.log(self.sites());
            },
            error: function (data) {
                alert("error! " + data.error);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });
    };
    
    self.deleteSite = function () {
        if (confirm("Вы подтверждаете удаление?")) {
            $.ajax("/api/deleteSite?id=" + ident, {
                type: "get",
                contentType: 'application/json; charset=utf-8',
                success: function (allData) {
                    //console.log(allData);
                    window.location.href = HOME_URL;
                },
                error: function (allData) {
                    //alert(allData.responseText);
                    alert("Упс, что-то пошло не так, попбуйти еще раз");
                    console.log('error! Не могу отправить json запрос');
                    console.log(allData);
                }
            });
        }
    };


    self.addSite = function () {
        var site = new Site();
        site.id = self.id();
        site.title(self.title());
        site.url(self.url());
        var jsonData = ko.toJSON(site);
        //console.log(jsonData);
        // make POST request
        $.ajax("/api/createSite", {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
                //alert("Ваш url добавлен");
                console.log(allData);
                self.sites.push(new Site(allData.id,allData.title,allData.url));
                self.title("");
                self.url("");
            },
            error: function (allData) {
                //alert(allData.responseText);
                alert("Упс, что-то пошло не так, попбуйти еще раз");
                console.log('error! Не могу отправить json запрос');
                console.log(allData);
            }
        });
        $('#createSite').modal("hide");
    };

    self.reload = function () {
        self.sites.removeAll();
        self.load();
    };
    
    self.updateSite = function () {
        thisSite.url(self.siteUrl());
        thisSite.title(self.siteTitle());
        var jsonData = ko.toJSON(thisSite);
        //console.log(jsonData);
        // make POST request
        $.ajax("/api/updateSite", {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
               // alert("Сохранено");
                for(var i=0;i<self.sites().length;i++){
                    if (self.sites()[i].id == thisSite.id){
                        self.sites()[i].title(thisSite.title());
                        self.sites()[i].url(thisSite.url());
                    }
                }
            },
            error: function (allData) {
                //alert(allData.responseText);
                alert("Упс, что-то пошло не так, попбуйти еще раз");
                console.log('error! Не могу отправить json запрос');
                console.log(allData);
            }
        });

    };

    self.reload();
}


// Activates knockout.js
//создаем экземпляр ViewModel
// var sitesViewModel = new SitesViewModel();
// ko.applyBindings(sitesViewModel);