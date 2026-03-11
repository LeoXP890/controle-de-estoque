import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import ProdutosView from '../views/ProdutosView.vue'
import CategoriasView from '../views/CategoriasView.vue'
import { isAuthenticated } from '../services/auth'

const routes = [
  { path: '/login', component: LoginView },
  { path: '/dashboard', component: DashboardView, meta: { requiresAuth: true } },
  { path: '/produtos', component: ProdutosView, meta: { requiresAuth: true } },
  { path: '/categorias', component: CategoriasView, meta: { requiresAuth: true } },
  { path: '/:pathMatch(.*)*', redirect: '/dashboard' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !isAuthenticated()) {
    return next('/login')
  }
  if (to.path === '/login' && isAuthenticated()) {
    return next('/dashboard')
  }
  return next()
})

export default router
