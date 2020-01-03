import request from '@/utils/request'
import querystring from 'querystring'
export default {
  namespaced: true,
  state: {
    departments: []
  },
  mutations: {
    refreshDepartment(state, departments) {
      state.departments = departments
    }
  },
  actions: {
    //   删除
    async deleteDepartmentByIds({ dispatch }, params) {
      const response = await request.get('/user/department/delectByIds', { params })
      dispatch('QueryDepartments')
    },

    //   保存或修改
    async addOrUpdateDepartment({ dispatch }, params) {
      // console.log(querystring.stringify(params))
      const url = '/user/department/addAndUpdate'
      await request({
        url,
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: querystring.stringify(params)
      })
      dispatch('QueryDepartments')
    },
    //   通过关键字查询部门信息
    async findInfoDepartment({ commit }, params) {
      const response = await request.get('/user/department/selectByKeyword', { params })
      commit('refreshDepartment', response.data)
      return response
    },
    // 查询所有部门信息
    // async findAllDepartments({ commit }){
    //     let response = await request.get('/user/department/selectAllDepartments')
    //     commit('refreshDepartment',response.data)
    // },

    // 分页查询
    async QueryDepartments({ commit }, params) {
      const response = await request.get('/user/department/selectAllDepartments/findPage', { params })
      commit('refreshDepartment', response.data)
      return response
    }

  }
}
