<template>
    <!-- Temporizador principal -->
    <div class="row mb-3">
        <div class="col-12">
            <div class="glass p-4">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="timer-name">{{ temporizador.nombre }}</span>
                    </div>
                    <div class="timer-main mb-1 fw-bold">{{ temporizador.tiempo_total }}</div>
                </div>

                <div class="timer-secondary">
                    Tiempo pregunta: <span class="fw-semibold">{{ temporizador.tiempo_pregunta }}</span>
                </div>
            </div>
        </div>
    </div>

    <!-- Alternativas que el usuario puede seleccionar -->
    <div class="d-flex justify-content-between ps-3 pe-3">
        <!-- Opciones de respuesta -->
        <div class="d-flex align-items-center gap-2">
            <span class="fs-4 fw-bold me-2">{{ temporizador.pregunta_actual }}.</span>
            <CircleOpcion v-for="opcion in temporizador.opciones" :key="opcion.alternativa" :opcion="opcion"
                @seleccionar="seleccionarOpcion" />
        </div>
        <!-- Opcion de pausador  -->
        <div>
            <div class="option-pause" :class="{ 'paused': temporizador.paused }" @click="togglePause">
                {{ temporizador.paused ? '⏸️' : '▶️' }}
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue';
import CircleOpcion from './CircleOpcion.vue';
import { parseTiempo, formatearTiempo, nowTime } from '../js/Time';

// Props
const props = defineProps({
    temporizador: {
        type: Object,
        required: true
    },
});

// Emits
const emit = defineEmits(['opcionSeleccionada', 'togglePause']);

// Métodos
const seleccionarOpcion = (alternativa) => {
    props.temporizador.opciones.forEach(opcion => {
        if (opcion.alternativa === alternativa) {
            opcion.seleccionada = true;
        } else {
            opcion.seleccionada = false;
        }
    });

    emit('opcionSeleccionada', {
        alternativa,
        preguntaActual: props.temporizador.pregunta_actual,
        tiempoPregunta: props.temporizador.tiempo_pregunta
    });
};
let intervalo = null;

const togglePause = () => {
    console.log('togglePause');
    if (props.temporizador.paused) {
        props.temporizador.paused = false;
        correrTiempo();
    } else {
        props.temporizador.paused = true;
        pausarTiempo();
    }
    emit('togglePause');
};
const correrTiempo = () => {

    let inicio_tiempo_total = performance.now() - parseTiempo(props.temporizador.tiempo_total)
    let inicio_tiempo_pregunta = performance.now() - parseTiempo(props.temporizador.tiempo_pregunta)
    intervalo = setInterval(() => {
        const ahora = performance.now()
        const transcurrido_total = ahora - inicio_tiempo_total
        const transcurrido_pregunta = ahora - inicio_tiempo_pregunta
        props.temporizador.tiempo_total = formatearTiempo(transcurrido_total)
        props.temporizador.tiempo_pregunta = formatearTiempo(transcurrido_pregunta)
    }, 100)
}
const pausarTiempo = () => {
    clearInterval(intervalo);
}

onMounted(() => {
    if (props.temporizador.paused) {
        console.log('El tiempo esta pausado');

    } else {
        console.log('El tiempo esta corriendo');
        correrTiempo();
    }
});

onUnmounted(() => {
    clearInterval(intervalo);
});

function saludar() {
  console.log('Hola desde el hijo 👶');
}
defineExpose({
    saludar,
    pausarTiempo,
    correrTiempo
});
</script>

<style scoped>
.glass {
    background: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.timer-name {
    font-size: 1.2rem;
    color: #333;
}

.timer-main {
    font-size: 2rem;
    color: #0d6efd;
}

.timer-secondary {
    color: #6c757d;
    font-size: 0.9rem;
}

.option-pause {
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 5px;
    transition: all 0.3s ease;
}

.option-pause:hover {
    background-color: #e9ecef;
}

.option-pause.paused {
    color: #dc3545;
}

.option-line {
    height: 2px;
    background-color: #dee2e6;
    margin: 10px 0;
}
</style>
