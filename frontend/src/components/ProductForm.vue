<template>
  <section class="card" v-if="admin" style="margin-bottom: 16px">
    <div class="page-header">
      <div>
        <h3>{{ editando ? 'Editar produto' : 'Novo produto' }}</h3>
        <p class="hint">{{ editando ? 'Altere os dados do produto e confirme para salvar as mudanças.' : 'Preencha os campos para cadastrar um novo produto.' }}</p>
      </div>
    </div>

    <form @submit.prevent="salvar" class="row column">
      <div class="field-grid full">
        <input v-model="form.nome" placeholder="Nome" required />
        <textarea v-model="form.descricao" placeholder="Descrição" required></textarea>
      </div>

      <div class="field-section">
        <p class="field-section-label">Preencha a quantidade e o preço para calcular o estoque corretamente.</p>
      </div>

      <div class="field-grid">
        <div class="field-group">
          <label>Quantidade</label>
          <input v-model.number="form.quantidade" type="number" min="0" placeholder="0" required />
        </div>

        <div class="field-group">
          <label>Preço</label>
          <div class="input-prefix">
            <span>R$</span>
            <input v-model.number="form.preco" type="number" min="0.01" step="0.01" placeholder="0.00" required />
          </div>
        </div>

        <div class="field-group">
          <label>Categoria</label>
          <select v-model.number="form.categoriaId" required>
            <option disabled value="">Selecione a categoria</option>
            <option v-for="cat in categorias" :key="cat.id" :value="cat.id">{{ cat.nome }}</option>
          </select>
        </div>
      </div>

      <div class="row align-right" style="width: 100%;">
        <button>{{ editando ? 'Atualizar' : 'Criar' }}</button>
        <button class="secondary" type="button" @click="limpar">{{ editando ? 'Cancelar' : 'Limpar' }}</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  admin: Boolean,
  editando: Boolean,
  initialForm: Object,
  categorias: Array
})

const emit = defineEmits(['salvar', 'limpar'])

const form = reactive({
  nome: '',
  descricao: '',
  quantidade: 0,
  preco: 0,
  categoriaId: ''
})

watch(
  () => props.initialForm,
  (next) => {
    if (next) {
      Object.assign(form, next)
    }
  },
  { immediate: true, deep: true }
)

function salvar() {
  emit('salvar', form)
}

function limpar() {
  Object.assign(form, { nome: '', descricao: '', quantidade: 0, preco: 0, categoriaId: '' })
  emit('limpar')
}
</script>