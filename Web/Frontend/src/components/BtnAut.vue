<template>
    <!-- Si el usuario está autenticado -->
    <div class="d-flex align-items-center gap-2" v-if="authStore.token">
        <button class="btn btn-outline-primary rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#btnAut"
            @click="componenteActual = Perfil"><i class="fas fa-user me-2"></i>Perfil</button>
        <button class="btn btn-gradient rounded-pill px-3" @click="logout"><i
                class="fas fa-sign-out-alt me-2"></i>Salir</button>
    </div>

    <!-- Si el usuario NO está autenticado -->
    <div class="d-flex align-items-center gap-2" v-else>
        <button class="btn btn-outline-primary rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#btnAut"
            @click="componenteActual = Login">Login
        </button>
        <button class="btn btn-gradient rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#btnAut"
            @click="componenteActual = Registro">
            <i class="fas fa-right-to-bracket me-2"></i>Register
        </button>
    </div>

    <div class="modal fade" id="btnAut" tabindex="-1" aria-labelledby="registroModalLabel" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="registroModalLabel">Modal</h5>
                    <button type="button" class="btn-close" ref="btnClose" data-bs-dismiss="modal"
                        aria-label="Cerrar"></button>
                </div>
                <div class="modal-body" v-if="componenteActual">
                    <component :is="componenteActual" @hideComponent="hidenModal" />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, shallowRef, defineProps } from 'vue';
import Login from './auth/Login.vue';
import Registro from './auth/Registro.vue';
import Perfil from './Perfil.vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../store/auth.store';
const authStore = useAuthStore();
const router = useRouter();

const btnClose = ref(null);
const componenteActual = shallowRef(null)

const hidenModal = (token) => {
    if (btnClose.value) {
        authStore.setToken(token);
        componenteActual.value = null;
        btnClose.value.click();
    }
    console.log('Modal hidden');
}

const logout = () => {
    authStore.logout();
    console.log('User logged out');
    router.push({ name: 'about' }); 
}
</script>