<template>
  <div class="app-container">
    <el-table :data="rolesList" style="width: 100%;margin-top:30px;" border>
      <el-table-column align="center" label="编号" width="220">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="课程" width="220">
        <template slot-scope="scope">
          {{ scope.row.courseName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="得分" width="220">
        <template slot-scope="scope">
          {{ scope.row.score }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="学生评教留言" width="220">
        <template slot-scope="scope">
          {{ scope.row.message }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="时间" width="220">
        <template slot-scope="scope">
          {{ scope.row.date }}
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      layout="prev, pager, next"
      style="margin-left: 30%;margin-top: 2%"
      :total="count"
      :page-size="8"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { getTeacherScoreRecord } from '@/api/role'
import store from '@/store'
const defaultRole = {
  departmentId: '',
  majorId: '',
  classId: '',
  courseId: '',
  userId: '',
  key: ''
}

export default {
  data() {
    return {
      course: Object.assign({}, defaultRole),
      routes: [],
      rolesList: [],
      id: '',
      count: null,
      departmentList: [],
      majorList: [],
      classes: [],
      subjects: [],
      dialogVisible: false,
      dialogType: 'new',
      checkStrictly: false,
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  computed: {
    routesData() {
      return this.routes
    }
  },
  created() {
    // Mock: get all routes and roles list from serve
    this.getCourses()
    this.loading = true
  },
  methods: {
    async getCourses() {
      const res = await getTeacherScoreRecord(1, store.getters.token)
      this.rolesList = res.result
      this.count = res.count
    },
    async handleCurrentChange(currentPage) {
      const res = await getTeacherScoreRecord(currentPage, store.getters.token)
      this.rolesList = res.result
      this.count = res.count
    }
  }
}
</script>

<style lang="scss" scoped>
  .app-container {
    .roles-table {
      margin-top: 30px;
    }
    .permission-tree {
      margin-bottom: 30px;
    }
  }
</style>
