$(document).ready(function() {
    $('input[name="locale"]').change(function () {
        var input = $('input[name="locale"]:checked');
        window.location.replace('?locale=' + input.val());
    });
});