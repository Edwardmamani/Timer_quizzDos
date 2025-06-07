<template>

  <div class="d-flex flex-grow-1 overflow-hidden gap-3">

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
    <div class="d-flex flex-column  overflow-hidden flex-grow-1">

      <TemporizadorPrincipal :temporizador="temporizador" @opcionSeleccionada="seleccionarOpcion"
        @togglePause="togglePause" @editNombreTemprizador="editarTemporizador" ref="refTemporizador" />
      <div class="option-line mt-3 mb-3"></div>


      <!-- Menú inferior -->
      <div class="d-flex justify-content-between align-items-center mt-2 mb-2">
        <span class="fw-semibold"><i class="fas fa-list-ul me-2"></i>Preguntas</span>
        <span class="fw-semibold"><i class="fas fa-chart-bar me-2"></i>Estadísticas</span>
      </div>

      <!-- Preguntas -->
      <!-- Evitamos el desorden de las preguntas en móvil, pero en mobile todas las preguntas se muestran en una sola columna -->
      <div class="d-flex flex-column justify-content-between flex-grow-1  overflow-y-scroll p-2">
        <div class="row g-1 g-md-2 ">
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
import HoraActual from '../components/HoraActual.vue';
import PreguntaMarcada from '../components/PreguntaMarcada.vue';
import TemporizadorPrincipal from '../components/TemporizadorPrincipal.vue';

const refTemporizador = ref(null);

const temporizador = ref({
  paused: true,
  id_temporizador: 1,
  nombre: 'Primer temporizador',
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
  }
])



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

const editarTemporizador = ()=>{
  console.log("Puedes guardar el nuevo valor de Nombre temporizador");
  console.log(temporizador.value.nombre)
}
onMounted(() => {
  console.log('refTemporizador montado:', refTemporizador.value)
});
</script>

<style scoped></style>
