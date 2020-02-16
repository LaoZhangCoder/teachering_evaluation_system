import request from '@/utils/request'

export function fetchList(token) {
  return request({
    url: '/student/teacher/score',
    method: 'get',
    params: { token }
  })
}

export function getRecordList(userId) {
  return request({
    url: '/student/score/list',
    method: 'get',
    params: { userId }
  })
}
export function startScore(data) {
  return request({
    url: '/student/start/score',
    method: 'post',
    data
  })
}
export function fetchUserList(query) {
  return request({
    url: '/admin/user',
    method: 'get',
    params: query
  })
}
export function fetchArticle(id) {
  return request({
    url: '/article/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/article/pv',
    method: 'get',
    params: { pv }
  })
}

export function createArticle(data) {
  return request({
    url: '/article/create',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/article/update',
    method: 'post',
    data
  })
}
