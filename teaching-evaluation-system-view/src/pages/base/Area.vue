<template>
  <div class="department">
    <!-- 按钮 -->
    <div class="btns">
      <el-col :span="20">
        <el-form :inline="true">
          <el-form-item id="">
            <!-- <el-input v-model="input" placeholder="请输入区域名称" /> -->
            <el-select v-model="form.name" placeholder="请选择区域名称">
              <el-option v-for="form in areas" :key="form.id" :label="form.name" :value="form.name" />
            </el-select>
          </el-form-item>
          <el-form-item id="" v-model="form.name">
            <el-button @click="findAreaByname(form.name)">搜索</el-button>
            <el-button @click="handlerReset">重置</el-button>
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
    <el-table ref="multipleTable" :data="areas" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="区域编号" type="selection" width="55px" align="center" />
      <el-table-column prop="name" label="区域名称" width="180" align="center" />
      <el-table-column prop="telepone" label="联系方式" width="180" align="center" />
      <el-table-column prop="address" label="区域地址" align="center" />
      <el-table-column prop="description" label="区域描述" width="180" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="record">
          <el-button type="danger" icon="el-icon-delete" circle @click.prevent="deleteHandler(record.row.id)" />
          <el-button type="primary" icon="el-icon-edit" circle @click.prevent="editHandler(record.row)" />
        </template>
      </el-table-column>
    </el-table>
    <!-- /表格 -->

    <!-- 模态框 -->
    <el-dialog :title="title" :visible.sync="visible">
      {{ form }} <br>
      <!-- {{areas}} -->
      <el-form :model="form" :rules="rules">
        <el-form-item label="区域名称" label-width="80px" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系方式" label-width="80px" prop="telepone">
          <el-input v-model="form.telepone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="区域地址" label-width="80px" prop="address">
          <el-input v-model="form.address" autocomplete="off" />
        </el-form-item>
        <el-form-item label="版权信息" label-width="80px" prop="copyright">
          <el-input v-model="form.copyright" autocomplete="off" />
        </el-form-item>
        <el-form-item label="区域描述" label-width="80px">
          <el-input v-model="form.description" type="textarea" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="handlerCommit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- /模态框 -->
    <!-- {{areas}} -->
  </div>
</template>
<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex'
export default {
  data() {
    return {
      input: '',
      visible: false,
      title: '',
      form: {},
      rules: {
        name: [
          { required: true, message: '请输入区域名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        telepone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入区域地址', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        copyright: [
          { required: true, message: '请输入版权信息', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 调用状态机里的查询所有区域函数
    this.findAllAreas()
  },
  computed: {
    ...mapState('area', ['areas'])
  },
  methods: {
    ...mapActions('area', ['findAllAreas', 'addOrUpdateArea', 'deleteAreas', 'searchAreas']),
    // 批量删除区域信息
    batchDeleteHandler() {
      this.id.forEach((item) => {
        var obj = {
          id: item
        }
        this.deleteAreas(obj)
      })
    },
    handleSelectionChange(val) {
      // val获取当前行的所有数据
      var idBatch = []
      val.forEach((item) => {
        // 将item的id放入idsBatch中
        idBatch.push(item.id)
      })
      this.id = idBatch
      console.log(this.id)
    },
    // 重置按钮
    handlerReset() {
      this.findAllAreas()
    },
    // 保存按钮
    handlerCommit() {
      this.addOrUpdateArea(this.form).then(() => {
        // 提示框
        this.visible = false
        // 提示信息
        this.$message({
          type: 'success',
          message: '保存成功'
        })
      })
    },

    // 添加区域信息
    toAddHandler() {
      // 弹出模态框,使visible为true
      this.visible = true
      // 重置表单信息
      this.form = {}
      // 修改title
      this.title = '添加区域信息'
    },
    // 根据区域名称搜索信息
    findAreaByname(name) {
      this.searchAreas(name)
    },

    // 修改区域信息
    editHandler(row) {
      // 弹出模态框,使visible为true
      this.visible = true
      // 重置表单信息
      this.form = row
      this.title = '修改区域信息'
    },
    // 删除区域信息
    deleteHandler(id) {
      this.$confirm('确认删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        const obj = {
          id: id
        }
        this.deleteAreas(obj)
          .then((response) => {
            this.$message({ type: 'success', message: '删除成功' })
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>
