<template>
    <!-- Aquí va tu formulario -->
    <form @submit.prevent="handleRegister">
        <div class="mb-3">
            <label for="email" class="form-label">Correo electrónico</label>
            <input type="email" class="form-control" id="email" v-model="user.email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <input type="password" class="form-control" id="password" v-model="user.password" required>
        </div>
        <div class="mb-3">
            <label for="password-confir" class="form-label">Contraseña</label>
            <input type="password" class="form-control" id="password-confir" required>
        </div>
        <button type="submit" class="btn btn-success">Registrarse</button>
    </form>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();
const user = ref({
    name: 'edward',
    password: '',
    email: ''
});
const emit = defineEmits(['hideComponent']);
const handleRegister = async () => {
    console.log('Register button clicked');
    axios.post('http://localhost:3001/api/auth/register', user.value)
        .then(response => {
            if (response.status !== 201) {
                throw new Error('Registration failed');
            }
            console.log('Registration response:', response);
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('archivoRaiz', response.data.archivoRaiz._id);
            console.log('Registration successful:', response);
            emit('hideComponent');
            router.push({ name: 'file' });

        })
        .catch(error => {
            console.error('Registration failed:', error);
        });
};

</script>