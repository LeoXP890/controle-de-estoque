<template>
  <main class="container">
    <h2>Gerenciamento de Categorias</h2>

    <section class="card" v-if="admin" style="margin-bottom: 16px">
      <h3>{{ editando ? 'Editar categoria' : 'Nova categoria' }}</h3>
      <form @submit.prevent="salvar" class="row" style="flex-direction: column">
        <input v-model="form.nome" placeholder="Nome" required />
        <textarea v-model="form.descricao" placeholder="Descrição" required></textarea>
        <div class="row">
          <button>{{ editando ? 'Atualizar' : 'Criar' }}</button>
          <button class="secondary" type="button" @click="limpar">Limpar</button>
        </div>
      </form>
    </section>

    <section class="card">
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Descrição</th>
          <th v-if="admin">Ações</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="cat in categorias" :key="cat.id">
          <td>{{ cat.id }}</td>
          <td>{{ cat.nome }}</td>
          <td>{{ cat.descricao }}</td>
          <td v-if="admin">
            <div class="row">
              <button class="secondary" @click="prepararEdicao(cat)">Editar</button>
              <button class="danger" @click="excluir(cat.id)">Excluir</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </section>
  </main>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import api from '../services/api'
import { isAdmin } from '../services/auth'

const categorias = ref([])
const admin = isAdmin()
const editando = ref(false)
const idEdicao = ref(null)

const form = reactive({
  nome: '',
  descricao: ''
})

function limpar() {
  form.nome = ''
  form.descricao = ''
  editando.value = false
  idEdicao.value = null
}

function prepararEdicao(cat) {
  form.nome = cat.nome
  form.descricao = cat.descricao
  editando.value = true
  idEdicao.value = cat.id
}

async function carregar() {
  const { data } = await api.get('/categorias')
  categorias.value = data
}

async function salvar() {
  if (editando.value) {
    await api.put(`/categorias/${idEdicao.value}`, form)
  } else {
    await api.post('/categorias', form)
  }
  limpar()
  await carregar()
}

async function excluir(id) {
  await api.delete(`/categorias/${id}`)
  await carregar()
}

onMounted(carregar)
</script>
