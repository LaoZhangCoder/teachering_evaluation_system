<template>
  <el-table
    :data="tableData.filter(data => !search || data.majorName.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%"
  >
    <el-table-column
      label="创建时间"
      prop="date"
    />
    <el-table-column
      label="班级名称"
      prop="className"
    />
    <el-table-column
      label="所属专业"
      prop="majorName"
    />
    <el-table-column
      label="所属院系"
      prop="departmentName"
    />
    <el-table-column
      align="right"
    >
      <template slot="header" slot-scope="scope">
        <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"
        />
      </template>
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index)"
        >Edit
        </el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index)"
        >Delete
        </el-button>
        <el-button type="success" size="mini" @click="open">add</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { listClass, deleteClass } from '@/api/user'

export default {
  data() {
    return {
      tableData: [],
      search: ''
    }
  },
  created() {
    this.loading = true
    listClass().then(response => {
      this.tableData = response.result
    })
  },
  methods: {
    handleEdit(data) {
      this.$router.push({ path: '/class/update',
        query: {
          classId: this.tableData[data].classId,
          className: this.tableData[data].className,
          oldDepartmentId: this.tableData[data].departmentId,
          oldMajorId: this.tableData[data].majorId
        }
      })
    },
    handleDelete(data) {
      deleteClass(this.tableData[data].classId)
      location.reload()
    },
    open() {
      this.$router.push('/class/add')
    }
  }
}
</script>
