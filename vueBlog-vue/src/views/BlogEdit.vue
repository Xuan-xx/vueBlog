<template>
  <div>
    <Header></Header>

    <div class="m-content">
      <el-form ref="editForm" status-icon :model="editForm" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="16" :push="4">
            <el-form-item label="标题" prop="title">
              <el-input v-model="editForm.title"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="16" :push="4">
            <el-form-item label="摘要" prop="description">
              <el-input type="textarea" v-model="editForm.description"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="16" :push="4">
            <el-form-item label="内容" prop="content">
              <mavon-editor v-model="editForm.content"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="16" :push="4">
            <el-form-item>
              <el-button type="primary" @click="submitForm" v-show="!hasBlogId">立即创建</el-button>
              <el-button type="primary" @click="submitForm" v-show="hasBlogId">修改</el-button>
              <el-button>取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "BlogEdit",
  components: {Header},
  data() {
    return {
      hasBlogId: false,
      editForm: {
        id:'',
        title: '',
        description: '',
        content: '',
        author: this.$store.getters.getUser.username
      },
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 3, max: 25, message: '长度在3到25个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm() {
      this.$refs.editForm.validate((valid) =>{
        if (valid){
          this.$axios.post('/vblog/blog/edit',this.editForm,{
            headers:{
              "Authorization" : localStorage.getItem("token")
            }
          }).then((res) =>{
            this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push("/blogs")
              }
            });
          }).catch(reason => {
            this.$message.error("无法修改，可能是您不具有修改权限。");
          })

        }else {
          console.log('error submit!!');
          return false;
        }
      })
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    if (blogId){
      this.$data.hasBlogId = true;
      this.$axios.get('/vblog/blog/'+blogId).then(res=>{
        const blog = res.data.data;
        this.editForm.id = blog.id;
        this.editForm.title = blog.title;
        this.editForm.description = blog.description;
        this.editForm.content = blog.content;

      })
    }
  }
}
</script>

<style scoped>

.m-content {
  text-align: center;
  margin: auto;
}
</style>