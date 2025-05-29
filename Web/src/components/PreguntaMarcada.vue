<template>
    <div class="col-12 col-md-6">
        <div class="pregunta-box glass d-flex align-items-center justify-content-between">
            <div>
                <span class="pregunta-num">{{ pregunta.numero }}.</span>
                <span class="pregunta-letra">{{ pregunta.letra }}</span>
                <span class="pregunta-tiempo"><i class="fas fa-clock me-1"></i>{{ pregunta.tiempo }}</span>
            </div>
            <button class="btn btn-gradient" @click="seleccionarPregunta">continuar</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
    pregunta: {
        type: Object,
        required: true
    }
})
const emit = defineEmits(['preguntaSeleccionada'])
const seleccionarPregunta = () => {
    emit('preguntaSeleccionada', props.pregunta)
}
</script>

<style scoped>

</style>
