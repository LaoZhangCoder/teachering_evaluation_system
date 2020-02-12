<template>
  <el-form>
    <el-form-item label="Name">
      <el-input v-model.trim="user.name" />
    </el-form-item>
    <el-form-item label="password">
      <el-input v-model.trim="user.password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { queryDepartment, updateTeacher, getInfo } from '@/api/user'
import store from '@/store'
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          password: '',
          userId: ''
        }
      }
    }
  },
  data() {
    return {
      departmentList: [],
      majorList: [],
      classes: []
    }
  },
  created() {
    this.loading = true
    queryDepartment().then(response => {
      this.departmentList = response.result
    })
    getInfo(store.getters.token).then(response => {
      this.user.name = response.result.userName
      this.user.userId = response.result.userId
    })
  },
  methods: {
    submit() {
      updateTeacher(this.user).then(response => {
        this.$message({
          message: 'User information has been updated successfully',
          type: 'success',
          duration: 5 * 1000
        })
      })
    }
  }
}
</script>
