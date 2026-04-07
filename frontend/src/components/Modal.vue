<template>
  <transition name="modal-fade">
    <div v-if="modelValue" class="modal-backdrop" @click.self="close">
      <div class="modal-card">
        <div class="modal-header">
          <h3>{{ title }}</h3>
          <button type="button" class="close-button" @click="close" aria-label="Fechar modal">×</button>
        </div>
        <div class="modal-body">
          <slot />
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
  title: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

function close() {
  emit('update:modelValue', false)
}
</script>
