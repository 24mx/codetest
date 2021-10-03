<template v-if="loaded">
  <h3>Available posts in the system are</h3>
  <div v-if="message">{{ message }}</div>
  <table>
    <tr>
      <th><label>Id</label></th>
      <th><label>Title</label></th>
      <th><label>Content</label></th>
      <th colspan="3"><label>Action</label></th>
    </tr>
    <tr v-for="post in posts" :key="post.id">
      <td>{{ post.id }}</td>
      <td>{{ post.title }}</td>
      <td>{{ post.content }}</td>
      <td>
        <button @click="showPostDetails(post)">Show</button>
      </td>
      <td>
        <button @click="goToEditPage(post.id)">Edit</button>
      </td>
      <td>
        <button @click="deletePostDetails(post.id)">Delete</button>
      </td>
    </tr>
  </table>

  <ShowPostDetailsModal v-show="isModalVisible" @close="closeModal" :post="this.selectedPost"/>

</template>

<script>
import PostService from '@/services/PostService'
import ShowPostDetailsModal from '@/views/modal/ShowPostDetailsModal'

export default {
  name: "ListAllPosts",
  components: {
    ShowPostDetailsModal
  },
  data() {
    return {
      posts: [],
      loaded: false,
      isModalVisible: false,
      selectedPost: {},
      message: '',
    }
  },
  mounted() {
    this.loadAllPosts()
  },
  methods: {
    loadAllPosts() {
      PostService.getAllPosts()
          .then((response) => {
            this.posts = response.data
            this.loaded = true
          }).catch((error) => {
        console.log("Error occurred " + error.data)
      })
    },
    showPostDetails(post) {
      this.message = ''
      this.selectedPost = post
      this.isModalVisible = true
    },

    closeModal() {
      this.message = ''
      this.isModalVisible = false
    },

    deletePostDetails(postId) {
      this.message = ''
      PostService.deletePost(postId)
          .then(response => {
            if (response.status === 200) {
              this.loadAllPosts()
              this.message = 'Post deleted successfully.'
            } else if (response.status === 404) {
              this.message = 'Post not found.'
            }
          })
          .catch(error => {
            console.log("Error occurred while deleting a post " + error)
          })
    },

    goToEditPage(postId) {
      this.$router.push({name: 'EditPost', params: {id: postId}});
    }
  }
}
</script>

<style scoped>
table {
  max-width: 600px;
  margin: 30px auto;
  background: white;
  text-align: center;
  padding: 40px;
  border-radius: 10px;
}

button {
  margin-top: 0;
}
</style>