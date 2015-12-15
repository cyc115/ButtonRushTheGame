var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/buttonRush', function(req, res){
  res.sendFile( __dirname + '/index.html');
});
var port = 3030;

http.listen(port, function(){
  console.log('listening on port' + port);
});

// Constants
const LEFTBUTTON = 0;
const CENTERBUTTON = 1;
const RIGTHBUTTON = 2;

// Variables to keep track three buttons
var bLeftButtonFree = true;
var bCenterButtonFree = true;
var bRightButtonFree = true;

// Variable to keep track number of players connected
var iNumPlayerConnect = 0;

// Variables related to players
var player1 = null;
var player2 = null;

// check the availability of the buttons
var checkButtons = function(iButtonClicked, socket, iScore) {
  var currentSocketId = socket.id.toString();
  var bValidClick = false;
  if (iButtonClicked == LEFTBUTTON && bLeftButtonFree) {
    bLeftButtonFree = false;
    bValidClick = true;
  }
  else if (iButtonClicked == CENTERBUTTON && bCenterButtonFree) {
    bCenterButtonFree = false;
    bValidClick = true
  }
  else if (iButtonClicked == RIGTHBUTTON && bRightButtonFree) {
    bRightButtonFree = false;
    bValidClick = true
  }

  if (bValidClick) {
    // if the click is valid, inform the opponent about the new score
    socket.emit("score_update", {score : iScore, button_update : iButtonClicked});
    console.log("valid click");
  }
  else {
    console.log("invalid click");
    // inform the current player that they lost.
    io.to(currentSocketId).emit("game_result", {result : "lost"});

    // inform the opponent that they win
    if (currentSocketId == player1) {
      io.to(player2).emit("game_result", {result : "win"});
      console.log("player1: " + currentSocketId + " lost.");
      console.log("player2: " + player2 + " win.");
    }
    else if (currentSocketId == player2) {
      io.to(player1).emit("game_result", {result : "win"});
      console.log("player2: " + currentSocketId + " lost.")
      console.log("player1: " + player1 + " win.");
    }
  }
}

io.sockets.on('connection', function (socket) {

  var socketid = socket.id.toString();
  if (player1 == null) {
    player1 = socketid;
  }
  else if (player2 == null) {
    player2 = socketid;
  }

  console.log('client: ' + socket.id.toString() + ' joined.');
  iNumPlayerConnect++;

  // As soon as there are 2 players joined, emit an event that
  // notify the clients to start the game
  if (iNumPlayerConnect == 2) {
    console.log('game_start');
    io.emit('game_start', {});
  }

  // listening on button_click event
  socket.on('button_click', function(data) {
    var iButtonClicked = data.button_clicked;
    var iScore = data.score
    checkButtons(iButtonClicked, socket, iScore);
  });

  //send server's time stamp to the client
  socket.on('get_ts', function(data){
    io.emit('server_timestamp', {
      time : new Date().getTime()
    })
  });

  socket.on('disconnect', function(socket){
//    console.log('client: ' + socket.id.toString() + ' left.');
    iNumPlayerConnect--;
  });

});
