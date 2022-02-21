<template>
  <div>
    <h3>Edit a post</h3>
    <div v-if="message">{{ message }}</div>

    <form @submit.prevent="handleUpdate">
      <label>Post id</label>
      <input type="text" required v-model="post.id" readonly>

      <label>Post title</label>
      <input type="text" required v-model="post.title">

      <label>Post content</label>
      <textarea v-model="post.content"></textarea>
      <div v-if="missingPostContent" class="error">{{ missingPostContent }}</div>

      <button type="submit">Update</button>
    </form>
  </div>

</template>

<script>
import PostService from '@/services/PostService'

export default {
  name: "EditPost",
  data() {
    return {
      post: {
        id: '',
        title: '',
        content: ''
      },
      message: '',
      missingPostContent: null
    }
  },
  mounted() {
    PostService.getPostById(this.$route.params.id)
        .then(response => {
          if (response.status === 200) {
            this.post = response.data
          } else if (response.status === 204) {
            this.post = ''
            this.message = 'Post not found'
          }
        }).catch(error => {
      console.log("Error occurred while searching a post " + error)
    })
  },
  methods: {
    handleUpdate() {
      this.message = ''
      this.missingPostContent = ''

      if (!this.post.content) {
        this.missingPostContent = 'Post content is missing'
      }

      if (this.checkPostIsPresent(this.post.id) && !this.missingPostContent) {
        PostService.updatePost(this.post)
            .then(response => {
              if (response.status === 201) {
                this.message = 'Post updated successfully'
              } else if (response.status === 400) {
                this.message = 'Post entries are not valid'
              }
            }).catch(error => {
          console.log("Error occurred while creating a post " + error)
        })
      }
    },

    checkPostIsPresent(postId) {
      return PostService.getPostById(postId)
          .then(response => {
            if (response.status === 404) {
              this.message = 'Post with post id does not exist'
              return false
            } else {
              return true
            }
          }).catch(error => {
            console.log("Error occurred while looking a post " + error)
          })
    }
  }
}
</script>

<style scoped>
form {
  max-width: 420px;
  margin: 30px auto;
  background: white;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}

input, textarea {
  display: block;
  padding: 10px 6px;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid #ddd;
  color: #555;
}
</style>