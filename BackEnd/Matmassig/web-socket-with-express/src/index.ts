import express, { Express, Request, Response } from "express";

const app: Express = express();

var cors = require("cors");
const { Server } = require("socket.io");
const http = require("http");

const httpServer = new http.Server(app);
const io = new Server(httpServer, { cors: { origin: "*" } });

app.use(cors());
app.use(express.json());

app.post("/recipeByUser", (req: express.Request, res: express.Response) => {
  console.log("received a message", req.body);
  res.send("msg received");
  io.emit("recipe-fetched", {
    message: req.body.message,
  });
});

app.post("/reviewCreated", (req: express.Request, res: express.Response) => {
  console.log("received a message", req.body);
  res.send("msg received");
  io.emit("review-created", {
    message: req.body.message,
  });
});

app.post("/recipeCreated", (req: express.Request, res: express.Response) => {
  res.send("msg received");
  io.emit("recipe-created", {
    message: req.body.message
  });
});
app.post("/ItemCreated", (req: express.Request, res: express.Response) => {
  res.send("msg received");
  io.emit("item-created", {
    message: req.body.message
  });
});

app.post("/reviewByUser", (req: express.Request, res: express.Response) => {
  console.log("received a message", req.body);
  res.send("msg received");
  io.emit("reviewByUser-fetched", {
    message: req.body.message,
  });
});
app.post("/reviewDeleted", (req: express.Request, res: express.Response) => {
  console.log("received a message", req.body);
  res.send("msg received");
  io.emit("reviewDeleted", {
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
