<template>
  <main class="container">
    <section class="card auth-card">
      <div class="page-header">
        <div>
          <h2>Login</h2>
          <p>Entre com seu usuário e senha para acessar o sistema.</p>
        </div>
      </div>

      <form @submit.prevent="fazerLogin" class="row column">
        <input v-model="form.username" type="text" placeholder="Usuário" required />
        <input v-model="form.password" type="password" placeholder="Senha" required />
        <button :disabled="carregando">Entrar</button>
      </form>

      <p class="error" v-if="erro">{{ erro }}</p>
      <p style="margin-top: 16px; font-size: 13px; color: var(--muted);">
        Admin: <strong>admin/admin123</strong> | User: <strong>user/user123</strong>
      </p>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../services/api'
import { saveAuth } from '../services/auth'
import { useRouter } from 'vue-router'

const emit = defineEmits(['auth-updated'])
const router = useRouter()

const form = reactive({
  username: '',
  password: ''
})

const carregando = ref(false)
const erro = ref('')

async function fazerLogin() {
  erro.value = ''
  carregando.value = true
  try {
    const { data } = await api.post('/auth/login', form)
    saveAuth(data)
    emit('auth-updated')
    await router.push('/dashboard')
  } catch (e) {
    erro.value = e.response?.data?.erro || 'Falha no login'
  } finally {
    carregando.value = false
  }
}
</script>
