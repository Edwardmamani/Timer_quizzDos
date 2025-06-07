import { ArchivoRaiz, ArchivoNormal,ArchivoBase } from '../model/Archivo.js'
const _createArchivoRaiz = async (req) => {
    const { id_user } = req.params;

    // Verificar si ya existe un ArchivoRaiz para el usuario
    const existingRaiz = await ArchivoRaiz.findOne({ owner: id_user });
    if (existingRaiz) {
        return { status: 400, message: 'Ya existe un archivo raíz para este usuario.' };
    }

    // Crear un nuevo ArchivoRaiz
    const newRaiz = new ArchivoRaiz({
        owner: id_user,
        name: 'Mi Unidad',
        type: 'folder'
    });

    try {
        const savedRaiz = await newRaiz.save();
        return { status: 201, data: savedRaiz };
    } catch (error) {
        return { status: 500, message: error.message };
    }
}
const archivoController = {
    // Crear un ArchivoRaiz para el usuario
    createArchivoRaiz: async (req, res) => {
        _createArchivoRaiz(req)
            .then(response => {
                res.status(response.status).json(response);
            })
            .catch(error => {
                res.status(500).json({ message: error.message });
            });
    },

    // Crear un ArchivoNormal para un archivo
    createArchivoNormal: async (req, res) => {
        const { name, type, archivo } = req.body;

        // Verificar si el archivo padre existe
        const parentArchivo = await ArchivoBase.findById(archivo);
        if (!parentArchivo) {
            return res.status(404).json({ message: 'Archivo padre no encontrado.' });
        }

        // Crear un nuevo ArchivoNormal
        const newArchivo = new ArchivoNormal({
            name,
            type,
            archivo: archivo,
            owner: parentArchivo.owner // Asignar el mismo owner que el archivo padre
        });

        try {
            const savedArchivo = await newArchivo.save();
            return res.status(201).json({savedArchivo });
        } catch (error) {
            return res.status(500).json({ message: error.message });
        }
    },

    __createArchivoRaiz: async (req) => {
        return _createArchivoRaiz(req)
    },

    // 1. Obtener el ArcivoRaiz del usuario
    getArchivoRaizByUser: async (req, res) => {
        const { id_user } = req.params

        const raiz = ArchivoRaiz.find({ owner: id_user })
        return res.status(200).json({
            data: raiz
        })
    },


    // 2. Obtener los Archivo(folder, pdf, img) que estan dentro de un Archivo tipo folder
    getArchivoByArchivo: async (req, res) => {
        try {
            const { id_archivo } = req.params;
            const archivos = await ArchivoNormal.find({ archivo: id_archivo })

            return res.status(200).json({ archivos });
        } catch (error) {
            return res.status(500).json({ message: error.message });
        }
    }

}

export default archivoController