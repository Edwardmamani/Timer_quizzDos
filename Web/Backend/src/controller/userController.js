import User from '../model/User.js'
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
        const user = new User({
            nombre: req.body.nombre,
            email: req.body.email,
            password_hash: req.body.password_hash,
            preferencias: {
                tema_visual: req.body.preferencias?.tema_visual,
                idioma: req.body.preferencias?.idioma || 'es',
                notificaciones: req.body.preferencias?.notificaciones ?? true
            },
            rol: req.body.rol || 'usuario',
        });

        try {
            const newUser = await user.save();
            res.status(201).json(newUser);
        } catch (error) {
            res.status(400).json({ message: error.message });
        }
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
    }
};

export default userController; 