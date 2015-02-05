$(document).ready(function () {
    $(".actions .searching").click(function () {
        if ($(".searchable .grid").is(":visible")) {
            $(".searchable .grid").slideUp("slow", function () {
                resizeGrid();
            });
        }
        else {
            $(".searchable .grid:hidden").slideDown("slow", function () {
                resizeGrid();
            });
        }
    });
});
