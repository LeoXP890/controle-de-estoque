<template>
  <section class="card table-card">
    <table>
      <thead>
        <tr>
          <th v-for="col in columns" :key="col.key">{{ col.label }}</th>
          <th v-if="showActions">Ações</th>
        </tr>
      </thead>
      <tbody v-if="data.length">
        <tr v-for="item in data" :key="item.id" :class="{ 'selected-row': item.id === selectedId }">
          <td v-for="col in columns" :key="col.key">
            <span v-if="col.key === 'preco'">R$ {{ Number(item[col.key]).toFixed(2) }}</span>
            <span v-else>{{ item[col.key] }}</span>
          </td>
          <td v-if="showActions">
            <div class="row">
              <button class="secondary" @click="$emit('edit', item)">Editar</button>
              <button class="danger" @click="$emit('delete', item.id)">Excluir</button>
            </div>
          </td>
        </tr>
      </tbody>
      <tbody v-else>
        <tr>
          <td :colspan="columns.length + (showActions ? 1 : 0)" class="empty-state">
            Nenhum registro encontrado.
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
const props = defineProps({
  columns: Array,
  data: Array,
  showActions: Boolean,
  selectedId: [String, Number]
})

defineEmits(['edit', 'delete'])
</script>