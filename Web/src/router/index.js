import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Temporizador',
    component: () => import('../pages/Temporizador.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../pages/Login.vue')
  },
  {
    path: '/registro',
    name: 'Registro',
    component: () => import('../pages/Registro.vue')
  },
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 