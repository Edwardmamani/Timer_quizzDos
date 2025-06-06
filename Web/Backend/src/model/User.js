import { mongoose, Schema } from 'mongoose'

const userSchema = new mongoose.Schema({
    nombre: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    password_hash: { type: String, required: true },
    preferencias: {
        idioma: { type: String, default: 'es' },
        notificaciones: { type: Boolean, default: true }
    },
    rol: { type: String, enum: ['usuario', 'admin'], default: 'usuario' },
    creado_en: { type: Date, default: Date.now },
    ultimo_login: { type: Date, default: Date.now }
});

export default mongoose.model('User', userSchema)
