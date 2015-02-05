var frogorf = {
    action: null,
    grid: "#grid",
    host: "http://" + window.location.host + "/grabber"
};

function getFormParam(str_replace) {
    var form_params = $("#form" + frogorf.action).serialize().replace(/&/g, ":");
    if (str_replace)
        form_params = form_params.replace(new RegExp(str_replace, 'g'), "");
    return form_params;
}

function gridReload() {
    $("#grid" + frogorf.action).jqGrid('setGridParam', { url: "/admin/" + frogorf.action.toLocaleLowerCase() + "?filters=" + getFormParam("#search" + frogorf.action, ""), page: 1 }).trigger("reloadGrid");
}

function getGridSelected() {
    var res = $("#grid" + frogorf.action).jqGrid('getGridParam', 'selrow');
    if (res == null) {
        alert("Пожалуйста, выберите строку");
        return false;
    }
    return res;
}

function gridEdit() {
    var id = getGridSelected();
    if ((id == null) || (id == false) || (id === "false")) return false;
    window.location = "/admin/" + frogorf.action.toLocaleLowerCase() + "/" + id + "/edit";
    return false;
}

function gridDetail() {
    var id = getGridSelected();
    if ((id == null) || (id == false)) return false;
    window.location = "/admin/" + frogorf.action.toLocaleLowerCase() + "/" + id;
    return false;
}

function gridDelete(name) {
    var id = getGridSelected();
    log(id);
    if ((id == null) || (id == false)) return;
    var delete_url = "/admin/" + frogorf.action.toLocaleLowerCase() + "/" + id + "/delete";
    log(delete_url);
    $("#DialogWindow").dialog({
        title: "Удалить позицию?",
        buttons: {
            "Да": function () {
                $.ajax({
                    type: "GET",
                    success: function () {
                        gridReload();
                        AjaxSuccess('');
                        $(this).dialog("close");
                    },
                    url: delete_url
                });
                $(this).dialog("close");
            },
            "Нет": function () {
                $(this).dialog("close");
            }
        }
    });
    $("#DialogWindow").dialog("open");
    return false;
}

function Save() {
    $('#form' + frogorf.action).submit();
}
function getGridHeight() {
    return $(window).height() - $(".segment").height() - $(".transparent").height() - $(".footer").height() - 185;
}
function log(message) {
    $("<div>").text(message).prependTo("#log");
    $("#log").scrollTop(0);
}
function resizeGrid() {
    var gridElement = $(frogorf.grid);
    var dataArea = gridElement.find(".k-grid-content");
    var newGridHeight = $(document).height() - 150 - $(".searchable").height() - $(".actions").height();
    var newDataAreaHeight = newGridHeight - 65;
    dataArea.height(newDataAreaHeight);
    gridElement.height(newGridHeight);
    $(frogorf.grid).data("kendoGrid").refresh();
}