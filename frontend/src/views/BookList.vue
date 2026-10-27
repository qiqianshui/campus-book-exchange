<template>
  <div class="container">
    <h2>📚 二手教材</h2>
    <input v-model="kw" placeholder="搜索书名 / 作者" />
    <div class="grid">
      <div class="card" v-for="b in filtered" :key="b.id">
        <h3>{{ b.title }}</h3>
        <p>作者：{{ b.author || '—' }}</p>
        <p>成色：{{ b.bookCondition || '—' }}</p>
        <p style="color:#e67e22;font-weight:700">¥{{ b.price }}</p>
        <p style="font-size:13px;color:#666">{{ b.description }}</p>
        <p style="font-size:13px">联系：{{ b.contact || '—' }}</p>
      </div>
    </div>
    <p v-if="!filtered.length" style="color:#888">暂无教材，去“发布”一本吧～</p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import request from '../utils/request'

const books = ref([])
const kw = ref('')

const filtered = computed(() => {
  if (!kw.value) return books.value
  const k = kw.value.toLowerCase()
  return books.value.filter(b =>
    (b.title || '').toLowerCase().includes(k) ||
    (b.author || '').toLowerCase().includes(k)
  )
})

async function load() {
  books.value = await request.get('/books')
}
onMounted(load)
</script>
