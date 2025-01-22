const express = require('express');
const app = express();
var cors = require('cors')
const { Server } = require("socket.io");
const http = require('http');


const httpServer = new http.Server(app);
const io = new Server(httpServer, { cors: { origin: '*' } });

app.use(cors());

io.on('connection', (client) => {
  // io.emit('users-online', User.getUserList());
  console.log("connected")
  io.emit("review-created", {
    name: "i'm a review"
  });
  // socket.disconnectClient(client, io);
  // socket.addUserOnline(client, io);
  // socket.removeUserOnline(client, io);
});

httpServer.listen(8089, () => {
  console.log('listening on localhost:8089');

});