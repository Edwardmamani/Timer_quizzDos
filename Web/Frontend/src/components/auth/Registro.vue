<template>
    <!-- Aquí va tu formulario -->
    <form>
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="nombre" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Correo electrónico</label>
            <input type="email" class="form-control" id="email" required>
        </div>
        <button type="submit" class="btn btn-success">Registrarse</button>
    </form>
</template>