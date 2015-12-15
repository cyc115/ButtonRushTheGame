/**
* this is the tester script that
**/
//http://localhost:3000/buttonRush

import io = require("socket.io-client");

let socket = io("http://farazoman.com:3000");

let data = {
  "button_clicked" : 0 ,
  "score" : 100
};

socket.emit("button_clicked" , data);

let currentTime = new Date().getTime();

socket.on('connect', function(){
  console.log("connected!");

  //when client received response from server
  //calculate RTT
  socket.on("server_timestamp", function (data) {
    let t2 = new Date().getTime();
    console.log("RTT is " + (t2 - currentTime) );
    console.log("data " + data["time"]);
  });


  socket.emit("get_ts",{});

});























console.log("Done");
