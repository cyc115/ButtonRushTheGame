var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res){
  res.sendFile('/Users/Gc/Desktop/ButtonRushTheGame/Server/server/index.html');
});

http.listen(3000, function(){
  console.log('listening on *:3000');
});
//ps i copied that stuff above from other file

//our implementation of the time sync, server side
var calibrateClock_own = function(iButtonClicked, socket){
	var incoming_ = new Date().getTime();
	var currentSocketId = socket.id.toString();
	var outgoing_ = new Date().getTime();

	io.to(currentSocketId).emit("server_timestamps", {incoming : incoming_, outgoing : outgoing_});
	//all the calculations are done client side
}

var threshold = 5; //in miliseconds
//A mock function for how the client does the calculations, our own version
var computeOffset_own = function(){
	offset = 0;

	//this while loop will get a good average of the latency by repeating until a threshold is met
	do{

		var outgoing_c = new Date().getTime() + offset;
		//call calibrate clock remotely and get variables
		var incoming_c = new Date().getTime() - offset;
		//do this calculation: calc = ((incoming_s - outgoing_c) + (outgoing_s - incoming_c)) / 2
		// more calcs : offset = offset + calc;

	}while(Math.abs(offset) > threshold);

	return offset;

}

//server side code of cristian algo
var calibrateClock_cristian = function(iButtonClicked, socket){
	var currentSocketId = socket.id.toString();
	var outgoing_ = new Date().getTime();

	io.to(currentSocketId).emit("server_timestamps", {incoming : incoming_, outgoing : outgoing_});
	//all the calculations are done client side
}

//client side calculations of cristian method
var computeOffset_cristian = function(){
	var outgoing_c = new Date().getTime();
	//TODO request time from server
	var incoming_s; //this is where we get the time from the server
	var incoming_c = new Date().getTime();
	var RTT = incoming_s + (outgoing_c + incoming_c);
	var offset = new Date().getTime() - incoming_s + RTT / 2;

	return offset;
}

//this one is more complicated and is done server side except for the time resquests
var computeOffset_berkeley = function(){
	var outgoing_s = new Date().getTime();
	//TODO request time from clients
	var incoming_c1; //ask client for the time
	var incoming_c2; //ask the clinet for their time 
	var incoming_s = new Date().getTime();

	//calculate the average
	var average = (outgoing_s + incoming_s + incoming_c2 + incoming_c1) / 4;
	
	var offset_c1 = incoming_c1 - average;
	var offset_c2 = incoming_c2 - average;	
	var offset_s  = (outgoing_s + incoming_s) / 2 - average;

	//send offsets to respective clients
		//io.to(**the socketid of C1**).emit("offset", {incoming : incoming_, offset : offset_c1});
		//io.to(**Socket ID of C2**).emit("offset", {incoming : incoming_, offset : offset_c2});
	return offset;
}