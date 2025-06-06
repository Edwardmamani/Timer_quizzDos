import express from 'express';
import userController from '../controller/userController.js';

const userRouter = express.Router();

// Rutas para usuarios
userRouter.get('/', userController.getAllUsers);
userRouter.get('/:id', userController.getUserById);
userRouter.post('/', userController.createUser);
userRouter.put('/:id', userController.updateUser);
userRouter.delete('/:id', userController.deleteUser);

export default userRouter;