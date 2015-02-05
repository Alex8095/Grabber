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
                    url: frogorf.host + "/admin/grabber/history/grid"
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
            { field: "task.url", title: "Task", "width": "200px" },
            { field: "startDate", title: "Start", template: function (dataItem) {
                return kendo.toString(kendo.parseDate("/Date(" + dataItem.startDate + ")/"), "yyyy-MM-dd HH:mm");
            }
            },
            { field: "endDate", title: "End", template: function (dataItem) {
                return kendo.toString(kendo.parseDate("/Date(" + dataItem.startDate + ")/"), "yyyy-MM-dd HH:mm");
            }
            },
            { field: "state",  title: "State",
                template: function(dataItem) {
                    return dataItem.state == null ? "" : dataItem.state.name ;
                }
            },
            { field: "countFound", title: "Found" },
            { field: "countNew", title: "New" },
            { field: "countUpdate", title: "Update" },
            { field: "countDuplicated", title: "Duplicated" }
        ]
    })
    ;

    $(window).resize(function () {
        resizeGrid();
    });
    $("#datePickerStart").kendoDatePicker();
})
;
function rowSelect(e) {
    var selectedrow = $("#grid").find("tbody tr.k-state-selected");
    var list = $('#grid').data("kendoGrid").dataItem(selectedrow);
    var listjson = list.toJSON();
    var ID = listjson.id;
    window.location = document.URL + ID;
}