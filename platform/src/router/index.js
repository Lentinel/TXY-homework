import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // 登录页面路由，不需要认证
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/user/Login.vue')
  },
  
  // 主布局路由，所有需要认证的页面都在这里作为子路由
  {
    path: '/',
    component: () => import('@/components/layout/BasicLayout.vue'),
    redirect: '/dashboard',
    children: [
      // 学生主页，例如，可以显示课程学习进度、我的问答等
      {
        path: 'student-dashboard',
        name: 'StudentDashboard',
        component: () => import('@/views/student/StudentDashboard.vue'),
        meta: { title: '学生主页', requiresAuth: true, roles: [0] }
      },
// 教师主页，例如，可以显示课程管理、学生问答等
      {
        path: 'teacher-dashboard',
        name: 'TeacherDashboard',
        component: () => import('@/views/teacher/TeacherDashboard.vue'),
        meta: { title: '教师主页', requiresAuth: true, roles: [1] }
      },
// 管理员主页，例如，可以显示用户管理、课程审核等
      {
        path: 'admin-dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/AdminDashboard.vue'),
        meta: { title: '管理员主页', requiresAuth: true, roles: [2] }
      },
      {
        path: 'home',
        name: 'HomePage',
        component: () => import('@/views/HomePage.vue'),
        meta: { title: '主页' }
      },{
        path: 'course/:courseId/discussions',
        name: 'DiscussionListPage',
        component: () => import('@/views/DiscussionListPage.vue'),
        props: true,
        meta: { title: '课程讨论' }
      },
      {
        path: 'course/:courseId/discussions/create',
        name: 'CreateDiscussion',
        component: () => import('@/views/CreateDiscussion.vue'),
        props: true,
        meta: { title: '发布讨论' }
      },
      {
        path: 'course/:courseId/discussions/:discussionId',
        name: 'DiscussionDetail',
        component: () => import('@/views/DiscussionDetail.vue'),
        props: true,
        meta: { title: '讨论详情' }
      },
// 课程进度模块
      {
        path: 'course/:courseId/progress',
        name: 'CourseProgressPage',
        component: () => import('@/views/CourseProgressPage.vue'),
        props: true,
        meta: { title: '学习进度' }
      },
// 用户收藏模块
      {
        path: 'user/favorites',
        name: 'UserFavoritesPage',
        component: () => import('@/views/UserFavoritesPage.vue'),
        meta: { title: '我的收藏' }
      },
      // ----------------- 原有课程管理路由 START -----------------
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
      // ----------------- 原有课程管理路由 END -----------------

      // ----------------- 论坛模块路由 START -----------------
      {
        path: 'forum', // 论坛板块列表页，新增
        name: 'ForumSectionPage',
        component: () => import('@/views/ForumSectionPage.vue'),
        meta: { title: '论坛板块' }
      },
      {
        path: 'forum/sections/:sectionId', // 按板块查看帖子列表，新路径
        name: 'ForumPage',
        component: () => import('@/views/ForumPage.vue'),
        props: true,
        meta: { title: '论坛帖子' }
      },
      {
        path: 'forum/sections/:sectionId/posts/:postId', // 论坛帖子详情页，新路径
        name: 'PostDetail',
        component: () => import('@/views/PostDetail.vue'),
        props: true,
        meta: { title: '帖子详情' }
      },
      {
        path: 'forum/create', // 创建新帖子页面，保持不变
        name: 'CreatePost',
        component: () => import('@/views/CreatePost.vue'),
        meta: { title: '发帖' }
      },
      // ----------------- 论坛模块路由 END -----------------

      // ----------------- 问答模块路由 START -----------------
      {
        path: 'course/:courseId/questions', // 课程问答列表页，保持不变
        name: 'QuestionListPage',
        component: () => import('@/views/QuestionListPage.vue'),
        props: true,
        meta: { title: '课程问答' }
      },
      {
        path: 'course/:courseId/ask', // 提问页面，保持不变
        name: 'AskQuestion',
        component: () => import('@/views/AskQuestion.vue'),
        props: true,
        meta: { title: '我要提问' }
      },
      {
        path: 'course/:courseId/question/:questionId', // 问题详情页，保持不变
        name: 'QuestionDetail',
        component: () => import('@/views/QuestionDetail.vue'),
        props: true,
        meta: { title: '问题详情' }
      },
      {
        path: 'user/:userId/questions', // 我的提问页面，保持不变
        name: 'MyQuestionsPage',
        component: () => import('@/views/MyQuestionsPage.vue'),
        props: true,
        meta: { title: '我的提问' }
      },
      {
        path: 'user/:userId/answers', // 我的回答页面，保持不变
        name: 'MyAnswersPage',
        component: () => import('@/views/MyAnswersPage.vue'),
        props: true,
        meta: { title: '我的回答' }
      },
      // ----------------- 问答模块路由 END -----------------
      
      // ----------------- 课程讨论模块路由 START -----------------
      {
        path: 'course/:courseId/discussions', // 课程讨论列表页
        name: 'DiscussionListPage',
        component: () => import('@/views/DiscussionListPage.vue'),
        props: true,
        meta: { title: '课程讨论' }
      },
      {
        path: 'course/:courseId/discussions/create', // 发布新讨论
        name: 'CreateDiscussion',
        component: () => import('@/views/CreateDiscussion.vue'),
        props: true,
        meta: { title: '发布讨论' }
      },
      {
        path: 'course/:courseId/discussions/:discussionId', // 讨论详情页
        name: 'DiscussionDetail',
        component: () => import('@/views/DiscussionDetail.vue'),
        props: true,
        meta: { title: '讨论详情' }
      }
      // ----------------- 课程讨论模块路由 END -----------------
 

      // ----------------- 管理员模块路由 START -----------------
      {
        path: 'admin-dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/AdminDashboard.vue'),
        meta: { title: '管理员主页', roles: [2] }
      },
      {
        path: 'admin/users',
        name: 'UserManagementPage',
        component: () => import('@/views/admin/UserManagementPage.vue'),
        meta: { title: '用户管理', roles: [2] }
      },
      {
        path: 'admin/courses',
        name: 'CourseManagementPage',
        component: () => import('@/views/admin/CourseManagementPage.vue'),
        meta: { title: '课程管理', roles: [2] }
      },
      {
        path: 'admin/orders',
        name: 'OrderManagementPage',
        component: () => import('@/views/admin/OrderManagementPage.vue'),
        meta: { title: '订单管理', roles: [2] }
      }
      // ----------------- 管理员模块路由 END -----------------
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