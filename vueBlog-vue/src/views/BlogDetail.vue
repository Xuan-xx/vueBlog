<template>
  <div>
    <Header></Header>
    <el-row gutter="30">
      <el-col span="16" push="4">
        <div class="mblog">
          <h1 style="text-align: center">
            {{ blog.title }}
            <div v-show="ownBlog">
              <el-link icon="el-icon-edit">
                <router-link :to="{name: 'BlogEdit',params:{blogId: blog.id}}">
                  编辑
                </router-link>
              </el-link>
            </div>
          </h1>
          <el-divider></el-divider>
          <div class="markdown-body" v-html="blog.content"></div>
        </div>
        <br>
        <el-button type="danger" v-show="ownBlog" @click="deleteBlog" >删除</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Header from "@/components/Header";
import "github-markdown-css/github-markdown-light.css"

export default {
  name: "BlogDetail",
  components: {Header},
  data() {
    return {
      blog: {
        id: '',
        title: "title",
        content: "content"
      },
      ownBlog: false
    }
  },
  created: function () {
    const blogId = this.$route.params.blogId;
    if (blogId) {
      this.$axios.get('/vblog/blog/' + blogId).then(res => {
        const blog = res.data.data;
        this.blog.id = blog.id;
        this.blog.title = blog.title;

        const MDIt = require("markdown-it");
        const md = new MDIt();
        const result = md.render(blog.content);

        this.blog.content = result;
        this.ownBlog = (blog.userId === this.$store.getters.getUser.id);
      })
    }
  },
  methods:{
    deleteBlog(){
      this.$axios.delete('/vblog/blog/'+this.blog.id,{
        headers:{
          "Authorization" : localStorage.getItem("token")
        }
      }).then(res=>{
        this.$message("删除博客成功！");
        this.$router.push("/blogs");
      })
    }
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
}
</style>