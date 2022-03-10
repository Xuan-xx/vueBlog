<template>

  <div id="app">
    <Header></Header>

    <div class="block">
      <el-row :gutter="20">
        <el-col :push="4" :span="16">
          <el-timeline>
            <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
              <el-card>
                <h4>
                  <router-link :to="{name: 'BlogDetail',params:{blogId: blog.id}}">
                    {{ blog.title }}
                  </router-link>
                </h4>
                <p style="size: letter;color: darkcyan">作者:{{blog.author}}</p>
                <p>{{ blog.description }}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-col>
      </el-row>
    </div>

    <el-row :gutter="20">
      <el-col :push="4" :span="16">
        <el-pagination
            background
            layout="prev, pager, next"
            :total="total"
            :current-page="currentPage"
            :page-size="pageSize"
            @current-change=page
            class="mpage">
        </el-pagination>
      </el-col>
    </el-row>
  </div>


</template>

<script>
import Header from "@/components/Header";

export default {
  name: "Blogs",
  components: {Header},
  data() {
    return {
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  methods: {
    page(currentPage) {
      this.$axios.get("vblog/blogs?currentPage=" + currentPage)
          .then(res => {
            this.blogs = res.data.data.records;
            this.currentPage = res.data.data.current;
            this.total = res.data.data.total;
            this.pageSize = res.data.data.size;
          })
    }
  },
  created() {
    this.page(1);
  }
}
</script>

<style scoped>

#app {
  max-width: 800px;
  margin: auto;
}

.mpage {
  margin: 0 auto;
  text-align: center;
}
</style>