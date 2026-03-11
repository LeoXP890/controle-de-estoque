<template>
  <main class="container">
    <h2>Dashboard</h2>
    <div class="row">
      <section class="card" style="min-width: 220px">
        <h3>Total de Produtos</h3>
        <p style="font-size: 30px">{{ totalProdutos }}</p>
      </section>
      <section class="card" style="min-width: 220px">
        <h3>Total de Categorias</h3>
        <p style="font-size: 30px">{{ totalCategorias }}</p>
      </section>
    </div>
    <section class="card" style="margin-top: 16px">
      <h3>Navegação</h3>
      <p><router-link to="/produtos">Ir para produtos</router-link></p>
      <p><router-link to="/categorias">Ir para categorias</router-link></p>
    </section>
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'

const totalProdutos = ref(0)
const totalCategorias = ref(0)

async function carregarTotais() {
  const [produtos, categorias] = await Promise.all([
    api.get('/produtos'),
    api.get('/categorias')
  ])
  totalProdutos.value = produtos.data.length
  totalCategorias.value = categorias.data.length
}

onMounted(carregarTotais)
</script>
