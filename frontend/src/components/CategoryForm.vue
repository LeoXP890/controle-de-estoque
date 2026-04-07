<template>
  <section class="card" v-if="admin" style="margin-bottom: 16px">
    <div class="page-header">
      <div>
        <h3>{{ editando ? 'Editar categoria' : 'Nova categoria' }}</h3>
        <p class="hint">{{ editando ? 'Atualize a categoria selecionada ou cancele para voltar ao cadastro.' : 'Adicione uma nova categoria para organizar seus produtos.' }}</p>
      </div>
    </div>

    <form @submit.prevent="salvar" class="row column">
      <div class="field-grid full">
        <input v-model="form.nome" placeholder="Nome" required />
        <textarea v-model="form.descricao" placeholder="Descrição" required></textarea>
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
  initialForm: Object
})

const emit = defineEmits(['salvar', 'limpar'])

const form = reactive({
  nome: '',
  descricao: ''
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
  Object.assign(form, { nome: '', descricao: '' })
  emit('limpar')
}
</script>