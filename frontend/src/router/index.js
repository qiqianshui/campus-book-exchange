import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import BookList from '../views/BookList.vue'
import BookPublish from '../views/BookPublish.vue'
import MyBooks from '../views/MyBooks.vue'
import { useAuthStore } from '../store/auth'

const routes = [
  { path: '/', redirect: '/books' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/books', component: BookList, meta: { auth: true } },
  { path: '/publish', component: BookPublish, meta: { auth: true } },
  { path: '/my', component: MyBooks, meta: { auth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：标记了 auth 的页面，未登录就踢回登录页
router.beforeEach((to, from, next) => {
  const auth = useAuthStore()
  if (to.meta.auth && !auth.isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

export default router
