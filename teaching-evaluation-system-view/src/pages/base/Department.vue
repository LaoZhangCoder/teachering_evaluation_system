<template>
  <div class="department">
    <!-- 按钮 -->
    <div class="btns">
      <el-col :span="20">
        <el-form :inline="true">
          <el-form-item>
            <el-input v-model="KeyWord" placeholder="请输入关键字" @input="findDepartmentKeyWord(KeyWord)" />
          </el-form-item>
          <el-form-item>
            <el-button @click="findDepartmentKeyWord(KeyWord)">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" size="small" @click="toAddHandler">添加</el-button>
        <el-button type="danger" size="small" @click="batchDeleteHandler">批量删除</el-button>
      </el-col>
    </div>
    <!-- /按钮 -->

    <!-- 表格 -->
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="部门编号" type="selection" width="55px" align="center" />
      <el-table-column prop="name" label="部门名称" align="center" />
      <el-table-column prop="region.name" label="地区名称" align="center" />
      <el-table-column prop="description" label="部门描述" align="center" />
      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="record">
          <el-button type="primary" icon="el-icon-edit" circle @click.prevent="editHandler(record.row)" />
          <el-button type="danger" icon="el-icon-delete" circle @click.prevent="deleteHandler(record.row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <!-- /表格 -->

    <!-- 模态框 -->
    <el-dialog :title="title" :visible.sync="visible">
      <el-form :model="department" :rules="rules">
        <el-form-item label="部门名称" label-width="80px" prop="name">
          <el-input v-model="department.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="所属区域" label-width="80px">
          <el-select v-model="department.regionId" placeholder="请选择区域名称">
            <el-option v-for="a in areas" :key="a.id" :value="a.id" :label="a.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="部门描述" label-width="80px" prop="description">
          <el-input v-model="department.description" type="textarea" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="handlerCommit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- /模态框 -->

    <!-- 分页查询 -->
    <el-pagination
      align="right"
      :page-size="page"
      layout="prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    />
    <!-- /分页查询 -->

  </div>
</template>
<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex'
export default {
  data() {
    return {
      tableData: [],
      page: 0,
      total: 5,
      visible: false,
      title: '',
      KeyWord: '',
      department: {},
      rules: {
        name: [
          { required: true, message: '请输入区域名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    const obj = {
      page: this.page,
      size: 4
    }
    // 调用查询所有部门函数
    // this.findAllDepartments()

    // 调用分页查询函数
    this.QueryDepartments(obj).then((res) => {
      this.tableData = res.data.list
      this.total = res.data.total
      this.page = res.data.pageSize
      // console.log(res.data)
    })
    // 调用查询所有区域函数
    this.findAllAreas()
  },
  computed: {
    ...mapState('department', ['departments']),
    ...mapState('area', ['areas'])
  },
  methods: {
    ...mapActions('department', ['findAllDepartments', 'QueryDepartments', 'findInfoDepartment', 'addOrUpdateDepartment', 'deleteDepartmentByIds']),
    ...mapActions('area', ['findAllAreas']),
    // 分页查询
    handleCurrentChange(page) {
      const obj = {
        page: page,
        size: 4
      }
      // console.log("obj",obj)
      this.QueryDepartments(obj).then((res) => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.page = res.data.pageSize
      })
    },
    // 保存
    handlerCommit() {
      this.addOrUpdateDepartment(this.department).then(() => {
        // 提示框
        this.visible = false
        // 提示信息
        this.$message({
          type: 'success',
          message: '保存成功'
        })
      })
    },
    // 搜索
    findDepartmentKeyWord(KeyWord) {
      const obj = {
        keyword: KeyWord
      }
      // console.log(obj)
      this.findInfoDepartment(obj).then((res) => {
        this.tableData = res.data
        // console.log(res)
      })
    },
    // 添加
    toAddHandler() {
      this.visible = true
      this.title = '添加部门信息'
      this.department = {}
    },
    // 删除
    deleteHandler(id) {
      this.$confirm('确认删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        const obj = {
          list: id
        }
        this.deleteDepartmentByIds(obj)
          .then((response) => {
            this.$message({ type: 'success', message: '删除成功' })
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 修改
    editHandler(row) {
      this.visible = true
      // 获取表单数据
      const obj = {
        name: row.name,
        description: row.description,
        regionId: row.region.id
      }
      this.department = obj
      this.title = '修改部门信息'
    },
    // 当选择项改变时，触发该事件
    handleSelectionChange(val) {
      // console.log("val",val)
      var idsBatch = []
      val.forEach((item) => {
        idsBatch.push(item.id)
      })
      this.ids = idsBatch
      // console.log("this.ids",this.ids)
    },
    // 批量删除
    batchDeleteHandler() {
      this.ids.forEach((item) => {
        var obj = {
          list: item
        }
        // console.log("obj",obj)
        this.deleteDepartmentByIds(obj)
      })
    }
  }
}
</script>
