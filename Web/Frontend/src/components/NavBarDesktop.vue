<template>
    <!-- Menú superior versión escritorio -->
    <nav
        class="navbar navbar-expand-lg bg-white shadow-sm rounded-3 px-4 py-2 mb-4 d-none d-lg-flex align-items-center justify-content-between">
        <div class="d-flex align-items-center gap-3">
            <span class="logo"></span>
            <router-link :to="{ name: 'Temporizador' }" class="nav-link fw-semibold text-dark fs-5" href="#">Acerca de Nosotros</router-link>
            <router-link :to="{ name: 'file' }">Files</router-link>
        </div>

        <BtnAut />


    </nav>
</template>

<script setup>
import { RouterLink } from 'vue-router';
import BtnAut from './BtnAut.vue';
// Props
</script>