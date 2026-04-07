<template>
  <main class="container">
    <div class="page-header">
      <h2>Gerenciamento de Usuários</h2>
      <p>Visualize todos os usuários cadastrados no sistema.</p>
    </div>

    <DataTable :columns="columns" :data="usuarios" :showActions="false" />
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'
import DataTable from '../components/DataTable.vue'

const usuarios = ref([])

const columns = [
  { key: 'id', label: 'ID' },
  { key: 'username', label: 'Username' },
  { key: 'role', label: 'Role' }
]

async function carregarUsuarios() {
  try {
    const { data } = await api.get('/users')
    usuarios.value = data
  } catch (error) {
    console.error('Erro ao carregar usuários:', error)
  }
}

onMounted(() => {
  carregarUsuarios()
})
</script>