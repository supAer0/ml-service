function Element(id, name,key) {
    var self = this;
    self.id=id;
    self.name=name;
    self.key=key;
}
// The Instance view model
function ElementViewModel() {
    var self = this;
    self.elements = ko.observableArray([]);

    self.id = ko.observable(null);
    // self.el_name = ko.observable("");
    // self.el_key = ko.observable("");

    var ident = document.location.pathname.match(/\d+/);
    self.load = function () {
        $.ajax("/api/elements?id="+ident, {
            dataType: 'json',
            success: function (data) {
                console.log("Успешно обработан json запрос. Записи загружены");
                console.log("data= " + data);
                for (var i = 0; i < data.length; i++) {
                    self.elements.push(new Element(data[i].id, data[i].name,data[i].key));
                }
                console.log("массив= " +self.elements());
            },
            error: function (data) {
                alert("error! " + data.error);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.saveElements = function () {

        var $rows = $TABLE.find('tr:not(:hidden)');
        var headers = [];
        var data = [];
        // Get the headers (add special header logic here)
        $($rows.shift()).find('th:not(:empty)').each(function () {
            headers.push($(this).text().toLowerCase());
        });

        // Turn all existing rows into a loopable array
        $rows.each(function () {
            var $td = $(this).find('td');
            var h = {};

            // Use the headers from earlier to name our hash keys
            headers.forEach(function (header, i) {
                h[header] = $td.eq(i).text();
            });
            var el = new Element();
            el.id = h[headers[0]];
            el.name = h[headers[1]];
            el.key = h[headers[2]];
            data.push(el);
        });
        // check for dist key
        for(var i = 0; i<data.length;i++){
            for (var j = 1; j <data.length;j++){
                if(i===j)
                    break;
                if (data[i].key == data[j].key){
                    alert("Возможно указан повторябщийся ключ  строки["+i+", " + j+"]");
                    return;
                }
            }
        }


        console.log("data = " + data);
        var jsonData = ko.toJSON(data);
        console.log("JSON= " + jsonData);
        // make POST request
        $.ajax("/api/saveElements?id="+ident, {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
                console.log(allData);
                //var deleteRows = $rows.slice(1, $rows.length);
               // console.log("DELETE ROWS" + deleteRows);
                $rows.remove();
                self.reload();
                //self.elements.push(new Element(allData.id,allData.name,allData.key));
            },
            error: function (allData) {
                //alert(allData.responseText);
                alert("Упс, что-то пошло не так, попбуйти еще раз");
                console.log('error! Не могу отправить json запрос');
                console.log(allData);
            }
        });
    };


    self.removeElement = function (element) {
        var jsonData = ko.toJSON(element);
        console.log("(DELETE)JSON= " + jsonData);
        // make POST request
        $.ajax("/api/deleteElement?id="+ident, {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
                console.log(allData);
                self.elements.remove(element);
                //self.elements.push(new Element(allData.id,allData.name,allData.key));
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
        self.elements.removeAll();
        self.load();
    };


    self.reload();
}



// Activates knockout.js
//создаем экземпляр ViewModel
// var instanceViewModel = new InstanceViewModel();
// ko.applyBindings(instanceViewModel, document.getElementById("instanceViewModel"));

