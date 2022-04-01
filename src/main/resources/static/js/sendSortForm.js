function sendSortParameters(sortFormId = "sortForm", sortFieldId = "sortField", sortDirectionId = "sortDirection"){
    let form = document.getElementById("sortForm");
    let input = document.getElementById("sortField");
    let direction = document.getElementById("sortDirection");
    input.options[input.selectedIndex].value += ",".concat(direction.value);
    direction.removeAttribute("name");
    form.submit();
}