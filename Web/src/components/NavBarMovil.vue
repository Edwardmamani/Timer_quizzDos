<template>
    <!-- Menú superior versión móvil -->
    <div class="d-flex justify-content-between align-items-center mb-4 d-lg-none">
        <!-- Cuando se presiona el icono de la hamburgesa se muestra el menú lateral (Menu de Mis temporizadores) -->
        <span id="btn-menu-hamburguesa" class="menu-hamburguesa d-flex align-items-center justify-content-center" style="cursor:pointer;"><i class="fas fa-bars fa-lg"></i></span>
        
        <div class="d-flex align-items-center gap-2">
          <span class="menu-sync d-flex align-items-center justify-content-center me-2" style="cursor:pointer;"><i class="fas fa-sync-alt fa-lg"></i></span>
          <span class="menu-ajuste d-flex align-items-center justify-content-center" style="cursor:pointer;"><i class="fas fa-cog fa-lg"></i></span>
        </div>
      </div>
</template>