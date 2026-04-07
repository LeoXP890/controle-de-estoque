<template>
  <main class="container">
    <div class="page-header">
      <h2>Meu Perfil</h2>
      <p>Visualize e gerencie suas informações pessoais.</p>
    </div>

    <div v-if="user" class="profile-card">
      <div class="profile-info">
        <p><strong>ID:</strong> {{ user.id }}</p>
        <p><strong>Username:</strong> {{ user.username }}</p>
        <p><strong>Role:</strong> {{ user.role }}</p>
      </div>
    </div>
    <div v-else>
      <p>Carregando...</p>
    </div>
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'

const user = ref(null)

async function carregarPerfil() {
  try {
    const { data } = await api.get('/users/me')
    user.value = data
  } catch (error) {
    console.error('Erro ao carregar perfil:', error)
  }
}

onMounted(() => {
  carregarPerfil()
})
</script>

<style scoped>
.profile-card {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.profile-info p {
  margin: 0.5rem 0;
}
</style>