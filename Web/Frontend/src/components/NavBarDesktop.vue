<template>
    <!-- Menú superior versión escritorio -->
    <nav
        class="navbar navbar-expand-lg bg-white shadow-sm rounded-3 px-4 py-2 mb-4 d-none d-lg-flex align-items-center justify-content-between">
        <div class="d-flex align-items-center gap-3">
            <span class="logo"></span>
            <a class="nav-link fw-semibold text-dark fs-5" href="#">Acerca de Nosotros</a>
        </div>

        <!-- Si el usuario está autenticado -->
        <div class="d-flex align-items-center gap-2" v-if="user.autentificado">
            <button class="btn btn-outline-primary rounded-pill px-3"><i class="fas fa-user me-2"></i>Perfil</button>
            <button class="btn btn-gradient rounded-pill px-3"><i class="fas fa-sign-out-alt me-2"></i>Salir</button>
        </div>

        <!-- Si el usuario NO está autenticado -->
        <div class="d-flex align-items-center gap-2" v-else>
            <button class="btn btn-outline-primary rounded-pill px-3">Login
            </button>
            <button class="btn btn-gradient rounded-pill px-3">
                <i class="fas fa-right-to-bracket me-2"></i>Register
            </button>
        </div>


    </nav>
</template>

<script setup>

// Props
const props = defineProps({
    user: {
        type: Object,
        required: true
    },
});
</script>