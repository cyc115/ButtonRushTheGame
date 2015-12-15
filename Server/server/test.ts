/**
* this is the tester script that
**/
//http://localhost:3000/buttonRush

import io = require("socket.io-client");

//   let url = "http://localhost:3030";
let url = "http://farazoman.com:3000";
let socket = io(url);

let data = {
  "button_clicked" : 0 ,
  "score" : 100
};

socket.emit("button_clicked" , data);

let emit = (k : number , savedTime : number ) => {
  if (k > 0 ){
    socket.emit("get_ts", {});
    emit(k-1, savedTime);
  }

  else {
    //print and terminate
    console.log(url + " : " + (new Date().getTime() - savedTime) );
    process.exit();
  }
}

let t_conn = new Date().getTime();

socket.on('connect', function(){

  let currentTime = new Date().getTime();
  console.log("connection established : " + (currentTime - t_conn));

  //when client received response from server
  //calculate RTT
  socket.on("server_timestamp", function (data) {
    let t2 = new Date().getTime();
    //console.log("RTT is " + (t2 - currentTime) );
    //console.log("data " + data["time"]);
    //currentTime = new Date().getTime();
  });

  emit(100, currentTime);

});
