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
                    url: frogorf.host + "/Grabber/admin/realty/grid"
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
            {"field": "id", "title": "ID", "width": "30px"},
            { field: "site_url", title: "Url", "width": "200px" },
            { field: "ownerType.name", title: "Owner" },
            { field: "implementationType.name", title: "Implementation" },
            {"field": "isRent", "title": "Rend", "width": "50px",
                template: function (dataItem) {
                    return dataItem.isRent ? '<i class="checkmark icon"></i>' : "";
                }
            },
            {"field": "isSale", "title": "Sale", "width": "50px",
                template: function (dataItem) {
                    return dataItem.isSale ? '<i class="checkmark icon"></i>' : "";
                }
            },
            {"field": "isChange", "title": "Change", "width": "80px",
                template: function (dataItem) {
                    return dataItem.isChange ? '<i class="checkmark icon"></i>' : "";
                }
            },
            { field: "", title: "Country" },
            { field: "", title: "Region" },
            { field: "", title: "District" },
            { field: "", title: "City" },
            { field: "", title: "Area city" },
            { field: "", title: "District city" },
            { field: "", title: "Street" }
        ]});

    $("#ownerType").kendoDropDownList({
        dataTextField: "name",
        dataValueField: "id",
        autoBind: false,
        optionLabel: "All",
        dataSource: {
            transport: {
                read: frogorf.host + "/Grabber/admin/dictionary/value/json?code=OWNER"
            }
        },
        change: ownerChange
    });

    $("#implementationType").kendoDropDownList({
        dataTextField: "name",
        dataValueField: "id",
        autoBind: false,
        optionLabel: "All",
        dataSource: {
            transport: {
                read: frogorf.host + "/Grabber/admin/dictionary/value/json?code=IMPLEMENTATION"
            }
        },
        change: implementationChange
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
function ownerChange() {
    var value = this.value(),
        grid = $("#grid").data("kendoGrid");
    if (value) {
        grid.dataSource.filter({ field: "ownerType.id", operator: "eq", value: parseInt(value) });
    } else {
        grid.dataSource.filter({});
    }
}
function implementationChange() {
    var value = this.value(),
        grid = $("#grid").data("kendoGrid");
    if (value) {
        grid.dataSource.filter({ field: "implementationType.id", operator: "eq", value: parseInt(value) });
    } else {
        grid.dataSource.filter({});
    }
}