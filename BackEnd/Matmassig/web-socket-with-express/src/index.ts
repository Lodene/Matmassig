import express, { Express, Request, Response } from "express";

const app: Express = express();

var cors = require("cors");
const { Server } = require("socket.io");
const http = require("http");

const httpServer = new http.Server(app);
const io = new Server(httpServer, { cors: { origin: "*" } });

app.use(cors());
app.use(express.json());

app.post("/", (req: express.Request, res: express.Response) => {
  console.log("received a message", req.body);
  res.send("msg received");
  let message: string = req.body.message;
  const sanitizedMessage = new String(message.replace(/\uFFFD/g, ""));
  let receipes =
    "{list: " + sanitizedMessage.substring(1, message.length) + "}";

  console.log(receipes.normalize("NFC"));

  const response = JSON.parse(receipes.normalize("NFC"));
  console.log(response);

  io.emit("recipe-fetched", {
    message: response,
  });
});

io.on("connection", (client) => {
  // io.emit('users-online', User.getUserList());
  console.log("connected", client);
  io.emit("review-created", {
    name: "i'm a review",
  });
  // socket.disconnectClient(client, io);
  // socket.addUserOnline(client, io);
  // socket.removeUserOnline(client, io);
});

httpServer.listen(8089, () => {
  console.log("listening on localhost:8089");
});
