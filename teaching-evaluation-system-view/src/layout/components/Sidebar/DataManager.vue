<template>
  <el-tree
    :data="data"
    class=""
    :props="defaultProps"
    style="height:7%;background: #2b2f3a; font-size: 24px;color: white; font-weight: bold;margin-top: 5px"
    @node-click="handleNodeClick"
  />
</template>

<script>
export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      data: [{
        label: '数据管理',
        children: [{
          label: '院系管理'
        },
        {
          label: '专业管理'
        },
        {
          label: '班级管理'
        },
        {
          label: '用户管理'
        },
        {
          label: '课程管理'
        }
        ]
      }]
    }
  },
  methods: {
    handleNodeClick: function(dropNode) {
      if (dropNode.label === '院系管理') {
        this.$router.push('/department/index')
      }
      if (dropNode.label === '专业管理') {
        this.$router.push('/major/index')
      }
      if (dropNode.label === '班级管理') {
        this.$router.push('/class/index')
      }
      if (dropNode.label === '用户管理') {
        this.$router.push('/user/index')
      }
      if (dropNode.label === '课程管理') {
        this.$router.push('/user/course/index')
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
