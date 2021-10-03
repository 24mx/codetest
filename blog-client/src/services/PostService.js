import axios from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:8081/blog-web/",
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }
})

export default {
    getAllPosts() {
        return apiClient.get("/posts")
    },

    getPostById(postId) {
        return apiClient.get("/posts/" + postId)
    },

    createPost(post) {
        return apiClient.post("/posts", {
            id: post.id,
            title: post.title,
            content: post.content
        })
    },

    updatePost(post) {
        return apiClient.put("/posts", {
            id: post.id,
            title: post.title,
            content: post.content
        })
    },

    deletePost(postId) {
        return apiClient.delete("/posts/", {
            params: {
                postId: postId
            }
        })
    }
}
