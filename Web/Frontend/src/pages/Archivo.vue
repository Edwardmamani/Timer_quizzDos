<template>
    <div class="p-xxl-4">
        <!-- Header con breadcrumb y controles -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <!-- Breadcrumb -->
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0">
                    <li class="breadcrumb-item">
                        <a href="#" class="text-decoration-none">Mi unidad</a>
                    </li>
                    <li class="breadcrumb-item">
                        <a href="#" class="text-decoration-none">SISTEMAS</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">6S-2024-I</li>
                </ol>
            </nav>

            <!-- Controles de vista -->
            <div class="d-flex align-items-center gap-2">
                <div class="btn-group" role="group">
                    <button type="button" :class="['btn', 'btn-outline-secondary', { 'active': viewMode === 'list' }]"
                        @click="viewMode = 'list'">
                        <i class="bi bi-list"></i>
                    </button>
                    <button type="button" :class="['btn', 'btn-outline-secondary', { 'active': viewMode === 'grid' }]"
                        @click="viewMode = 'grid'">
                        <i class="bi bi-grid-3x3-gap"></i>
                    </button>
                </div>

                <!-- Filtros -->
                <div class="dropdown">
                    <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
                        Tipo
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Todos</a></li>
                        <li><a class="dropdown-item" href="#" @click="crearFolder">Carpetas</a></li>
                        <li><a class="dropdown-item" href="#" @click="crearDocumento">Documento</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Vista de Lista -->
        <div v-if="viewMode === 'list'" class="table-responsive">
            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
                        <th scope="col" class="border-0">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-arrow-up me-2"></i>
                                Nombre
                            </div>
                        </th>
                        <th scope="col" class="border-0">Propietario</th>
                        <th scope="col" class="border-0">Fecha de modificación</th>
                        <th scope="col" class="border-0">Tamaño de archivo</th>
                        <th scope="col" class="border-0"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in items" :key="item.id" class="align-middle">
                        <td>
                            <div class="d-flex align-items-center">
                                <i :class="getIcon(item)" class="me-3 text-primary fs-4"></i>
                                <span class="fw-medium">{{ item.name }}</span>
                            </div>
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <img :src="item.owner.avatar" :alt="item.owner.name" class="rounded-circle me-2"
                                    width="24" height="24">
                                <span class="text-muted">{{ item.owner.name }}</span>
                            </div>
                        </td>
                        <td class="text-muted">{{ formatDate(item.modifiedDate) }}</td>
                        <td class="text-muted">{{ item.size || '—' }}</td>
                        <td>
                            <div class="dropdown">
                                <button class="btn btn-sm btn-ghost" data-bs-toggle="dropdown">
                                    <i class="bi bi-three-dots-vertical"></i>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="#">Abrir</a></li>
                                    <li><a class="dropdown-item" href="#">Compartir</a></li>
                                    <li><a class="dropdown-item" href="#">Descargar</a></li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item text-danger" href="#">Eliminar</a></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Vista de Cuadrícula -->
        <div v-else class="row g-3">
            <div v-for="item in items" :key="item.id" class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2">
                <div class="card h-100 shadow-sm border-0 hover-card">
                    <div class="card-body text-center p-3">
                        <div class="mb-3">
                            <i :class="getIcon(item)" class="text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h6 class="card-title text-truncate mb-2" :title="item.name">{{ item.name }}</h6>
                        <div class="d-flex align-items-center justify-content-center mb-2">
                            <img :src="item.owner.avatar" :alt="item.owner.name" class="rounded-circle me-1" width="16"
                                height="16">
                            <small class="text-muted">{{ item.owner.name }}</small>
                        </div>
                        <small class="text-muted d-block">{{ formatDate(item.modifiedDate) }}</small>
                        <small class="text-muted" v-if="item.size">{{ item.size }}</small>
                    </div>

                    <!-- Overlay con acciones -->
                    <div class="card-overlay">
                        <div class="dropdown">
                            <button class="btn btn-light btn-sm" data-bs-toggle="dropdown">
                                <i class="bi bi-three-dots"></i>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Abrir</a></li>
                                <li><a class="dropdown-item" href="#">Compartir</a></li>
                                <li><a class="dropdown-item" href="#">Descargar</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item text-danger" href="#">Eliminar</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '../store/auth.store.js'
const authStorage = useAuthStore()

// Estado reactivo
const viewMode = ref('list') // 'list' o 'grid'

// Datos de ejemplo
const items = ref([])

// Funciones
const getIcon = (item) => {
    const icons = {
        folder: 'bi bi-folder-fill',
        image: 'bi bi-file-earmark-image-fill',
        pdf: 'bi bi-file-earmark-pdf-fill',
        document: 'bi bi-file-earmark-text-fill',
        default: 'bi bi-file-earmark-fill'
    }
    return icons[item.type] || icons.default
}

const formatDate = (date) => {
    const now = new Date()
    const diffTime = Math.abs(now - date)
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))

    if (diffDays === 1) return 'ayer'
    if (diffDays < 30) return `${diffDays} días`
    if (diffDays < 365) {
        const months = ['ene', 'feb', 'mar', 'abr', 'may', 'jun',
            'jul', 'ago', 'sep', 'oct', 'nov', 'dic']
        return `${date.getDate()} ${months[date.getMonth()]}`
    }
    return date.getFullYear().toString()
}
const crearFolder = () => {
    console.log('Crear carpeta')
    // Aquí puedes implementar la lógica para crear una carpeta
    // a http://localhost:3001/api/archivo por post
    axios.post('http://localhost:3001/api/archivo/', {
        name: 'Nueva Carpeta (2)',
        type: 'folder',
        archivo: authStorage.getArchivoRaiz() 
    }).then(response => {
        console.log('Carpeta creada:', response.data)
        // Recargar los datos después de crear la carpeta
        fechtData()
    }).catch(error => {
        console.error('Error al crear la carpeta:', error)

    })
}
const crearDocumento = () => {
    console.log('Crear documento')
    // Aquí puedes implementar la lógica para crear un documento
}

// antes de cargar los datos, inicializamos algunos ejemplos
const fechtData = () => {
    // http://localhost:3001/api/archivo/[archivoId]
    const archivoId = authStorage.getArchivoRaiz()
    axios.get('http://localhost:3001/api/archivo/' + archivoId)
        .then(response => {
            console.log('Datos cargados:', response)
            items.value = response.data.archivos.map(item => ({
                ...item,
                modifiedDate: new Date(item.modifiedDate),
                owner: {
                    name: item.owner.name,
                    avatar: item.owner.avatar || 'https://via.placeholder.com/24'
                }
            }))
        })
        .catch(error => {
            console.error('Error fetching data:', error)
        })
}
fechtData()
</script>

<style scoped>
/* Estilos personalizados */
.hover-card {
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    position: relative;
    overflow: hidden;
}

.hover-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15) !important;
}

.card-overlay {
    position: absolute;
    top: 8px;
    right: 8px;
    opacity: 0;
    transition: opacity 0.2s ease;
}

.hover-card:hover .card-overlay {
    opacity: 1;
}

.btn-ghost {
    background: none;
    border: none;
    color: #6c757d;
}

.btn-ghost:hover {
    background-color: rgba(0, 0, 0, 0.05);
    color: #495057;
}

.table th {
    font-weight: 600;
    color: #495057;
    font-size: 0.875rem;
}

.table tbody tr:hover {
    background-color: rgba(0, 0, 0, 0.02);
}

.breadcrumb-item+.breadcrumb-item::before {
    content: "›";
    font-weight: bold;
}

/* Responsive adjustments */
@media (max-width: 768px) {
    .d-flex.justify-content-between {
        flex-direction: column;
        gap: 1rem;
    }

    .table-responsive {
        font-size: 0.875rem;
    }
}
</style>