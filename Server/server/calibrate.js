var calibrateClock = function(iButtonClicked, socket){
	var incoming_ = new Date().getTime();
	var currentSocketId = socket.id.toString();
	var outgoing_ = new Date().getTime();

	io.to(currentSocketId).emit("server_timestamps", {incoming : incoming_, outgoing : outgoing_});
	//all the calculations are done client side
}

var threshold = 5; //in miliseconds
//A mock function for how the client does the calculations
var computeOffset = function(){
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