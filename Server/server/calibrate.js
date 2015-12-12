var calibrateClock = function(iButtonClicked, socket){
	var incoming_ = new Date().getTime();
	var currentSocketId = socket.id.toString();
	var outgoing_ = new Date().getTime();

	io.to(currentSocketId).emit("server_timestamps", {incoming : incoming_, outgoing : outgoing_});
	//all the calculations are done client side
}