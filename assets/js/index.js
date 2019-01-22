var handlers = [
    {
        element: document.getElementById("SearchBar-btn"),
        type: "click",
        handler: function() {
            var searchText = document.getElementById("SearchBar-text").value;
            console.log(searchText);
            Util.ajax({
                method: "POST",
                url: env + "/search",
                async: true,
                data: { keyword: searchText },
                success: function(response) {
                    if (response.result.ack === "success") {
                        if (response.result.blockList.length > 0) {
                        } else if (response.result.transactionList.length > 0) {
                        }
                    } else {
                        console.log(response.result.errorMessage);
                    }
                }
            });
        },
        p: true
    },
    {
        element: document.getElementById("SearchCard-btn"),
        type: "click",
        handler: function() {
            var searchText = document.getElementById("SearchCard-text").value;
            console.log(searchText);
            Util.ajax({
                method: "POST",
                url: env + "/search",
                async: true,
                data: { keyword: searchText },
                success: function(response) {
                    if (response.result.ack === "success") {
                        if (response.result.blockList.length > 0) {
                        } else if (response.result.transactionList.length > 0) {
                        }
                    } else {
                        console.log(response.result.errorMessage);
                    }
                }
            });
        },
        p: true
    }
];

for (h of handlers) {
    Util.addHandler(h.element, h.type, h.handler, h.p);
}
