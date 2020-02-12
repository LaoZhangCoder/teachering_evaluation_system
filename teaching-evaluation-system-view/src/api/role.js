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
export function getCourses() {
  return request({
    url: '/user/course/list',
    method: 'get'
  })
}
export function getAdmins() {
  return request({
    url: '/admin/list',
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
export function getPageCourses(currentPage) {
  return request({
    url: '/user/course/page',
    method: 'get',
    params: { currentPage }
  })
}
export function getPageAdmins(currentPage) {
  return request({
    url: '/admin/page',
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
export function addCourse(data) {
  return request({
    url: '/user/course/add',
    method: 'post',
    data
  })
}
export function addTeacherCourse(data, userId) {
  return request({
    url: `/teacher/course/add/${userId}`,
    method: 'post',
    data
  })
}
export function addAdmin(data) {
  return request({
    url: '/admin/activation',
    method: 'post',
    data
  })
}

export function updateRole(id, data) {
  return request({
    url: `/user/update/${id}`,
    method: 'post',
    data
  })
}
export function updateCourse(id, data) {
  return request({
    url: `/user/course/update/${id}`,
    method: 'post',
    data
  })
}
export function updateAdmin(id, data) {
  return request({
    url: `/admin/update/${id}`,
    method: 'post',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/user/delete/${id}`,
    method: 'get'
  })
}

export function deleteCourse(id) {
  return request({
    url: `/user/course/delete/${id}`,
    method: 'get'
  })
}
export function deleteAdmin(id) {
  return request({
    url: `/admin/delete/${id}`,
    method: 'get'
  })
}
