<template>
  <div class="app">
    <nav v-if="auth.isAuthenticated" class="nav">
      <span class="logo">📚 二手教材</span>
      <router-link to="/books">浏览</router-link>
      <router-link to="/publish">发布</router-link>
      <router-link to="/my">我的</router-link>
      <span class="user">{{ auth.username }}</span>
      <button @click="logout">退出</button>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
import { useAuthStore } from './store/auth'
import { useRouter } from 'vue-router'

const auth = useAuthStore()
const router = useRouter()

function logout() {
  auth.logout()
  router.push('/login')
}
</script>
