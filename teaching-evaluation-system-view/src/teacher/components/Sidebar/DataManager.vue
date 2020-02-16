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
        label: '选课管理',
        children: [{
          label: '进行选课'
        }
        ]
      },
      {
        label: '评教管理',
        children: [{
          label: '评教记录'
        }
        ]
      }
      ]
    }
  },
  methods: {
    handleNodeClick: function(dropNode) {
      if (dropNode.label === '进行选课') {
        this.$router.push('/teacher/course/index')
      }
      if (dropNode.label === '评教记录') {
        this.$router.push('/teacher/score/record')
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
