import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import db from "./src/model/connection.js"; // <--- Aquí se inicia la conexión

import Test from './src/router/Test.js';
import userRouter from './src/router/userRoutes.js';
import router from './src/router/timerRouter.js';
import archivoRouter from './src/router/archivoRouter.js';
import morgan from 'morgan';
import authRouter from './src/router/auth.js';
// dot env config
dotenv.config()

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

// Rutas
app.get('/', (req,res)=>{
  return res.json({
    saludo: "Hola mundo"
  })
})
app.use('/api/test', Test);
app.use('/api/user', userRouter);
app.use('/api/timer', router);
app.use('/api/archivo', archivoRouter);
app.use('/api/auth', authRouter);







// Manejo de errores
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ message: '¡Algo salió mal!' });
});

app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto http://localhost:${PORT}`);
}); 