import request from '@/utils/request'
import querystring from 'querystring'
export default {
  namespaced: true,
  state: {
    teachers: []
  },
  mutations: {
    refreshTeacher(state, teachers) {
      state.teachers = teachers
    }
  },
  actions: {
    //   删除
    async deleteTeacherByIds({ dispatch }, params) {
      const response = await request.get('/user/teacher/delectByIds', { params })
      dispatch('findAllTeachers')
    },
    //   保存或更新
    async saveOrUpdateTeacher({ dispatch }, params) {
      const url = '/user/teacher/addAndUpdate'
      await request({
        url,
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: querystring.stringify(params)
      })
      dispatch('findAllTeachers')
    },
    //   通过关键字搜索
    async findTeacherByKeyword({ commit }, params) {
      const response = await request.get('/user/teacher/selectByKeyword', { params })
      commit('refreshTeacher', response.data)
    },
    //   查询所有讲师
    async findAllTeachers({ commit }) {
      const response = await request.get('/user/teacher/selectAllTeachers')
      commit('refreshTeacher', response.data)
    }

  }
}
