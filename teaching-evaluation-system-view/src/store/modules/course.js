import request from '@/utils/request'
import querystring from 'querystring'
export default {
  namespaced: true,
  state: {
    coursees: []
  },
  mutations: {
    refreshCourse(state, coursees) {
      state.coursees = coursees
    }
  },
  actions: {
    //   通过关键字查询课程信息
    async findInfoByKeyWord({ commit }, params) {
      const response = await request.get('/user/course/selectByKeyWord', { params })
      commit('refreshCourse', response.data)
      // dispatch('findAllCoursees')
    },
    //   通过id删除课程信息
    async deleteCouersees({ dispatch }, params) {
      const response = await request.get('/user/course/deleteById', { params })
      dispatch('findAllCoursees')
    },
    //   保存或更新
    async addOrupdateCourse({ dispatch }, form) {
      const url = '/user/course/addOrupdate'
      await request({
        url,
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: querystring.stringify(form)
      })
      dispatch('findAllCoursees')
    },
    // 查询所有课程
    async findAllCoursees({ commit }) {
      const response = await request.get('/user/course/selectAll')
      commit('refreshCourse', response.data)
    }
  }
}
