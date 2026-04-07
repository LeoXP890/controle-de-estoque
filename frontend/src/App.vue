<template>
  <div>
    <header class="nav" v-if="loggedIn">
      <div class="container">
        <strong>Controle de Estoque</strong>
        <nav class="nav-links">
          <router-link to="/dashboard">Dashboard</router-link>
          <router-link to="/produtos">Produtos</router-link>
          <router-link to="/categorias">Categorias</router-link>
          <router-link v-if="role === 'ADMIN'" to="/usuarios">Usuários</router-link>
          <router-link to="/perfil" class="user-link">{{ username }}</router-link>
          <span>({{ role }})</span>
          <button class="secondary" @click="logout">Sair</button>
        </nav>
      </div>
    </header>
    <router-view @auth-updated="refreshAuthState" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { clearAuth, getRole, getUsername, isAuthenticated } from './services/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const tick = ref(0)

const loggedIn = computed(() => {
  tick.value
  return isAuthenticated()
})

const role = computed(() => {
  tick.value
  return getRole() || ''
})

const username = computed(() => {
  tick.value
  return getUsername() || ''
})

function refreshAuthState() {
  tick.value++
}

function logout() {
  clearAuth()
  tick.value++
  router.push('/login')
}
</script>
