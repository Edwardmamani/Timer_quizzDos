<template>
    <NavBarDesktop />
    <NavBarMovil />

    <router-view></router-view>

</template>

<script setup>
import { ref } from 'vue';

import NavBarDesktop from './components/NavBarDesktop.vue';
import NavBarMovil from './components/NavBarMovil.vue';


</script>