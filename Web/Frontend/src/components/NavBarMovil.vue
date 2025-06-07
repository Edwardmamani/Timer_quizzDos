<template>
  <!-- Menú superior versión móvil -->
  <nav class="d-flex justify-content-between align-items-center mb-4 d-lg-none">
    <!-- Cuando se presiona el icono de la hamburgesa se muestra el menú lateral (Menu de Mis temporizadores) -->
    <span id="btn-menu-hamburguesa" class="menu-hamburguesa d-flex align-items-center justify-content-center" 
    ref="btnMenu"  
    style="cursor:pointer;"><i class="fas fa-bars fa-lg"></i></span>

   <BtnAut />
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted, defineProps } from 'vue';
import BtnAut from './BtnAut.vue';


const btnMenu = ref(null);

let menuLateral;

onMounted(() => {
  menuLateral = document.getElementById('menu-lateral');
  function toggleMenu() {
    console.log("Click en menu ambuerguesa:")
    if (window.innerWidth < 992) {
      menuLateral.classList.toggle('d-none'); //ocultar el menu lateral
      menuLateral.classList.toggle('menu-lateral-movil'); //mostrar el menu lateral
    }
  };
  btnMenu.value?.addEventListener('click', toggleMenu)

  menuLateral.addEventListener('click', toggleMenu);
})

// Los removemos al desmontar
onUnmounted(() => {
  btnMenu.value?.removeEventListener('click', toggleMenu);
  menuLateral.removeEventListener('click', toggleMenu);
});

</script>