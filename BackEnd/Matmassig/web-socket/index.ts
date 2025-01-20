import * as WebSocket from 'ws';
import * as http from 'http';

// Création d'un serveur HTTP
const server = http.createServer((req, res) => {
  res.writeHead(200, { 'Content-Type': 'text/plain' });
  res.end('WebSocket server is running\n');
});

// Création d'un serveur WebSocket attaché au serveur HTTP
const wss = new WebSocket.Server({ server });

// Associer chaque email à un client WebSocket
const emailToClients: Map<string, WebSocket[]> = new Map();

// Gestion des connexions clients
wss.on('connection', (ws: WebSocket, req) => {
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
  ws.on('message', (message: string) => {
    console.log(`Message reçu de ${email}:`, message);

    // Exemple : envoyer un message uniquement au propriétaire du canal
    const clients = emailToClients.get(email) || [];
    clients.forEach(client => {
      if (client.readyState === WebSocket.OPEN) {
        client.send(`Message privé pour ${email}: ${message}`);
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

// Fonction simulant l'envoi de notifications privées
function sendPrivateNotification(email: string, notification: string) {
  const clients = emailToClients.get(email) || [];
  clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify({ type: 'notification', data: notification }));
    }
  });
}

// Exemple : Envoi d'une notification privée toutes les 10 secondes
setInterval(() => {
  emailToClients.forEach((_, email) => {
    sendPrivateNotification(email, `Hello ${email}, voici une notification privée!`);
  });
}, 10000);

// Démarrer le serveur
const PORT = 8089;
server.listen(PORT, () => {
  console.log(`Serveur WebSocket sécurisé en écoute sur le port ${PORT}`);
});
