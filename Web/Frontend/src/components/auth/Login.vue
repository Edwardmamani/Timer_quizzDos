<template>
    <!-- Aquí va tu formulario -->
    <form method="post"  @submit.prevent="handleLogin">
        <div class="mb-3">
            <label for="email" class="form-label">Correo electrónico</label>
            <input name="email" type="email" class="form-control" id="email" v-model="user.email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <input name="password" type="password" class="form-control" id="password" v-model="user.password" required>
        </div>
        <button type="submit" class="btn btn-success">Login</button>
    </form>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
const router = useRouter();

const emit = defineEmits(['hideComponent']);
const user = ref({
    email: '',
    password: ''
});

const handleLogin = async () => {
    console.log('Login button clicked');
    try {
        const response = await axios.post('http://localhost:3001/api/auth/login', user.value);
        console.log('Response from login:', response);
        
        if (response.status !== 200) {
            throw new Error('Login failed');
        }
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('archivoRaiz', response.data.archivoRaiz._id);

        console.log('Login successful:', response);
        emit('hideComponent', response.data.token); 

        router.push({ name: 'file' }); 
    } catch (error) {
        console.error('Login failed:', error.response);
        
    }
};

</script>