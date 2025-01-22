import * as WebSocket from 'ws';
import * as http from 'http';

// Création d'un serveur HTTP
const server: http.Server = http.createServer((req: http.IncomingMessage, res: http.ServerResponse) => {
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
const wss = new WebSocket.Server({ server });

// Associer chaque email à un client WebSocket
const emailToClients: Map<string, WebSocket[]> = new Map();

// Gestion des connexions clients
wss.on('connection', (ws: WebSocket, req: http.IncomingMessage) => {
  const params = new URLSearchParams(req.url?.split('?')[1] || '');
  const email = params.get('email');

  if (!email) {
    ws.close(4001, 'Email manquant');
    return;
  }

  console.log(`Client connecté avec l'email: ${email}`);

  // Associer le WebSocket à l'email
  if (!emailToClients.has(email)) {
    emailToClients.set(email, []);
  }
  emailToClients.get(email)?.push(ws);

  // Gestion des messages entrants
  ws.on('message', (message: WebSocket.RawData) => {
    const textMessage = message.toString(); // Conversion explicite en chaîne
    console.log(`Message reçu de ${email}:`, textMessage);

    // Répondre au client
    const clients = emailToClients.get(email) || [];
    clients.forEach(client => {
      if (client.readyState === WebSocket.OPEN) {
        client.send(`Message privé pour ${email}: ${textMessage}`);
      }
    });
  });

  // Gestion de la déconnexion
  ws.on('close', () => {
    console.log(`Client déconnecté avec l'email: ${email}`);
    const clients = emailToClients.get(email) || [];
    emailToClients.set(
      email,
      clients.filter(client => client !== ws)
    );
  });
});

// Fonction pour envoyer une notification privée
function sendPrivateNotification(email: string, notification: string) {
  const clients = emailToClients.get(email) || [];
  clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(`Notification: ${notification}`);
    }
  });
}

// Démarrer le serveur
const PORT = 8089;
server.listen(PORT, () => {
  console.log(`Serveur WebSocket en écoute sur le port bizarre ${PORT}`);
});
