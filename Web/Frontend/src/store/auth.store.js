// state
// getters
// actions
import { defineStore } from 'pinia';


export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: null,
    archivoRaiz: null,
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.token,
    getUser: (state) => state.user,
    getToken: (state) => state.token,
    getArchivoRaiz: (state) => state.archivoRaiz,
  },
  
  actions: {
    setUser(user) {
      this.user = user;
    },
    
    setToken(token) {
      this.token = token;
    },
    setArchivoRaiz(archivoRaiz) {
      this.archivoRaiz = archivoRaiz;
    },
    
    clearAuth() {
      this.user = null;
      this.token = null;
    },
    logout() {
      this.clearAuth();
    }
  },
});