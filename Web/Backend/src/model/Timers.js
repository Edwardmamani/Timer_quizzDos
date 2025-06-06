import { mongoose, Schema } from 'mongoose'

const preguntaSchema = new Schema({
    marcado: { type: String, default: "" },
    tiempo: { type: String, default: "00:00.0" }
})

const timerSchema = new mongoose.Schema({
    usuario_id: { type: Schema.Types.ObjectId, ref: 'User', required: true },
    nombre: { type: String, required: true },
    preguntas: [preguntaSchema],
    creado_en: { type: Date, default: Date.now },
    duracion_total_segundos: { type: Number, required: true }
});

export default mongoose.model('Timer', timerSchema)