<template>

  <NavBarDesktop />
  <NavBarMovil />


  <div class="d-flex justify-content-between gap-3">

    <!-- Menú lateral-->
    <div id="menu-lateral" class="d-none d-lg-block">
      <div class="glass p-4">
        <span class="fw-bold">Mis temporizadores</span>
        <div>
          <span>Semama 2 - Tema 3</span>
          <span>01:06:29.3</span>
          <span>04:42.8</span>
        </div>
        <div>
          <span>Semama 2 - Tema 4</span>
          <span>01:06:29.3</span>
          <span>04:42.8</span>
        </div>
      </div>
    </div>

    <!-- contenido de la derecha -->
    <div>

      <TemporizadorPrincipal :temporizador="temporizador" @opcionSeleccionada="seleccionarOpcion"
      @togglePause = "togglePause"/>
      <div class="option-line"></div>


      <!-- Menú inferior -->
      <div class="d-flex justify-content-between align-items-center mt-4 mb-2">
        <span class="fw-semibold"><i class="fas fa-list-ul me-2"></i>Preguntas</span>
        <span class="fw-semibold"><i class="fas fa-chart-bar me-2"></i>Estadísticas</span>
      </div>

      <!-- Preguntas -->
      <!-- Evitamos el desorden de las preguntas en móvil, pero en mobile todas las preguntas se muestran en una sola columna -->
      <div class="d-md-block ">
        <div class="row g-3 ">
          <div class="col-12 col-md-6">
            <div class="pregunta-box glass d-flex align-items-center justify-content-between">
              <div>
                <span class="pregunta-num">1.</span>
                <span class="pregunta-letra">A</span>
                <span class="pregunta-tiempo"><i class="fas fa-clock me-1"></i>3:45.7</span>
              </div>
              <button class="btn btn-gradient">continuar</button>
            </div>
          </div>
          <div class="col-12 col-md-6">
            <div class="pregunta-box glass d-flex align-items-center justify-content-between">
              <div>
                <span class="pregunta-num">2.</span>
                <span class="pregunta-letra">E</span>
                <span class="pregunta-tiempo"><i class="fas fa-clock me-1"></i>3:45.7</span>
              </div>
              <button class="btn btn-gradient">continuar</button>
            </div>
          </div>
          <div class="col-12 col-md-6">
            <div class="pregunta-box glass d-flex align-items-center justify-content-between">
              <div>
                <span class="pregunta-num">3.</span>
                <span class="pregunta-letra">C</span>
                <span class="pregunta-tiempo"><i class="fas fa-clock me-1"></i>3:45.7</span>
              </div>
              <button class="btn btn-gradient">continuar</button>
            </div>
          </div>
          <div class="col-12 col-md-6">
            <div class="pregunta-box glass d-flex align-items-center justify-content-between">
              <div>
                <span class="pregunta-num">4.</span>
                <span class="pregunta-letra">D</span>
                <span class="pregunta-tiempo"><i class="fas fa-clock me-1"></i>3:45.7</span>
              </div>
              <button class="btn btn-gradient">continuar</button>
            </div>
          </div>
          <div class="col-12 col-md-6">
            <div class="pregunta-box glass d-flex align-items-center justify-content-between">
              <div>
                <span class="pregunta-num">5.</span>
                <span class="pregunta-letra">A</span>
                <span class="pregunta-tiempo"><i class="fas fa-clock me-1"></i>3:45.7</span>
              </div>
              <button class="btn btn-gradient">continuar</button>
            </div>
          </div>
        </div>
      </div>


      <!-- Hora Actual-->
      <HoraActual />
    </div>


  </div>
</template>

<script setup>
import { ref } from 'vue';
import NavBarDesktop from '../components/NavBarDesktop.vue';
import NavBarMovil from '../components/NavBarMovil.vue';
import HoraActual from '../components/HoraActual.vue';
import TemporizadorPrincipal from '../components/TemporizadorPrincipal.vue';

const temporizador = ref({
  paused: true,
  id_temporizador: 1,
  nombre: 'Semama 2 - Tema 3',
  tiempo_total: '01:05:30.0',
  tiempo_pregunta: '02:10.0',
  pregunta_actual: '6.',
  opciones: [{
    alternativa: 'A',
    seleccionada: false,

  },
  {
    alternativa: 'B',
    seleccionada: true,
  },
  {
    alternativa: 'C',
    seleccionada: false,
  },
  {
    alternativa: 'D',
    seleccionada: false,
  },
  {
    alternativa: 'E',
    seleccionada: false,
  }
  ],
});

const seleccionarOpcion = (u) => {
 console.log(temporizador.value.opciones)
 console.log(u)
}

const togglePause = () => {
  //Cambiar el estado del temporizador
  console.log(temporizador.value.tiempo_total)
 
}
</script>

<style scoped></style>
