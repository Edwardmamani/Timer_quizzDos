import express from 'express'
var Test = express.Router();

// Ruta de prueba
Test.get('/', (req, res) => {
  res.json({ message: 'API funcionando correctamente' });
});


// Aquí se agregarán más rutas según sea necesario
// router.use('/quizzes', require('./quizzes'));
export default Test;