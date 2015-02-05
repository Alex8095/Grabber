jQuery(function () {
    jQuery("#grid").kendoGrid({
        "dataSource": {
            "schema": {
                "total": "total",
                "data": "data"
            },
            "transport": {
                "parameterMap": function parameterMap(options) {
                    return JSON.stringify(options);
                },
                "read": {
                    "contentType": "application/json",
                    "type": "POST",
                    "url": frogorf.host + "/admin/dictionary/grid"
                }
            },
            "pageSize": 20.0
        },
        change: rowSelect,
        selectable: true,
        height: $(document).height() - 200,
        sortable: true,
        pageable: true,
        "columns": [
            {"field": "id", "title": "ID", "width": "100px"},
            { field: "name", title: "Name" },
            { field: "code", title: "Code" }
        ]});

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
function categoriesChange() {
    var value = this.value(),
        grid = $("#grid").data("kendoGrid");
    if (value) {
        grid.dataSource.filter({ field: "categoryId", operator: "eq", value: parseInt(value) });
    } else {
        grid.dataSource.filter({});
    }
}