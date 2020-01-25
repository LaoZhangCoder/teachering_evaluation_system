<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="专业名称">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item label="所属院系">
      <el-select v-model="form.departmentId" placeholder="请选择院系">
        <el-option v-for="department in departmentList" :label="department.name" :value="department.id" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button @click="onExit">取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import { queryDepartment, addMajor } from '@/api/user'
export default {
  data() {
    return {
      form: {
        name: '',
        departmentId: ''
      },
      departmentList: []
    }
  },
  created() {
    this.loading = true
    queryDepartment().then(response => {
      this.departmentList = response.result
      console.log(this.departmentList)
    })
  },
  methods: {
    onSubmit() {
      addMajor(this.form).then(response => {
        if (response.success === true) {
          this.$router.push('/major/index')
        }
      })
    },
    onExit() {
      this.$router.push('/major/index')
    }
  }
}
</script>
