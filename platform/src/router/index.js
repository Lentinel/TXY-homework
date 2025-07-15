import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/user/Login.vue')
  },
  {
    path: '/',
    component: () => import('@/components/layout/BasicLayout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '控制台' }
      },
      {
        path: 'course',
        name: 'CourseList',
        component: () => import('@/views/course/List.vue'),
        meta: { title: '课程列表' }
      },
      {
        path: 'course/create',
        name: 'CourseCreate',
        component: () => import('@/views/course/Edit.vue'),
        meta: { title: '创建课程' }
      },
      {
        path: 'course/edit/:id',
        name: 'CourseEdit',
        component: () => import('@/views/course/Edit.vue'),
        meta: { title: '编辑课程' }
      },
      // --- 论坛模块路由 START ---
      {
        path: 'forum', // 论坛主页，显示所有帖子
        name: 'ForumPage',
        component: () => import('@/views/ForumPage.vue'), // 假设 ForumPage.vue 在 src/views 下
        meta: { title: '论坛' }
      },
      {
        path: 'forum/create', // 创建新帖子页面
        name: 'CreatePost',
        component: () => import('@/views/CreatePost.vue'), // 假设 CreatePost.vue 在 src/views 下
        meta: { title: '发帖' }
      },
      {
        path: 'forum/:id', // 帖子详情页，显示帖子内容和评论
        name: 'PostDetail',
        component: () => import('@/views/PostDetail.vue'), // 假设 PostDetail.vue 在 src/views 下
        props: true, // 允许将路由参数 `:id` 作为组件的 props 传递
        meta: { title: '帖子详情' }
      },
      // --- 论坛模块路由 END ---

      // --- 问答模块路由 START ---
      {
        path: 'course/:courseId/questions', // 课程问答列表页，显示特定课程下的所有问题
        name: 'QuestionListPage',
        component: () => import('@/views/QuestionListPage.vue'), // 假设 QuestionListPage.vue 在 src/views 下
        props: true, // 允许将路由参数 `:courseId` 作为组件的 props 传递
        meta: { title: '课程问答' }
      },
      {
        path: 'course/:courseId/ask', // 提问页面，用于用户在特定课程下提问
        name: 'AskQuestion',
        component: () => import('@/views/AskQuestion.vue'), // 假设 AskQuestion.vue 在 src/views 下
        props: true, // 允许将路由参数 `:courseId` 作为组件的 props 传递
        meta: { title: '我要提问' }
      },
      {
        path: 'course/:courseId/question/:questionId', // 问题详情页，显示问题内容和所有回答
        name: 'QuestionDetail',
        component: () => import('@/views/QuestionDetail.vue'), // 假设 QuestionDetail.vue 在 src/views 下
        props: true, // 允许将路由参数 `:courseId` 和 `:questionId` 作为组件的 props 传递
        meta: { title: '问题详情' }
      },
      {
        path: 'user/:userId/questions', // 我的提问页面，显示当前用户提问的所有问题
        name: 'MyQuestionsPage',
        component: () => import('@/views/MyQuestionsPage.vue'), // 假设 MyQuestionsPage.vue 在 src/views 下
        props: true, // 允许将路由参数 `:userId` 作为组件的 props 传递
        meta: { title: '我的提问' }
      },
      {
        path: 'user/:userId/answers', // 我的回答页面，显示当前用户作出的所有回答
        name: 'MyAnswersPage',
        component: () => import('@/views/MyAnswersPage.vue'), // 假设 MyAnswersPage.vue 在 src/views 下
        props: true, // 允许将路由参数 `:userId` 作为组件的 props 传递
        meta: { title: '我的回答' }
      }
      // --- 问答模块路由 END ---
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router