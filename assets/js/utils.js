var Util = {
    // 元素添加事件监听器
    addHandler: function(element, type, handler, p) {
        if (element.addEventListener) {
            element.addEventListener(type, handler, p);
        } else if (element.attachEvent) {
            element.attachEvent("on" + type, handler);
        } else {
            element["on" + type] = handler;
        }
    },
    // 元素增加类
    addClass: function(className, element) {
        if (element.setAttribute) {
            element.setAttribute(
                "class",
                element.getAttribute("class") + " " + className
            );
        } else {
            element.className = element.className + " " + className;
        }
    },
    // 插入CSS
    addStyle: function(className, beforeStyle, _style, afterStyle) {
        let style = document.createElement("style");
        document.head.appendChild(style);
        let sheet = style.sheet;
        if (sheet.addRule) {
            sheet.addRule("." + className, _style);
            sheet.addRule("." + className + ":before", beforeStyle);
            sheet.addRule("." + className + ":after", afterStyle);
        } else {
            sheet.insertRule("." + className + "{" + _style + "}", 0);
            sheet.insertRule(
                "." + className + ":before{" + beforeStyle + "}",
                0
            );
            sheet.insertRule("." + className + ":after{" + afterStyle + "}", 0);
        }
    },
    /**封装 ajax 函数
     * @param {String} obj.method http连接方式，POST || GET
     * @param {String} obj.url 发送请求的url
     * @param {Boolean} obj.async 是否为异步请求，true异步 || false同步
     * @param {Object} obj.data 发送的参数，对象类型
     * @param {Function} obj.success ajax发送并成功接受调用的回调函数
     * @author XIE Hui
     */
    ajax: function(obj) {
        obj.method = obj.method.toUpperCase();
        var request = null;
        if (XMLHttpRequest) {
            request = new XMLHttpRequest();
        } else {
            request = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var params = [];
        for (var key in obj.data) {
            params.push(key + "=" + obj.data[key]);
        }
        var send_data = params.join("&");
        if (obj.method === "POST") {
            request.open(obj.method, obj.url, obj.async);
            request.setRequestHeader(
                "Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8"
            );
            request.send(send_data);
        } else if (obj.method === "GET") {
            request.open(obj.method, obj.url + "?" + send_data, obj.async);
            request.send();
        }
        request.onreadystatechange = function() {
            if (request.readyState === 4 && request.status === 200) {
                obj.success(request.responseText);
            }
        };
    }
};
