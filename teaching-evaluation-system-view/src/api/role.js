import request from '@/utils/request'

export function getRoutes() {
  return request({
    url: '/routes',
    method: 'get'
  })
}

export function getRoles() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}
export function getPageRoles(currentPage) {
  return request({
    url: '/user/page',
    method: 'get',
    params: { currentPage }
  })
}
export function addRole(data) {
  return request({
    url: '/user/activation',
    method: 'post',
    data
  })
}

export function updateRole(id, data) {
  return request({
    url: `/role/${id}`,
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/role/${id}`,
    method: 'delete'
  })
}
