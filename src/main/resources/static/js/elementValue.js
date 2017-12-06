
function El(id, name,key,value) {
    var self = this;
    self.id=id;
    self.name=name;
    self.key=key;
    self.value = value;
}


// The Instance view model
function ElementValueViewModel() {
    var self = this;
    self.elements = ko.observableArray([]);
    self.id = ko.observable(null);
    // self.token = ko.observable("");

    var ident = document.location.pathname.match(/\d+/g)[1];
    self.load = function () {
        $.ajax("/api/elementsValue?id_instance="+ident, {
            dataType: 'json',
            success: function (data) {
                console.log("Успешно обработан json запрос. Записи загружены");
                console.log("data= " + data);
                for (var i = 0; i < data.length; i++) {
                    self.elements.push(new El(data[i].id, data[i].name,data[i].key,data[i].value));
                }
                console.log("массив= " +self.elements());
            },
            error: function (data) {
                alert("error! " + data.error);
                console.log('error! Не могу отправить json запрос');
                console.log(data);
            }
        });
        // $.ajax("/api/getToken?id_instance="+ident, {
        //     success: function (data) {
        //         console.log("token= " + data);
        //         self.token(OPENAPI_PATH + data);
        //     },
        //     error: function (data) {
        //         alert("error! " + data.error);
        //         console.log('error! Не могу отправить json запрос');
        //         console.log(data);
        //     }
        // });
    };

    self.save = function () {

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
            var el = new El();
            el.id = h[headers[0]];
            el.name = h[headers[1]];
            el.key = h[headers[2]];
            el.value = h[headers[3]];
            data.push(el);
        });
        console.log("data = " + data);
        var jsonData = ko.toJSON(data);
        console.log("JSON= " + jsonData);
        // make POST request
        $.ajax("/api/saveElementsWithValue?id_instance="+ident, {
            data: jsonData,
            type: "post",
            contentType: 'application/json; charset=utf-8',
            success: function (allData) {
                console.log(allData);
                //self.reload();
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

