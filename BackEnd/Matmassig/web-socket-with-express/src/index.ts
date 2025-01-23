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

  // const body = req.body;
  // const message = body.message as string;
  // console.log(message + "}");
  res.send("msg received");
  // const response = JSON.parse("{" + message.replace(message[0], "") + "}");
  // console.log(response);

  io.emit("recipe-fetched", {
    message: req.body.message,
  });
});

io.on("connection", () => {
  // io.emit('users-online', User.getUserList());
  console.log("connected");
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
