import { createRouter, createWebHistory } from 'vue-router'
import CreatePost from '@/views/CreatePost.vue'
import ListAllPosts from '@/views/ListAllPosts.vue'
import SearchPost from '@/views/SearchPost'
import NotFound from '@/views/NotFound'
import EditPost from '@/views/EditPost'

const routes = [
  {
    path: '/',
    name: 'CreatePost',
    component: CreatePost
  },
  {
    path: '/listPosts',
    name: 'ListPosts',
    component: ListAllPosts
  },
  {
    path: '/searchPost',
    name: 'SearchPost',
    component: SearchPost
  },
  {
    path: '/editPost/:id',
    name: 'EditPost',
    component: EditPost
  },
  {
    path: '/:catchAll(.*)',
    name: 'NotFound',
    component: NotFound
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
