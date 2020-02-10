<template>
  <el-tree :data="data" class="" :props="defaultProps" style="height:7%;background: #2b2f3a; font-size: 24px;color: white; font-weight: bold;margin-top: 5px" @node-click="handleNodeClick" />
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
        label: '学生信息',
        children: [{
          label: '信息详情'
        }]
      }]
    }
  },
  methods: {
    handleNodeClick: function(dropNode) {
      if (dropNode.label === '信息详情') {
        this.$router.push('/profile/index')
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
