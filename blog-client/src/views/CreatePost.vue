<template>
  <h3>Create a post</h3>
  <div v-if="message">{{ message }}</div>

  <form @submit.prevent="handleSubmit">
    <label>Post id</label>
    <input type="text" required v-model="post.id">

    <label>Post title</label>
    <input type="text" required v-model="post.title">

    <label>Post content</label>
    <textarea v-model="post.content"></textarea>
    <div v-if="missingPostContent" class="error">{{ missingPostContent }}</div>

    <button type="submit">Create</button>
  </form>

</template>

<script>
import PostService from '@/services/PostService'

export default {
  name: "CreatePost",
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
  methods: {
    handleSubmit() {
      this.message = ''
      this.missingPostContent=''

      if(!this.post.content) {
        this.missingPostContent = 'Post content is missing'
      }

      if(this.checkPostIdNotPresent(this.post.id) && !this.missingPostContent) {
        PostService.createPost(this.post)
            .then(response => {
              if (response.status === 201) {
                this.message = 'Post created successfully'
              } else if (response.status === 400) {
                this.message = 'Post entries are not valid'
              }
            }).catch(error => {
          console.log("Error occurred while creating a post " + error)
        })
      }
    },

    checkPostIdNotPresent(postId) {
      return PostService.getPostById(postId)
          .then(response => {
            if (response.status === 204) {
              return true
            } else {
              this.message = 'Post with post id already exists'
              return false
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