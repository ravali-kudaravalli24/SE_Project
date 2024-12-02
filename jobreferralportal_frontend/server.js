const express = require('express');
const path = require('path');
const { createProxyMiddleware } = require('http-proxy-middleware');

const app = express();

// Serve the static files from the Angular build
const buildPath = path.join(__dirname, 'dist/my-angular-app');
app.use(express.static(buildPath));

// Proxy API requests
app.use(
  '/api',
  createProxyMiddleware({
    target: 'https://se-project-5mhm.onrender.com', // Replace with your backend URL
    changeOrigin: true,
    secure: false, // For HTTPS backend; set to true if the backend is fully secure
  })
);

// Catch-all route to serve the Angular app for any non-API requests
app.get('*', (req, res) => {
  res.sendFile(path.join(buildPath, 'index.html'));
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
