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

      <!-- Temporizador principal -->
      <div class="row mb-3">
        <div class="col-12">

          <div class="glass p-4 ">

            <div class="d-flex d-flex justify-content-between">
              <div class="">
                <span class="timer-name">Semama 2 - Tema 3 </span>
              </div>
              <div class="timer-main mb-1 fw-bold">01:06:29.3</div>
            </div>

            <div class="timer-secondary">Tiempo pregunta: <span class="fw-semibold">04:42.8</span></div>


          </div>
        </div>
      </div>

      <!-- Alternativas que el usuario puede seleccionar -->
      <div class="d-flex justify-content-between ps-3 pe-3">
        <!-- Opciones de respuesta -->
        <div class="d-flex align-items-center gap-2">
          <span class="fs-4 fw-bold me-2">6.</span>
          <span class="circle d-flex align-items-center justify-content-center fs-5 fw-bold selected">A</span>
          <span class="circle d-flex align-items-center justify-content-center fs-5 fw-bold">B</span>
          <span class="circle d-flex align-items-center justify-content-center fs-5 fw-bold">C</span>
          <span class="circle d-flex align-items-center justify-content-center fs-5 fw-bold">D</span>
          <span class="circle d-flex align-items-center justify-content-center fs-5 fw-bold">E</span>
        </div>
        <!-- Opcion de pausador  -->
        <div>

          <div class="option-pause"> ||</div>
        </div>
      </div>
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
      <div class="footer-hora mt-4"><i class="fas fa-clock me-2"></i>3:34 p.m</div>
    </div>


  </div>
</template>

<script setup>
import NavBarDesktop from '../components/NavBarDesktop.vue';
import NavBarMovil from '../components/NavBarMovil.vue';
</script>

<style scoped></style>
