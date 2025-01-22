"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var WebSocket = require("ws");
var http = require("http");
// Création d'un serveur HTTP
var server = http.createServer(function (req, res) {
    const headers = {
        'Access-Control-Allow-Origin': '*', /* @dev First, read about security */
        'Access-Control-Allow-Methods': 'OPTIONS, POST, GET',
        'Access-Control-Max-Age': 2592000, // 30 days
        /** add other headers as per requirement */
    };
    res.setHeader('Access-Control-Allow-Origin', '*'); /* @dev First, read about security */
    res.setHeader('Access-Control-Allow-Methods', 'OPTIONS, GET');
    res.setHeader('Access-Control-Max-Age', 2592000); // 30 days
    // res.setHeader('Access-Control-Allow-Headers', req.headers.origin as string);
    
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end('WebSocket server is running\n');
      res.writeHead(200, { 'Content-Type': 'text/plain' });
      res.end('WebSocket server is running\n');
});
// Création d'un serveur WebSocket attaché au serveur HTTP
var wss = new WebSocket.Server({ server: server });
// Associer chaque email à un client WebSocket
var emailToClients = new Map();
// Gestion des connexions clients
wss.on('connection', function (ws, req) {
    var _a, _b;
    var params = new URLSearchParams(((_a = req.url) === null || _a === void 0 ? void 0 : _a.split('?')[1]) || '');
    var email = params.get('email');
    if (!email) {
        ws.close(4001, 'Email manquant');
        return;
    }
    console.log("Client connect\u00E9 avec l'email: ".concat(email));
    // Associer le WebSocket à l'email
    if (!emailToClients.has(email)) {
        emailToClients.set(email, []);
    }
    (_b = emailToClients.get(email)) === null || _b === void 0 ? void 0 : _b.push(ws);
    // Gestion des messages entrants
    ws.on('message', function (message) {
        console.log("Message re\u00E7u de ".concat(email, ":"), message);
        // Exemple : envoyer un message uniquement au propriétaire du canal
        var clients = emailToClients.get(email) || [];
        clients.forEach(function (client) {
            if (client.readyState === WebSocket.OPEN) {
                client.send("Message priv\u00E9 pour ".concat(email, ": ").concat(message));
            }
        });
    });
    // Gestion de la déconnexion
    ws.on('close', function () {
        console.log("Client d\u00E9connect\u00E9 avec l'email: ".concat(email));
        var clients = emailToClients.get(email) || [];
        emailToClients.set(email, clients.filter(function (client) { return client !== ws; }));
    });
});
// Fonction simulant l'envoi de notifications privées
function sendPrivateNotification(email, notification) {
    var clients = emailToClients.get(email) || [];
    clients.forEach(function (client) {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify({ type: 'notification', data: notification }));
        }
    });
}
// Exemple : Envoi d'une notification privée toutes les 10 secondes
setInterval(function () {
    emailToClients.forEach(function (_, email) {
        sendPrivateNotification(email, "Hello ".concat(email, ", voici une notification priv\u00E9e!"));
    });
}, 10000);
// Démarrer le serveur
var PORT = 8089;
server.listen(PORT, function () {
    console.log("Serveur WebSocket s\u00E9curis\u00E9 en \u00E9coute sur le port bizarre ".concat(PORT));
});
