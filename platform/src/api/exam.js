import request from '@/utils/request'

// 获取考试列表
export function getExamList(params) {
  return request({
    url: '/api/exam',
    method: 'get',
    params
  })
}

// 获取考试详情
export function getExamDetail(id) {
  return request({
    url: `/api/exam/${id}`,
    method: 'get'
  })
}

// 创建考试
export function createExam(data) {
  return request({
    url: '/api/exam',
    method: 'post',
    data
  })
}

// 更新考试
export function updateExam(id, data) {
  return request({
    url: `/api/exam/${id}`,
    method: 'put',
    data
  })
}

// 删除考试
export function deleteExam(id) {
  return request({
    url: `/api/exam/${id}`,
    method: 'delete'
  })
}

// 提交答案
export function submitExam(examId, data) {
  return request({
    url: `/api/exam/${examId}/submit`,
    method: 'post',
    data
  })
}

// 获取考试结果
export function getExamResult(examId) {
  return request({
    url: `/api/exam/${examId}/result`,
    method: 'get'
  })
}