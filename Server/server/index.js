var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res){
  res.sendFile('/Users/Gc/Desktop/ButtonRushTheGame/Server/server/index.html');
});

http.listen(3000, function(){
  console.log('listening on *:3000');
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
    socket.emit("score_update", {score : iScore});
    console.log("valid click");
  }
  else {
    console.log("invalid click");

    io.to(currentSocketId).emit("game_result", {result : "lost"});
    if (currentSocketId == player1) {
      io.to(player2).emit("game_result", {result : "win"});
      console.log(currentSocketId + " lost.")
    }
    else if (currentSocketId == player2) {
      io.to(player1).emit("game_result", {result : "win"});
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
  // notify the client to start the game
  if (iNumPlayerConnect == 2) {
    console.log('game_start');
    io.emit('game_start', {});
  }

  socket.on('button_click', function(data) {
    var iButtonClicked = data.button_clicked;
    var iScore = data.score
    checkButtons(iButtonClicked, socket, iScore);
  });

  socket.on('disconnect', function(socket){
    console.log('client: ' + socket.id.toString() + ' left.');
    iNumPlayerConnect--;
  });
});




