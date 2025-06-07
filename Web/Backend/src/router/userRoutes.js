import express from 'express';
import userController from '../controller/userController.js';

const userRouter = express.Router();

// Rutas para usuarios
userRouter.get('/', userController.getAllUsers); // bien
userRouter.get('/:id', userController.getUserById); // bien
userRouter.post('/', userController.createUser); // bien
userRouter.put('/:id', userController.updateUser);
userRouter.delete('/:id', userController.deleteUser);

export default userRouter;