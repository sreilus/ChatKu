var app = require('express')();
var http = require('http').Server(app);
var io= require('socket.io')(http);

app.get('/',function(req,res){
    res.sendFile('index.html',{root:__dirname});
})

io.on('connect',function(socket){
    console.log("Ada user yang konek : "+socket.id);

    socket.on('pesan',function(data){
        //var sockets=io.sockets.sockets;
        socket.broadcast.emit('pesan',data);
    })

    socket.on('disconnect',function(){
        console.log("User :"+socket.id+" has left");
    })
})

http.listen(3000,function(){
    console.log("Server dengan port 3000 sudah jalan");
})

