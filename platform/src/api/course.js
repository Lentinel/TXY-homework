import request from '@/utils/request'

// 创建课程
export function createCourse(data) {
  return request({
    url: '/api/teacher/course',
    method: 'post',
    data
  })
}

// 获取课程列表
export function getCourseList(params) {
  return request({
    url: '/api/teacher/course',
    method: 'get',
    params
  })
}

// 获取课程详情
export function getCourseDetail(id) {
  return request({
    url: `/api/teacher/course/${id}`,
    method: 'get'
  })
}

// 更新课程描述
export function updateCourseDesc(courseId, desc) {
  return request({
    url: `/api/teacher/course/${courseId}/desc`,
    method: 'put',
    params: { desc }
  })
}

// 删除课程
export function deleteCourse(id) {
  return request({
    url: `/api/teacher/course/${id}`,
    method: 'delete'
  })
}

// 更新课程
export function updateCourse(id, data) {
  return request({
    url: `/api/teacher/course/${id}`,
    method: 'put',
    data
  })
}

// 获取分类列表
export function getCategoryList() {
  return request({
    url: '/api/category',
    method: 'get'
  })
}