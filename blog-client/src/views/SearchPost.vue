<template>
  <h3>Search a post by using post id</h3>
  <div v-if="message">{{ message }}</div>
  <form @submit.prevent="handleSearch">
    <label>Post id</label>
    <input type="text" required v-model="id">

    <button type="submit">Search</button>

    <div v-if="post">
      <table>
        <tr>
          <th><label>Id</label></th>
          <th><label>Title</label></th>
          <th><label>Content</label></th>
        </tr>
        <tr>
          <td>{{ post.id }}</td>
          <td>{{ post.title }}</td>
          <td>{{ post.content }}</td>
        </tr>
      </table>
    </div>

  </form>
</template>

<script>
import PostService from '@/services/PostService'

export default {
  name: "SearchPost",
  data() {
    return {
      id: '',
      post: '',
      message: ''
    }
  },
  methods: {
    handleSearch() {
      this.message = ''
      PostService.getPostById(this.id)
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
    }
  }
}
</script>

<style>
form {
  max-width: 420px;
  margin: 30px auto;
  background: white;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}

label {
  color: #aaa;
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.6em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}

input {
  display: block;
  padding: 10px 6px;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid #ddd;
  color: #555;
}

button {
  background: #0b6dff;
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
}
</style>