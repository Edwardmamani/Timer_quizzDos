import { createRouter, createWebHistory } from 'vue-router'
import About from '../pages/About.vue'
import Temporizador from '../pages/Temporizador.vue'
import Archivo from '../pages/Archivo.vue'

const routes = [
  {
    path: '/', name: 'about', component: About,
  },
  {
    path: '/temporizador', name: 'Temporizador', component: Temporizador,
  },
  {
    path: '/file', name: 'file', component: Archivo, meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('token');

  if (to.meta.requiresAuth && !isAuthenticated) next({ name: 'about' });
  else next();

});

export default router 