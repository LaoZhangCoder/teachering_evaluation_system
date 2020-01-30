<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="班级名称">
      <el-input v-model="form.className" />
    </el-form-item>
    <el-form-item label="所属院系">
      <el-select v-model="form.departmentId" placeholder="请选择院系" @change="departmentChange(form.departmentId)">
        <el-option v-for="department in departmentList" :label="department.name" :value="department.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="所属专业">
      <el-select v-model="form.majorId" placeholder="请选择专业">
        <el-option v-for="major in majorList" :label="major.majorName" :value="major.majorId" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即修改</el-button>
      <el-button @click="onExit">取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import { queryDepartment, updateClass, listMajor, queryMajorByCondition } from '@/api/user'
export default {
  data() {
    return {
      form: {
        className: '',
        departmentId: '',
        classId: '',
        majorId: '',
        oldDepartmentId: '',
        oldMajorId: '',
        oldName: ''
      },
      departmentList: [],
      majorList: []
    }
  },
  created() {
    this.loading = true
    queryDepartment().then(response => {
      this.departmentList = response.result
      console.log(this.departmentList)
    })
    listMajor().then(response => {
      this.majorList = response.result
    })
  },
  methods: {
    onSubmit() {
      this.form.classId = this.$route.query.classId
      this.form.oldName = this.$route.query.className
      this.form.oldDepartmentId = this.$route.query.oldDepartmentId
      this.form.oldMajorId = this.$route.query.oldMajorId
      updateClass(this.form).then(response => {
        if (response.success === true) {
          this.$router.push('/class/index')
        }
      })
    },
    onExit() {
      this.$router.push('/class/index')
    },
    departmentChange(departmentId) {
      this.majorList = null
      queryMajorByCondition(departmentId).then(response => {
        this.majorList = response.result
      })
    }
  }
}
</script>
