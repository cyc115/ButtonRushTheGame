"use strict";
var io = require("socket.io-client");
var socket = io("http://farazoman.com:3000");
var data = {
    "button_clicked": 0,
    "score": 100
};
socket.emit("button_clicked", data);
var currentTime = new Date().getTime();
socket.on('connect', function () {
    console.log("connected!");
    socket.on("server_timestamp", function (data) {
        var t2 = new Date().getTime();
        console.log("RTT is " + (t2 - currentTime));
        console.log("data " + data["time"]);
    });
    socket.emit("get_ts", {});
});
console.log("Done");
