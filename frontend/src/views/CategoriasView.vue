<template>
  <main class="container">
    <div class="page-header">
      <div>
        <h2>Gerenciamento de Categorias</h2>
        <p>Organize suas categorias e mantenha o cadastro atualizado para facilitar o controle dos produtos.</p>
      </div>
    </div>

    <CategoryForm :admin="admin" :editando="false" :initialForm="form" @salvar="salvar" @limpar="limpar" />

    <DataTable :columns="columns" :data="categorias" :showActions="admin" :selectedId="modalItemId" @edit="abrirEdicao" @delete="excluir" />

    <Modal v-model="modalOpen" title="Editar categoria">
      <CategoryForm
        :admin="admin"
        :editando="true"
        :initialForm="modalForm"
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
import CategoryForm from '../components/CategoryForm.vue'
import DataTable from '../components/DataTable.vue'
import Modal from '../components/Modal.vue'

const categorias = ref([])
const admin = isAdmin()
const modalOpen = ref(false)
const modalItemId = ref(null)

const form = reactive({
  nome: '',
  descricao: ''
})

const modalForm = reactive({
  nome: '',
  descricao: ''
})

const columns = [
  { key: 'id', label: 'ID' },
  { key: 'nome', label: 'Nome' },
  { key: 'descricao', label: 'Descrição' }
]

function limpar() {
  form.nome = ''
  form.descricao = ''
}

function abrirEdicao(cat) {
  modalForm.nome = cat.nome
  modalForm.descricao = cat.descricao
  modalItemId.value = cat.id
  modalOpen.value = true
}

function fecharModal() {
  modalOpen.value = false
  modalItemId.value = null
  modalForm.nome = ''
  modalForm.descricao = ''
}

async function salvarEdicao(formData) {
  await api.put(`/categorias/${modalItemId.value}`, formData)
  fecharModal()
  await carregar()
}

async function carregar() {
  const { data } = await api.get('/categorias')
  categorias.value = data
}

async function salvar(formData) {
  await api.post('/categorias', formData)
  limpar()
  await carregar()
}

async function excluir(id) {
  await api.delete(`/categorias/${id}`)
  await carregar()
}

onMounted(carregar)
</script>
