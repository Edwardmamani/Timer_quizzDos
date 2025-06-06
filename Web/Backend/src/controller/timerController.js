import Timer from "../model/Timers.js";

const timerController = {
    // 1. CREAR - Crear un nuevo timer
    createTimer: async (req, res) => {
        try {
            const { usuario_id, nombre, preguntas, duracion_total_segundos } = req.body;

            const nuevoTimer = new Timer({
                usuario_id,
                nombre,
                preguntas: preguntas || [],
                duracion_total_segundos
            });

            const timerGuardado = await nuevoTimer.save();
            res.status(201).json({
                success: true,
                message: "Timer creado exitosamente",
                data: timerGuardado
            });
        } catch (error) {
            res.status(400).json({
                success: false,
                message: "Error al crear timer",
                error: error.message
            });
        }
    },

    // 2. LEER - Obtener todos los timers
    getAllTimers: async (req, res) => {
        try {
            const timers = await Timer.find().populate('usuario_id', 'nombre email');
            res.status(200).json({
                success: true,
                data: timers,
                total: timers.length
            });
        } catch (error) {
            res.status(500).json({
                success: false,
                message: "Error al obtener timers",
                error: error.message
            });
        }
    },

    // 3. LEER - Obtener timer por ID
    getTimerById: async (req, res) => {
        try {
            const { id } = req.params;
            const timer = await Timer.findById(id).populate('usuario_id', 'nombre email');

            if (!timer) {
                return res.status(404).json({
                    success: false,
                    message: "Timer no encontrado"
                });
            }

            res.status(200).json({
                success: true,
                data: timer
            });
        } catch (error) {
            res.status(500).json({
                success: false,
                message: "Error al obtener timer",
                error: error.message
            });
        }
    },

    // 4. LEER - Obtener timers por usuario
    getTimersByUser: async (req, res) => {
        try {
            const { usuario_id } = req.params;
            const timers = await Timer.find({ usuario_id }).populate('usuario_id', 'nombre email');

            res.status(200).json({
                success: true,
                data: timers,
                total: timers.length
            });
        } catch (error) {
            res.status(500).json({
                success: false,
                message: "Error al obtener timers del usuario",
                error: error.message
            });
        }
    },

    // 5. ACTUALIZAR - Actualizar timer completo
    updateTimer: async (req, res) => {
        try {
            const { id } = req.params;
            const updateData = req.body;

            const timerActualizado = await Timer.findByIdAndUpdate(
                id,
                updateData,
                { new: true, runValidators: true }
            ).populate('usuario_id', 'nombre email');

            if (!timerActualizado) {
                return res.status(404).json({
                    success: false,
                    message: "Timer no encontrado"
                });
            }

            res.status(200).json({
                success: true,
                message: "Timer actualizado exitosamente",
                data: timerActualizado
            });
        } catch (error) {
            res.status(400).json({
                success: false,
                message: "Error al actualizar timer",
                error: error.message
            });
        }
    },

    // 6. ACTUALIZAR - Agregar pregunta al timer
    addPregunta: async (req, res) => {
        try {
            const { id } = req.params;
            const { marcado, tiempo } = req.body;

            const timer = await Timer.findByIdAndUpdate(
                id,
                { $push: { preguntas: { marcado: marcado || "", tiempo: tiempo || "00:00.0" } } },
                { new: true, runValidators: true }
            );

            if (!timer) {
                return res.status(404).json({
                    success: false,
                    message: "Timer no encontrado"
                });
            }

            res.status(200).json({
                success: true,
                message: "Pregunta agregada exitosamente",
                data: timer
            });
        } catch (error) {
            res.status(400).json({
                success: false,
                message: "Error al agregar pregunta",
                error: error.message
            });
        }
    },

    // 7. ACTUALIZAR - Actualizar pregunta específica
    updatePregunta: async (req, res) => {
        try {
            const { id, preguntaId } = req.params;
            const { marcado, tiempo } = req.body;

            const timer = await Timer.findOneAndUpdate(
                { _id: id, "preguntas._id": preguntaId },
                {
                    $set: {
                        "preguntas.$.marcado": marcado,
                        "preguntas.$.tiempo": tiempo
                    }
                },
                { new: true, runValidators: true }
            );

            if (!timer) {
                return res.status(404).json({
                    success: false,
                    message: "Timer o pregunta no encontrado"
                });
            }

            res.status(200).json({
                success: true,
                message: "Pregunta actualizada exitosamente",
                data: timer
            });
        } catch (error) {
            res.status(400).json({
                success: false,
                message: "Error al actualizar pregunta",
                error: error.message
            });
        }
    },

    // 8. ELIMINAR - Eliminar timer
    deleteTimer: async (req, res) => {
        try {
            const { id } = req.params;
            const timerEliminado = await Timer.findByIdAndDelete(id);

            if (!timerEliminado) {
                return res.status(404).json({
                    success: false,
                    message: "Timer no encontrado"
                });
            }

            res.status(200).json({
                success: true,
                message: "Timer eliminado exitosamente",
                data: timerEliminado
            });
        } catch (error) {
            res.status(500).json({
                success: false,
                message: "Error al eliminar timer",
                error: error.message
            });
        }
    },

    // 9. ELIMINAR - Eliminar pregunta específica
    deletePregunta: async (req, res) => {
        try {
            const { id, preguntaId } = req.params;

            const timer = await Timer.findByIdAndUpdate(
                id,
                { $pull: { preguntas: { _id: preguntaId } } },
                { new: true }
            );

            if (!timer) {
                return res.status(404).json({
                    success: false,
                    message: "Timer no encontrado"
                });
            }

            res.status(200).json({
                success: true,
                message: "Pregunta eliminada exitosamente",
                data: timer
            });
        } catch (error) {
            res.status(500).json({
                success: false,
                message: "Error al eliminar pregunta",
                error: error.message
            });
        }
    },

    // 10. OPERACIONES ESPECIALES - Obtener estadísticas del timer
    getTimerStats: async (req, res) => {
        try {
            const { id } = req.params;
            const timer = await Timer.findById(id);

            if (!timer) {
                return res.status(404).json({
                    success: false,
                    message: "Timer no encontrado"
                });
            }

            const stats = {
                nombre: timer.nombre,
                duracion_total_segundos: timer.duracion_total_segundos,
                total_preguntas: timer.preguntas.length,
                preguntas_marcadas: timer.preguntas.filter(p => p.marcado !== "").length,
                fecha_creacion: timer.creado_en
            };

            res.status(200).json({
                success: true,
                data: stats
            });
        } catch (error) {
            res.status(500).json({
                success: false,
                message: "Error al obtener estadísticas",
                error: error.message
            });
        }
    }
};

export default timerController;