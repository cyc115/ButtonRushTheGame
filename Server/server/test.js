"use strict";
var io = require("socket.io-client");
var url = "http://farazoman.com:3000";
var socket = io(url);
var data = {
    "button_clicked": 0,
    "score": 100
};
socket.emit("button_clicked", data);
var emit = function (k, savedTime) {
    if (k > 0) {
        socket.emit("get_ts", {});
        emit(k - 1, savedTime);
    }
    else {
        console.log(url + " : " + (new Date().getTime() - savedTime));
        process.exit();
    }
};
var t_conn = new Date().getTime();
socket.on('connect', function () {
    var currentTime = new Date().getTime();
    console.log("connection established : " + (currentTime - t_conn));
    socket.on("server_timestamp", function (data) {
        var t2 = new Date().getTime();
    });
    emit(100, currentTime);
});
