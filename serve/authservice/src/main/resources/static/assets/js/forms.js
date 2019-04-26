function keyChange(e) {
    var key = this.value;
    var input = this.parentElement.nextElementSibling.querySelector("input");
    var name = input.getAttribute("name");

    if(name.includes("[")) {
        var field = name.split("[")[0];
        input.setAttribute("id", field + key);
        input.setAttribute("name", field + "[" + key + "]");
    } else {
        input.setAttribute("name", key);
    }
}

function removeInput(e) {
     e.preventDefault();
     var el = this.parentElement;
     if (el.tagName == "TD") {
        el = el.parentElement;
     }
     el.remove();
}

function addInput(e) {
    e.preventDefault();
    var field = this.getAttribute("data-field");
    var container = this.closest(".form-field");
    var table = container.querySelector("tbody");
    if(table != null) {
        var icon = document.createElement("a");
        icon.setAttribute("href", "#");
        icon.setAttribute("class", "icon danger remove");
        icon.innerHTML = "&#8854;";

        icon.addEventListener("click", removeInput, true);

        var value = document.createElement("input");
        value.setAttribute("class", "value");
        value.setAttribute("type", "text");
        value.setAttribute("name", field);
        value.setAttribute("placeholder", "_");

        var key = document.createElement("input");
        key.setAttribute("class", "key");
        key.setAttribute("type", "text");
        key.setAttribute("placeholder", "_");
        key.setAttribute("required","");
        key.addEventListener("change", keyChange, true);

        var td1 = document.createElement("td");
        td1.appendChild(key);

        var td2 = document.createElement("td");
        td2.setAttribute("class", "rel");
        td2.appendChild(value);
        td2.appendChild(icon);

        var tr = document.createElement("tr");
        tr.appendChild(td1);
        tr.appendChild(td2);

        table.appendChild(tr);
    } else {
        var index = container.querySelectorAll("input:not([type='hidden'])").length;
        var id = field + index;
        var name = field + "[" + index + "]";
        if(field.includes("#")){
            id = field.replace("#", index);
            name = field.replace("#", "[" + index + "]");
        }

        var icon = document.createElement("a");
        icon.setAttribute("href", "#");
        icon.setAttribute("class", "icon danger remove");
        icon.innerHTML = "&#8854;";

        icon.addEventListener("click", removeInput, true);

        var input = document.createElement("input");
        input.setAttribute("type", "text");
        input.setAttribute("id", id);
        input.setAttribute("name", name);
        input.setAttribute("placeholder", "_");

        var div = document.createElement("div");
        div.setAttribute("class", "rel");
        div.appendChild(input);
        div.appendChild(icon);

        container.appendChild(div);
    }
}

for(var node of document.querySelectorAll("a.icon[data-field]")) {
    node.addEventListener("click", addInput, true);
}

for(var node of document.querySelectorAll("input.key")) {
    node.addEventListener("change", keyChange, true);
}

for(var node of document.querySelectorAll("a.icon.remove")) {
    node.addEventListener("click", removeInput, true);
}