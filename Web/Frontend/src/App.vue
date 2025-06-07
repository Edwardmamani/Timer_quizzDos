<template>
    <NavBarDesktop :user="user" />
    <NavBarMovil :user="user" />

    <router-view></router-view>

</template>

<script setup>
import { ref } from 'vue';

import NavBarDesktop from './components/NavBarDesktop.vue';
import NavBarMovil from './components/NavBarMovil.vue';

const user = ref({
  token: localStorage.getItem("token") !== null,
  autentificado: localStorage.getItem("token") !== null,
  configuracion: {

  }
})

</script>