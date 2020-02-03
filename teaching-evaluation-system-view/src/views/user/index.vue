<template>
  <div class="app-container documentation-container">
    <a class="document-btn" @click="addUser()">用户权限激活入口</a>
    <a class="document-btn">用户信息查看入口</a>
    <a class="document-btn">管理员权限修改入口</a>
  </div>
</template>

<script>
export default {
  name: 'Documentation',
  data() {
    return {

    }
  },
  methods: {
    addUser() {
      this.$router.push('/user/permission/index')
    }
  }
}
</script>

<style lang="scss" scoped>
  .documentation-container {
    margin: 50px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
    .document-btn {
      flex-shrink: 0;
      display: block;
      cursor: pointer;
      background: black;
      color: white;
      height: 60px;
      width: 200px;
      margin-bottom: 16px;
      line-height: 60px;
      font-size: 20px;
      text-align: center;
    }
  }
</style>
