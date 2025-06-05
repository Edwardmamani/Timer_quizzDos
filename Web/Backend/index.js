import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';

import Test from './src/router/Test.js';
import router from './src/router/userRoutes.js';


// dot env config
dotenv.config()

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());

// Rutas
app.use('/api/test', Test);
app.use('/api/user', router);







// Manejo de errores
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ message: '¡Algo salió mal!' });
});

app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
}); 