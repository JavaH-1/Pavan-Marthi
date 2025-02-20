function sendLibraryData() {
    var bookId = document.getElementById("bookId").value;
    var bookTitle = document.getElementById("bookTitle").value;
    var author = document.getElementById("author").value;
    var genre = document.getElementById("genre").value;
    var quantity = document.getElementById("quantity").value;
    var price = document.getElementById("price").value;

    var xmlhttp = new XMLHttpRequest();
    var url = "/LibraryDataServlet";

    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById("response").innerHTML = xmlhttp.responseText;
        }
    };

    var data =
        "bookId=" + encodeURIComponent(bookId) +
        "&bookTitle=" + encodeURIComponent(bookTitle) +
        "&author=" + encodeURIComponent(author) +
        "&genre=" + encodeURIComponent(genre) +
        "&quantity=" + encodeURIComponent(quantity) +
        "&price=" + encodeURIComponent(price);

    xmlhttp.send(data);
}

function getBookById() {
    var searchId = document.getElementById("searchBookId").value;

    var xmlhttp = new XMLHttpRequest();
    var url = "/LibraryDataServlet";

    xmlhttp.open("GET", url + "?searchBookId=" + encodeURIComponent(searchId), true);

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById("searchResponse").innerHTML = xmlhttp.responseText;
        }
    };

    xmlhttp.send();
}
