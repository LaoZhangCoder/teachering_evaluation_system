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
        label: '课程评教',
        children: [{
          label: '进行评教'
        }
        ]
      }]
    }
  },
  methods: {
    handleNodeClick: function(dropNode) {
      if (dropNode.label === '进行评教') {
        this.$router.push('/student/score')
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
