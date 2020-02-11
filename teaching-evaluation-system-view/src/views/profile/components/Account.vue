<template>
  <el-form>
    <el-form-item label="Name">
      <el-input v-model.trim="user.name" />
    </el-form-item>
    <el-form-item label="password">
      <el-input v-model.trim="user.password" />
    </el-form-item>
    <el-form-item label="所属院系">
      <el-select v-model="user.departmentId" placeholder="请选择院系" @change="departmentChange(user.departmentId)">
        <el-option v-for="department in departmentList" :label="department.name" :value="department.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="所属专业">
      <el-select v-model="user.majorId" placeholder="请选择专业" @change="majorChange(user.majorId)">
        <el-option v-for="major in majorList" :label="major.majorName" :value="major.majorId" />
      </el-select>
    </el-form-item>
    <el-form-item label="所在班级">
      <el-select v-model="user.classId" placeholder="请选择专业">
        <el-option v-for="clasps in classes" :label="clasps.className" :value="clasps.classId" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { queryDepartment, queryClassesByCondition, queryMajorByCondition, updateStudent, getInfo } from '@/api/user'
import store from '@/store'
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          password: '',
          departmentId: '',
          majorId: '',
          classId: '',
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
      updateStudent(this.user).then(response => {
        this.$message({
          message: 'User information has been updated successfully',
          type: 'success',
          duration: 5 * 1000
        })
      })
    },
    departmentChange(departmentId) {
      this.majorList = null
      queryMajorByCondition(departmentId).then(response => {
        this.majorList = response.result
      })
    },
    majorChange(majorId) {
      this.classes = null
      queryClassesByCondition(majorId).then(response => {
        this.classes = response.result
      })
    }
  }
}
</script>
