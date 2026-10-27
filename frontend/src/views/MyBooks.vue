<template>
  <div class="container">
    <h2>我的发布</h2>
    <div class="grid">
      <div class="card" v-for="b in books" :key="b.id">
        <h3>{{ b.title }}</h3>
        <p>¥{{ b.price }} · {{ b.bookCondition }}</p>
        <p style="font-size:13px">状态：{{ b.status === 'SOLD' ? '已售出' : '在售' }}</p>
        <button class="plain" @click="del(b.id)">删除</button>
      </div>
    </div>
    <p v-if="!books.length" style="color:#888">你还没有发布过教材。</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request'

const books = ref([])

async function load() {
  books.value = await request.get('/books/me')
}
async function del(id) {
  if (!confirm('确定删除这本教材？')) return
  await request.delete('/books/' + id)
  load()
}
onMounted(load)
</script>
