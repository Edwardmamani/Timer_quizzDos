import express from "express";
import archivoController from '../controller/achivoController.js'

const archivoRouter = express.Router()

archivoRouter.post('/', archivoController.createArchivoNormal)
archivoRouter.get('/:id_archivo', archivoController.getArchivoByArchivo)
archivoRouter.get('/raiz/:id_user', archivoController.getArchivoRaizByUser)

export default archivoRouter;
