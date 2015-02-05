jQuery(function () {
    jQuery("#grid").kendoGrid({
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "shipCity": {"type": "string"},
                        "orderDate": {"type": "date"},
                        "freight": {"type": "number"},
                        "shipName": {"type": "string"},
                        "orderId": {"type": "number"}
                    }
                },
                "data": "data",
                "groups": "data"
            },
            "serverFiltering": true,
            "transport": {
                "parameterMap": function (options) {
                    return JSON.stringify(options);
                },
                "read": {
                    "contentType": "application/json",
                    "type": "POST",
                    "url": "/spring-demos/grid/remote-data-binding/read"
                }
            },
            "serverSorting": true,
            "pageSize": 20.0,
            "serverPaging": true,
            "serverGrouping": true
        },
        "height": "430px",
        "filterable": true,
        "sortable": true,
        "pageable": true,
        "columns": [
            {"field": "orderId", "title": "Order ID", "filterable": false, "width": "100px"},
            {"field": "freight", "title": "Freight", "width": "100px"},
            {"field": "orderDate", "title": "Order Date", "width": "140px", "format": "{0:MM/dd/yyyy}"},
            {"field": "shipName", "title": "Ship Name"},
            {"field": "shipCity", "title": "Ship City", "width": "150px"}
        ],
        "scrollable": {},
        "groupable": true});
})