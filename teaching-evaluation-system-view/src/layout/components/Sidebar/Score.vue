<template>
      <el-tree :data="data" class="" :props="defaultProps" style="height:7%;background: #2b2f3a; font-size: 24px;color: white; font-weight: bold;margin-top: 5px" @node-click="handleNodeClick"></el-tree>
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
        label: '分数统计',
        children: [{
          label: '当前分数'
        }]
      }]
    }
  },
  methods: {
    handleNodeClick: function(dropNode) {
      if (dropNode.label === '当前分数') {
        this.$router.push('/currentScore/index')
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
