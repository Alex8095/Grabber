$(function () {
    $("#grid").kendoGrid({
        dataSource: {
            schema: {
                total: "total",
                data: "data"
            },
            transport: {
                parameterMap: function parameterMap(options) {
                    return JSON.stringify(options);
                },
                read: {
                    contentType: "application/json",
                    type: "POST",
                    url: frogorf.host + "/admin/dictionary/value/grid"
                }
            },
            pageSize: 20.0
        },
        change: rowSelect,
        height: $(document).height() - 200,
        selectable: true,
        sortable: true,
        pageable: true,
        columns: [
            {"field": "id", "title": "ID", "width": "100px"},
            { field: "name", title: "Name" },
            { field: "code", title: "Code" },
            { field: "dictionary.name", title: "Dictionary" },
            { field: "parentDictionaryValue",  title: "Parent",
                template: function(dataItem) {
                    return dataItem.parentDictionaryValue == null ? "" : dataItem.parentDictionaryValue.name ;
                }
            }
        ]});

    $("#dictionary").kendoDropDownList({
        dataTextField: "name",
        dataValueField: "id",
        autoBind: false,
        optionLabel: "All",
        dataSource: {
            transport: {
                read: frogorf.host + "/admin/dictionary/list/json"
            }
        },
        change: dictionaryChange
    });

    $(window).resize(function () {
        resizeGrid();
    });
});


function rowSelect(e) {
    var selectedrow = $("#grid").find("tbody tr.k-state-selected");
    var list = $('#grid').data("kendoGrid").dataItem(selectedrow);
    var listjson = list.toJSON();
    var ID = listjson.id;
    window.location = document.URL + ID;
}
function dictionaryChange() {
    var value = this.value(),
        grid = $("#grid").data("kendoGrid");
    if (value) {
        grid.dataSource.filter({ field: "dictionary.id", operator: "eq", value: parseInt(value) });
    } else {
        grid.dataSource.filter({});
    }
}