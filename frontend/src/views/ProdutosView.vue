<template>
  <main class="container">
    <h2>Gerenciamento de Produtos</h2>

    <section class="card" v-if="admin" style="margin-bottom: 16px">
      <h3>{{ editando ? 'Editar produto' : 'Novo produto' }}</h3>
      <form @submit.prevent="salvar" class="row" style="flex-direction: column">
        <input v-model="form.nome" placeholder="Nome" required />
        <textarea v-model="form.descricao" placeholder="Descrição" required></textarea>
        <div class="row">
          <input v-model.number="form.quantidade" type="number" min="0" placeholder="Quantidade" required />
          <input v-model.number="form.preco" type="number" min="0.01" step="0.01" placeholder="Preço" required />
          <select v-model.number="form.categoriaId" required>
            <option disabled value="">Selecione a categoria</option>
            <option v-for="cat in categorias" :key="cat.id" :value="cat.id">{{ cat.nome }}</option>
          </select>
        </div>
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
          <th>Nome</th>
          <th>Quantidade</th>
          <th>Preço</th>
          <th>Categoria</th>
          <th v-if="admin">Ações</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="prod in produtos" :key="prod.id">
          <td>{{ prod.nome }}</td>
          <td>{{ prod.quantidade }}</td>
          <td>R$ {{ Number(prod.preco).toFixed(2) }}</td>
          <td>{{ prod.categoriaNome }}</td>
          <td v-if="admin">
            <div class="row">
              <button class="secondary" @click="prepararEdicao(prod)">Editar</button>
              <button class="danger" @click="excluir(prod.id)">Excluir</button>
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

const admin = isAdmin()
const produtos = ref([])
const categorias = ref([])
const editando = ref(false)
const idEdicao = ref(null)

const form = reactive({
  nome: '',
  descricao: '',
  quantidade: 0,
  preco: 0,
  categoriaId: ''
})

function limpar() {
  form.nome = ''
  form.descricao = ''
  form.quantidade = 0
  form.preco = 0
  form.categoriaId = ''
  editando.value = false
  idEdicao.value = null
}

function prepararEdicao(prod) {
  form.nome = prod.nome
  form.descricao = prod.descricao
  form.quantidade = prod.quantidade
  form.preco = Number(prod.preco)
  form.categoriaId = prod.categoriaId
  editando.value = true
  idEdicao.value = prod.id
}

async function carregarCategorias() {
  const { data } = await api.get('/categorias')
  categorias.value = data
}

async function carregarProdutos() {
  const { data } = await api.get('/produtos')
  produtos.value = data
}

async function salvar() {
  if (editando.value) {
    await api.put(`/produtos/${idEdicao.value}`, form)
  } else {
    await api.post('/produtos', form)
  }
  limpar()
  await carregarProdutos()
}

async function excluir(id) {
  await api.delete(`/produtos/${id}`)
  await carregarProdutos()
}

onMounted(async () => {
  await carregarCategorias()
  await carregarProdutos()
})
</script>
