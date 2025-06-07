import { ArchivoRaiz } from '../model/Archivo.js';
import User from '../model/User.js'
import archivoController from './achivoController.js';
import jwt from 'jsonwebtoken';

const _crearUsuario = async (req) => {
    const user = new User({
        name: req.body.name,
        email: req.body.email,
        password: req.body.password,
        preferencias: {
            tema_visual: req.body.preferencias?.tema_visual,
            idioma: req.body.preferencias?.idioma || 'es',
            notificaciones: req.body.preferencias?.notificaciones ?? true
        },
        rol: req.body.rol || 'usuario',
    });

    try {
        const newUser = await user.save();
        // Crear un archivo raíz para el usuario
        const respuest = await archivoController.__createArchivoRaiz({ params: { id_user: newUser._id } });
        const token = jwt.sign(
            { id: newUser._id, id_archivo_raiz: respuest.data._id, },
            process.env.JWT_SECRET,  // Cadena fija temporal
            { expiresIn: '48h' }
        );
        // Responder con el usuario creado
        return { status: 201, newUser, archivoRaiz: respuest.data, token };
    } catch (error) {
        return { status: 400, message: error.message };
    }
}

var userController = {
    // Obtener todos los usuarios
    getAllUsers: async (req, res) => {
        try {
            const users = await User.find();
            res.json(users);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    },

    // Obtener un usuario por ID
    getUserById: async (req, res) => {
        try {
            const user = await User.findById(req.params.id);
            if (!user) {
                return res.status(404).json({ message: 'Usuario no encontrado' });
            }
            res.json(user);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    },

    // Crear un nuevo usuario
    createUser: async (req, res) => {
       _crearUsuario(req)
            .then(response => {
                if (response.status === 201) {
                    res.status(201).json({
                        message: 'Usuario creado exitosamente',
                        user: response.newUser,
                        archivoRaiz: response.archivoRaiz,
                        token: response.token
                    });
                } else {
                    res.status(response.status).json({ message: response.message });
                }
            })
            .catch(error => {
                res.status(500).json({ message: error.message });
            });
    },

    // Actualizar un usuario
    updateUser: async (req, res) => {
        try {
            const user = await User.findById(req.params.id);
            if (!user) {
                return res.status(404).json({ message: 'Usuario no encontrado' });
            }

            if (req.body.nombre) user.nombre = req.body.nombre;
            if (req.body.email) user.email = req.body.email;

            const updatedUser = await user.save();
            res.json(updatedUser);
        } catch (error) {
            res.status(400).json({ message: error.message });
        }
    },

    // Eliminar un usuario
    deleteUser: async (req, res) => {
        try {
            const user = await User.findById(req.params.id);
            if (!user) {
                return res.status(404).json({ message: 'Usuario no encontrado' });
            }

            await user.deleteOne();
            res.json({ message: 'Usuario eliminado' });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    },

    login: async (req, res) => {
        const { email, password } = req.body;
        try {
            const user = await User.findOne({ email: email });
            if (user.length === 0) return res.status(404).json({ message: 'Usuario no encontrado' });

            if (user.password !== password) return res.status(401).json({ message: 'Contraseña incorrecta' });

            const token = jwt.sign(
                { id: user._id, id_archivo_raiz: user.archivo_raiz },
                process.env.JWT_SECRET,
                { expiresIn: '48h' }
            );
            const archivoRaiz = await ArchivoRaiz.findOne({ owner: user._id });

            res.json({ user: user, archivoRaiz, token });
        } catch (error) {
            return res.status(500).json({ message: error.message });
        }
    },
    // Registrar un nuevo usuario
    register: async (req, res) => {
        _crearUsuario(req)
            .then(response => {
                if (response.status === 201) {
                    res.status(201).json({
                        message: 'Usuario registrado exitosamente',
                        user: response.newUser,
                        archivoRaiz: response.archivoRaiz,
                        token: response.token
                    });
                } else {
                    res.status(response.status).json({ message: response.message });
                }
            })
            .catch(error => {
                res.status(500).json({ message: error.message });
            });
    }
};

export default userController; 