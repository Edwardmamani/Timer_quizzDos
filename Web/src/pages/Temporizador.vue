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
        @togglePause="togglePause" ref="refTemporizador" />
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
          <PreguntaMarcada v-for="pregunta in preguntas" :key="pregunta.numero" :pregunta="pregunta"
            @preguntaSeleccionada="seleccionarPregunta" />

        </div>
      </div>


      <!-- Hora Actual-->
      <HoraActual />
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import NavBarDesktop from '../components/NavBarDesktop.vue';
import NavBarMovil from '../components/NavBarMovil.vue';
import HoraActual from '../components/HoraActual.vue';
import PreguntaMarcada from '../components/PreguntaMarcada.vue';
import TemporizadorPrincipal from '../components/TemporizadorPrincipal.vue';

const refTemporizador = ref(null);

const temporizador = ref({
  paused: true,
  id_temporizador: 1,
  nombre: 'Semama 2 - Tema 3',
  tiempo_total: '01:05:30.0',
  tiempo_pregunta: '00:00.0',
  pregunta_actual: 6,
  opciones: [{
    alternativa: 'A',
    seleccionada: false,

  },
  {
    alternativa: 'B',
    seleccionada: false,
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

const preguntas = ref([
  {
    numero: 1,
    letra: 'A',
    tiempo: '02:12.0',
  },
  {
    numero: 2,
    letra: 'E',
    tiempo: '03:15.0'
  },
  {
    numero: 3,
    letra: 'C',
    tiempo: '01:50.0'
  },
  {
    numero: 4,
    letra: 'D',
    tiempo: '03:01.0'
  },
  {
    numero: 5,
    letra: 'A',
    tiempo: '01:58.0'
  }
])

const inicio_timepo_total = ref(0);
const inicio_timepo_pregunta = ref(0);


const seleccionarOpcion = (u) => {
  console.log(temporizador.value.opciones)
  console.log(u)
  let pregunta = preguntas.value.find(pregunta => {
    if (pregunta.numero === u.preguntaActual) {
      pregunta.letra = u.alternativa;
      pregunta.tiempo = u.tiempoPregunta;
      return pregunta;
    }
  });

  if (!pregunta) {
    preguntas.value.push({
      numero: u.preguntaActual,
      letra: u.alternativa,
      tiempo: u.tiempoPregunta
    })
  }

  let preguntaSinOpcion = preguntas.value.find(pregunta => {
    if (!pregunta.letra) {
      return pregunta;
    }
  });
  if (preguntaSinOpcion) {
    modificarTemporizador(preguntaSinOpcion);
  } else {
    let ultimoNumero = preguntas.value[preguntas.value.length - 1].numero;
    let newPregunta = {
      numero: parseInt(ultimoNumero) + 1,
      letra: '',
      tiempo: '00:00.0'
    }
    modificarTemporizador(newPregunta);
  }
}

const togglePause = () => {
  //Cambiar el estado del temporizador
  console.log(temporizador.value.tiempo_total)
  refTemporizador.value.saludar();

}

const seleccionarPregunta = (pregunta) => {
  console.log("Seleccionamos la pregunta:", pregunta);
  const flag = parseInt(temporizador.value.pregunta_actual) == pregunta.numero;
  if(!flag){
    console.log('No es la pregunta actual')
     let p = preguntas.value.find(pregunta => {
      if(temporizador.value.pregunta_actual == pregunta.numero){
        pregunta.tiempo = temporizador.value.tiempo_pregunta;
        return pregunta;
      }
    })
    if(!p){
      preguntas.value.push({
        numero: parseInt(temporizador.value.pregunta_actual),
        letra: '',
        tiempo: temporizador.value.tiempo_pregunta
      })
    }

    modificarTemporizador(pregunta);
    
  }

}

const modificarTemporizador = (pregunta) => {

  console.log(refTemporizador.value && !temporizador.value.paused)
  if (refTemporizador.value && !temporizador.value.paused) {
    refTemporizador.value.pausarTiempo();
  }

  console.log("Modificamos los datos de temporizador:", pregunta);
  temporizador.value.pregunta_actual = pregunta.numero;
  temporizador.value.tiempo_pregunta = pregunta.tiempo;
  temporizador.value.opciones.forEach(opcion => {
    opcion.seleccionada = false;
    if (opcion.alternativa === pregunta.letra) {
      opcion.seleccionada = true;
    }
  });

  if (refTemporizador.value && !temporizador.value.paused) {
    refTemporizador.value.correrTiempo();
  }


}
onMounted(() => {
  console.log('refTemporizador montado:', refTemporizador.value)
});
</script>

<style scoped></style>
