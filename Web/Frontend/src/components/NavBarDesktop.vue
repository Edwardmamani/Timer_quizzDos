<template>
    <!-- Menú superior versión escritorio -->
    <nav
        class="navbar navbar-expand-lg bg-white shadow-sm rounded-3 px-4 py-2 mb-4 d-none d-lg-flex align-items-center justify-content-between">
        <div class="d-flex align-items-center gap-3">
            <span class="logo"></span>
            <a class="nav-link fw-semibold text-dark fs-5" href="#">Acerca de Nosotros</a>
        </div>

       <BtnAut :user="user" />


    </nav>
</template>

<script setup>
import BtnAut from './BtnAut.vue';
// Props
const props = defineProps({
    user: {
        type: Object,
        required: true
    },
});
</script>