<template>
  <main class="container">
    <div class="page-header">
      <div>
        <h2>Gerenciamento de Produtos</h2>
        <p>Adicione, edite e remova produtos com facilidade enquanto acompanha o estoque disponível.</p>
      </div>
    </div>

    <ProductForm :admin="admin" :editando="false" :initialForm="form" :categorias="categorias" @salvar="salvar" @limpar="limpar" />

    <DataTable :columns="columns" :data="produtos" :showActions="admin" :selectedId="modalItemId" @edit="abrirEdicao" @delete="excluir" />

    <Modal v-model="modalOpen" title="Editar produto">
      <ProductForm
        :admin="admin"
        :editando="true"
        :initialForm="modalForm"
        :categorias="categorias"
        @salvar="salvarEdicao"
        @limpar="fecharModal"
      />
    </Modal>
  </main>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import api from '../services/api'
import { isAdmin } from '../services/auth'
import ProductForm from '../components/ProductForm.vue'
import DataTable from '../components/DataTable.vue'
import Modal from '../components/Modal.vue'

const admin = isAdmin()
const produtos = ref([])
const categorias = ref([])
const modalOpen = ref(false)
const modalItemId = ref(null)

const form = reactive({
  nome: '',
  descricao: '',
  quantidade: 0,
  preco: 0,
  categoriaId: ''
})

const modalForm = reactive({
  nome: '',
  descricao: '',
  quantidade: 0,
  preco: 0,
  categoriaId: ''
})

const columns = [
  { key: 'nome', label: 'Nome' },
  { key: 'quantidade', label: 'Quantidade' },
  { key: 'preco', label: 'Preço' },
  { key: 'categoriaNome', label: 'Categoria' }
]

function limpar() {
  form.nome = ''
  form.descricao = ''
  form.quantidade = 0
  form.preco = 0
  form.categoriaId = ''
}

function abrirEdicao(prod) {
  modalForm.nome = prod.nome
  modalForm.descricao = prod.descricao
  modalForm.quantidade = prod.quantidade
  modalForm.preco = Number(prod.preco)
  modalForm.categoriaId = prod.categoriaId
  modalItemId.value = prod.id
  modalOpen.value = true
}

function fecharModal() {
  modalOpen.value = false
  modalItemId.value = null
  modalForm.nome = ''
  modalForm.descricao = ''
  modalForm.quantidade = 0
  modalForm.preco = 0
  modalForm.categoriaId = ''
}

async function carregarCategorias() {
  const { data } = await api.get('/categorias')
  categorias.value = data
}

async function carregarProdutos() {
  const { data } = await api.get('/produtos')
  produtos.value = data
}

async function salvar(formData) {
  await api.post('/produtos', formData)
  limpar()
  await carregarProdutos()
}

async function salvarEdicao(formData) {
  await api.put(`/produtos/${modalItemId.value}`, formData)
  fecharModal()
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
