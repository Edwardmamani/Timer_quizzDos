import express from 'express';
import timerController from '../controller/timerController.js';

const router = express.Router();

// Rutas básicas CRUD para timers
router.get('/', timerController.getAllTimers);
router.get('/:id', timerController.getTimerById);
router.post('/', timerController.createTimer);
router.put('/:id', timerController.updateTimer);
router.delete('/:id', timerController.deleteTimer);

// Rutas específicas para usuarios
router.get('/user/:usuario_id', timerController.getTimersByUser);

// Rutas para estadísticas
router.get('/:id/stats', timerController.getTimerStats);

// Rutas para manejo de preguntas
router.post('/:id/preguntas', timerController.addPregunta);
router.put('/:id/preguntas/:preguntaId', timerController.updatePregunta);
router.delete('/:id/preguntas/:preguntaId', timerController.deletePregunta);

export default router;