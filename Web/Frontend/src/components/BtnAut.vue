<template>
    <!-- Si el usuario está autenticado -->
    <div class="d-flex align-items-center gap-2" v-if="user.autentificado">
        <button class="btn btn-outline-primary rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#btnAut"  @click="componenteActual = Perfil"><i class="fas fa-user me-2"></i>Perfil</button>
        <button class="btn btn-gradient rounded-pill px-3"><i class="fas fa-sign-out-alt me-2"></i>Salir</button>
    </div>

    <!-- Si el usuario NO está autenticado -->
    <div class="d-flex align-items-center gap-2" v-else>
        <button class="btn btn-outline-primary rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#btnAut" @click="componenteActual = Login">Login
        </button>
        <button class="btn btn-gradient rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#btnAut"  @click="componenteActual = Registro">
            <i class="fas fa-right-to-bracket me-2"></i>Register
        </button>
    </div>

    <Modal :componente="componenteActual"/>
</template>

<script setup>
import { shallowRef  ,defineProps } from 'vue';
import Modal from './Modal.vue';
import Login from './auth/Login.vue';
import Registro from './auth/Registro.vue';
import Perfil from './Perfil.vue';

const componenteActual = shallowRef(null)

const props = defineProps({
    user: {
        type: Object,
        required: true
    }
})

</script>