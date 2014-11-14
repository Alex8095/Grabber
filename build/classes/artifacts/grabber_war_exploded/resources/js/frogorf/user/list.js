$(function () {
    $("#gridUser").jqGrid({
        url: '/resources/json.txt',
        hidegrid: false,
        datatype: "json",
        autowidth: true,
        height: getGridHeight(),
        colNames: ['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
        colModel: [
            {name: 'id', index: 'id', width: 55},
            {name: 'invdate', index: 'invdate', width: 90},
            {name: 'name', index: 'name asc, invdate', width: 100},
            {name: 'amount', index: 'amount', width: 80, align: "right"},
            {name: 'tax', index: 'tax', width: 80, align: "right"},
            {name: 'total', index: 'total', width: 80, align: "right"},
            {name: 'note', index: 'note', width: 150, sortable: false}
        ],
        rowNum: 30,
        rowList: [30, 60, 80],
        //jsonReader: { repeatitems: false, id: "id" },
        pager: '#gridUserPager',
        sortname: 'id',
        viewrecords: true,
        sortorder: "asc",
        onSortCol: function (index, columnIndex, sortOrder) {
            index = "id";
            columnIndex = "id";
        },
        onSelectRow: function (rowid) {
            var cont = $("#grid" + frogorf.action).getCell(rowid, 'id');
            //alert(cont);
        },
        ondblClickRow: function () {
            gridDetail();
        }
    });
    $("#grid").jqGrid('setGridWidth', $("#gview_grid").parent("div").width() - 25, true);
    $(".EditDict").click(function () {
        gridEdit();
        return false;
    });
    $(".DeleteDict").click(function () {
        gridDelete();
        return false;
    });
});

