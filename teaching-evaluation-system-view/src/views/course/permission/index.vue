<template>
  <div class="app-container">
    <el-button type="primary" @click="handleAddRole">添加课程</el-button>

    <el-table :data="rolesList" style="width: 100%;margin-top:30px;" border>
      <el-table-column align="center" label="课程编号" width="220">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="课程名称" width="220">
        <template slot-scope="scope">
          {{ scope.row.courseName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="所属院系" width="220">
        <template slot-scope="scope">
          {{ scope.row.departmentName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="所属专业" width="220">
        <template slot-scope="scope">
          {{ scope.row.majorName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="所属班级" width="220">
        <template slot-scope="scope">
          {{ scope.row.className }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="Operations">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">Edit</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">Delete</el-button>
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
    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'Edit Course':'New Course'">
      <el-form :model="course" label-width="80px" label-position="left">
        <el-form-item label="课程名称">
          <el-input v-model="course.courseName" placeholder="课程名称" />
        </el-form-item>
        <el-form-item label="所在院系">
          <el-select v-model="course.departmentId" placeholder="所在院系" @change="departmentChange(course.departmentId)">
            <el-option v-for="department in departmentList" :label="department.name" :value="department.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="所在专业">
          <el-select v-model="course.majorId" placeholder="所在专业" @change="majorChange(course.majorId)">
            <el-option v-for="major in majorList" :label="major.majorName" :value="major.majorId" />
          </el-select>
        </el-form-item>
        <el-form-item label="所在班级">
          <el-select v-model="course.classId" placeholder="所在班级">
            <el-option v-for="major in classes" :label="major.className" :value="major.classId" />
          </el-select>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">Cancel</el-button>
        <el-button type="primary" @click="confirmRole">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import path from 'path'
import { deepClone } from '@/utils'
import { getRoutes, getCourses, addCourse, deleteCourse, updateCourse, getPageCourses } from '@/api/role'
import { queryDepartment, queryClassesByCondition, queryMajorByCondition } from '@/api/user'
const defaultRole = {
  courseName: '',
  departmentId: '',
  majorId: '',
  classId: '',
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
    queryDepartment().then(response => {
      this.departmentList = response.result
    })
  },
  methods: {
    async getRoutes() {
      const res = await getRoutes()
      this.routes = this.generateRoutes(res.data)
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
    },
    async getCourses() {
      const res = await getCourses()
      this.rolesList = res.result
      this.count = res.count
    },

    // Reshape the routes structure so that it looks the same as the sidebar
    generateRoutes(routes, basePath = '/') {
      const res = []

      for (let route of routes) {
        // skip some route
        if (route.hidden) { continue }

        const onlyOneShowingChild = this.onlyOneShowingChild(route.children, route)

        if (route.children && onlyOneShowingChild && !route.alwaysShow) {
          route = onlyOneShowingChild
        }

        const data = {
          path: path.resolve(basePath, route.path),
          title: route.meta && route.meta.title

        }

        // recursive child routes
        if (route.children) {
          data.children = this.generateRoutes(route.children, data.path)
        }
        res.push(data)
      }
      return res
    },
    generateArr(routes) {
      let data = []
      routes.forEach(route => {
        data.push(route)
        if (route.children) {
          const temp = this.generateArr(route.children)
          if (temp.length > 0) {
            data = [...data, ...temp]
          }
        }
      })
      return data
    },
    async handleCurrentChange(currentPage) {
      const res = await getPageCourses(currentPage)
      this.rolesList = res.result
      this.count = res.count
    },
    handleAddRole() {
      this.course = Object.assign({}, defaultRole)
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedNodes([])
      }
      this.dialogType = 'new'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
      this.checkStrictly = true
      this.course = deepClone(scope.row)
      this.id = this.rolesList[scope.$index].id
      this.$nextTick(() => {
        const routes = this.generateRoutes(this.course.routes)
        this.$refs.tree.setCheckedNodes(this.generateArr(routes))
        // set checked state of a node not affects its father and child nodes
        this.checkStrictly = false
      })
    },
    handleDelete({ $index, row }) {
      this.$confirm('Confirm to remove the Course?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(async() => {
          await deleteCourse(this.rolesList[$index].id)
          this.rolesList.splice($index, 1)
          this.$message({
            type: 'success',
            message: 'Delete success!'
          })
        })
        .catch(err => { console.error(err) })
    },
    generateTree(routes, basePath = '/', checkedKeys) {
      const res = []

      for (const route of routes) {
        const routePath = path.resolve(basePath, route.path)

        // recursive child routes
        if (route.children) {
          route.children = this.generateTree(route.children, routePath, checkedKeys)
        }

        if (checkedKeys.includes(routePath) || (route.children && route.children.length >= 1)) {
          res.push(route)
        }
      }
      return res
    },
    async confirmRole() {
      const isEdit = this.dialogType === 'edit'
      if (isEdit) {
        await updateCourse(this.id, this.course)
        for (let index = 0; index < this.rolesList.length; index++) {
          if (this.rolesList[index].key === this.course.key) {
            this.rolesList.splice(index, 1, Object.assign({}, this.course))
            break
          }
        }
      } else {
        await addCourse(this.course)
        this.$router.push('/user/course/index')
      }

      this.dialogVisible = false
      location.reload()
      this.$notify({
        title: 'Success',
        dangerouslyUseHTMLString: true,
        message: `
            <div>操作成功!</div>
          `,
        type: 'success'
      })
    },
    // reference: src/view/layout/components/Sidebar/SidebarItem.vue
    onlyOneShowingChild(children = [], parent) {
      let onlyOneChild = null
      const showingChildren = children.filter(item => !item.hidden)

      // When there is only one child route, the child route is displayed by default
      if (showingChildren.length === 1) {
        onlyOneChild = showingChildren[0]
        onlyOneChild.path = path.resolve(parent.path, onlyOneChild.path)
        return onlyOneChild
      }

      // Show parent if there are no child route to display
      if (showingChildren.length === 0) {
        onlyOneChild = { ... parent, path: '', noShowingChildren: true }
        return onlyOneChild
      }

      return false
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
