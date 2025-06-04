<template>
  <div class="footer-hora mt-4">
    <i class="fas fa-clock me-2"></i>{{ HA }}
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { nowTime } from '../js/Time'


const HA = ref('')
let intervalo = null

function actualizarHora() {
  HA.value = nowTime()
}


onMounted(() => {
  actualizarHora() // Actualiza inmediatamente
  intervalo = setInterval(actualizarHora, 1000) // Luego cada segundo
})

onUnmounted(() => {
  clearInterval(intervalo) // Limpia cuando se destruye el componente
})
</script>