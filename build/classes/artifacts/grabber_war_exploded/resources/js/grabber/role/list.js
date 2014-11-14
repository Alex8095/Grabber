$(document).ready(function () {
    var grid = $("#grid").kendoGrid({
        dataSource: {
            type: "odata",
            transport: {
                read: "http://localhost:8080/grabber/admin/role/grid"
            },
            pageSize: 20,
            serverPaging: true,
            serverSorting: true,
            serverFiltering: true
        },
        height: 550,
        sortable: true,
        pageable: true,
        columns: [
            { field: "id", title: "ID", width: 100 },
            { field: "role", title: "Role" }
        ]
    });
});